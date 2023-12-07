﻿using AutoMapper;
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

        public PlaylistService(IMapper mapper, IPlaylistRepository playlistRepository)
        {
            _mapper = mapper;
            _playlistRepository = playlistRepository;
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
            await _playlistRepository.AddSongToPlaylistAsync(playlistConfigEntity);


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

            var result = _mapper.Map<PlaylistResponseDto>(res[0]);

            result.song_entities = listSong;

            return result;

        }
    }
}
