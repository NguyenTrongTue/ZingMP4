using Microsoft.AspNetCore.Mvc;
using ZINGMP4.Application.Dto.Playlist;
using ZINGMP4.Application.Dto.Song;
using ZINGMP4.Application.Interface.Playlist;
using ZINGMP4.Application.Interface.Song;
using ZINGMP4.Application.Request;

namespace ZINGMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PlaylistController : ControllerBase
    {
        private readonly IPlaylistService _playlistService;


        public PlaylistController(IPlaylistService playlistService)
        {
            _playlistService = playlistService;
        }


        /// <summary>
        /// Hàm tạo playlist cho người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        [HttpPost("add_playlist")]
        public async Task<IActionResult> AddSong(PlaylistDto playlistDto)
        {
            try
            {
                var result = await _playlistService.AddPlaylistAsync(playlistDto);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        /// <summary>
        /// Hàm thêm bài hát vào playlist người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        [HttpPost("add_song_playlist")]
        public async Task<IActionResult> AddSongToPlaylist(PlaylistConfigDto playlistConfig)
        {
            try
            {
                await _playlistService.AddSongToPlaylistAsync(playlistConfig);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        /// <summary>
        /// Hàm thêm bài hát vào playlist người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        [HttpPost("delete_song_playlist")]
        public async Task<IActionResult> DeleteSongToPlaylist(PlaylistConfigDto playlistConfig)
        {
            try
            {
                await _playlistService.DeleteSongToPlaylistAsync(playlistConfig);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        /// <summary>
        /// Hàm lấy ra playlist của người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        [HttpGet("get_playlist")]
        public async Task<IActionResult> GetPlaylist(Guid playlist_id)
        {
            try
            {
               var result =   await _playlistService.GetPlaylistAsync(playlist_id);
                return Ok(result);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        /// <summary>
        /// Hàm lấy ra playlist của người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        [HttpGet("get_playlist_by_user")]
        public async Task<IActionResult> GetPlaylistByUser(Guid user_id)
        {
            try
            {
                var result = await _playlistService.GetPlaylistByUserAsync(user_id);
                return Ok(result);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
