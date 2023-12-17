using AutoMapper;
using ZINGMP4.Application.Dto.Playlist;
using ZINGMP4.Application.Interface.Playlist;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

namespace ZINGMP4.Application.Service
{
    public class PlaylistService : IPlaylistService
    {
        private readonly IMapper _mapper;
        private readonly IPlaylistRepository _playlistRepository;
        private readonly ISongRepository _songRepository;

        public PlaylistService(IMapper mapper, IPlaylistRepository playlistRepository, ISongRepository songRepository)
        {
            _mapper = mapper;
            _playlistRepository = playlistRepository;
            _songRepository = songRepository;
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

            var playlist = await this.GetPlaylistAsync(playlistConfig.playlist_id);

            if (playlist.playlist_image is null)
            {

                await _playlistRepository.UpdatePlaylistImageAsync(song.thumnail, playlistConfig.playlist_id);
            }
        }

        public async Task<bool> CheckSongExistsInPlaylistAsycn(Guid song_id, Guid playlist_id)
        {
            var result = await  _playlistRepository.CheckSongExistsInPlaylistAsycn(song_id, playlist_id);

            return result;
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

            var res = await _playlistRepository.GetPlaylistAsync(playlist_id);



            var result = new PlaylistResponseDto();
            if (res.Count > 0)
            {
                result = _mapper.Map<PlaylistResponseDto>(res[0]);
                if (res[0].song_id.ToString() != "00000000-0000-0000-0000-000000000000")
                {

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
                    result.song_entities = listSong;
                } else
                {
                    result.song_entities = new List<SongEntity>();
                }
            }
            return result;

        }

        public async Task<List<PlaylistEntity>> GetPlaylistByUserAsync(Guid user_id)
        {
            var res = await _playlistRepository.GetPlaylistByUserAsync(user_id);

            return res;
        }


    }
}
