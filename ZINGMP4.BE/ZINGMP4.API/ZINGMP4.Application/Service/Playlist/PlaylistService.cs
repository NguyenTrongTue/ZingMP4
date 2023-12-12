using AutoMapper;
using ZINGMP4.Application.Dto.Playlist;
using ZINGMP4.Application.Interface.Playlist;
using ZINGMP4.Domain.Cache;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

namespace ZINGMP4.Application.Service
{
    public class PlaylistService : IPlaylistService
    {
        private readonly IMapper _mapper;
        private readonly IPlaylistRepository _playlistRepository;
        private readonly ISongRepository _songRepository;
        private readonly IRedisCache _redisCache;

        public PlaylistService(IMapper mapper, IPlaylistRepository playlistRepository, ISongRepository songRepository, IRedisCache redisCache)
        {
            _mapper = mapper;
            _playlistRepository = playlistRepository;
            _songRepository = songRepository;
            _redisCache = redisCache;
        }

        public async Task<PlaylistEntity> AddPlaylistAsync(PlaylistDto playlistDto)
        {
            var playlistEntity = _mapper.Map<PlaylistEntity>(playlistDto);

            playlistEntity.playlist_id = Guid.NewGuid();

            var res = await _playlistRepository.InsertEntity(playlistEntity);

            return res;

        }

        public async Task AddSongToPlaylistAsync(PlaylistConfigDto playlistConfig)
        {
            var playlistConfigEntity = new PlaylistConfigEntity()
            {
                playlist_config_id = Guid.NewGuid(),
                playlist_id = playlistConfig.playlist_id,
                song_id = playlistConfig.song_id
            };

            var song = await _songRepository.GetSongByIdAsync(playlistConfig.song_id);
            if (song == null)
            {
                throw new Exception("Bài hát không tồn tại");
            }
            await _playlistRepository.AddSongToPlaylistAsync(playlistConfigEntity);

            await _playlistRepository.UpdatePlaylistImageAsync(song.thumnail, playlistConfig.playlist_id);


        }

        public async Task DeleteSongToPlaylistAsync(PlaylistConfigDto playlistConfig)
        {
            var playlistConfigEntity = new PlaylistConfigEntity()
            {
                playlist_id = playlistConfig.playlist_id,
                song_id = playlistConfig.song_id
            };

            await _playlistRepository.DeleteSongToPlaylistAsync(playlistConfigEntity);


        }

        public async Task<PlaylistResponseDto> GetPlaylistAsync(Guid playlist_id)
        {
            string key = $"playlist_{playlist_id}";
            var resultCache = await _redisCache.GetRecordAsync<PlaylistResponseDto>(key);

            if (resultCache is null)
            {
                var res = await _playlistRepository.GetPlaylistAsync(playlist_id);
                var listSong = res.Select(item => new SongEntity()
                {
                    song_id = item.song_id,
                    song_name = item.song_name,
                    singer_name = item.singer_name,
                    thumnail = item.thumnail,
                    link_song = item.link_song,
                    location = item.location,
                    number_of_listens = item.number_of_listens
                }).ToList();
                var result = new PlaylistResponseDto();
                if (res.Count > 0)
                {
                    result = _mapper.Map<PlaylistResponseDto>(res[0]);
                    result.song_entities = listSong;
                }

                await _redisCache.SetRecordAsync<PlaylistResponseDto>(key, result);

                return result;
            }

            return resultCache;

        }

        public async Task<List<PlaylistEntity>> GetPlaylistByUserAsync(Guid user_id)
        {
            var res = await _playlistRepository.GetPlaylistByUserAsync(user_id);

            return res;
        }
    }
}
