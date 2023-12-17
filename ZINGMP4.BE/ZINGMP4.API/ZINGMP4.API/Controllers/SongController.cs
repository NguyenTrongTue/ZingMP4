using Microsoft.AspNetCore.Mvc;
using RabbitMQ;
using ZINGMP4.Application.Interface.Song;
using ZINGMP4.Application.Request;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SongController : ControllerBase
    {
        #region Fields
        private readonly ISongInterface _songInterface;
        private readonly ILogger<SongController> _logger;
        private readonly IMessagerProvider _messagerProvider;
        #endregion
        public SongController(ISongInterface songInterface, ILogger<SongController> logger, IMessagerProvider messagerProvider)
        {
            _songInterface = songInterface;
            _logger = logger;
            _messagerProvider = messagerProvider;
        }
        /// <summary>
        /// Hàm thêm bài hát 
        /// </summary>
        /// <param name="file"></param>
        /// <returns></returns>
        /// <exception cref="Exception"></exception>
        /// Created by: nttue - 20/11/2023
        [HttpPost("add_song")]
        public async Task<IActionResult> AddSong(IFormFile file)
        {
            try
            {
                var result = await _songInterface.AddSong(file);

                _messagerProvider.SendingMessage<SongEntity>(result);                
                return Ok(result);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        /// <summary>
        /// Hàm cập nhật số lượt nghe, và bài truy cập gần đây
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("update_listen_of_number")]
        public async Task<IActionResult> UpdateNumberOfListens(Guid song_id, Guid user_id)
        {
            try
            {
                await _songInterface.UpdateNumberOfListens(song_id, user_id);

                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        /// <summary>
        /// Lấy ra các bài hát phổ biến
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpGet("get_trending")]
        public async Task<IActionResult> GetTrending()
        {
            try
            {
                var res = await _songInterface.GetTrendingAsync();
                return Ok(res);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        /// <summary>
        /// Hàm tìm kiếm các bài hát
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("search_song")]
        public async Task<IActionResult> SearchSong(FilterSongRequest request)
        {
            try
            {
                var res = await _songInterface.SearchSongAsync(request);
                return Ok(res);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        [HttpGet("like_song")]
        public async Task<IActionResult> LikeSong(Guid song_id, Guid user_id)
        {
            try
            {
                var song = await _songInterface.LikeSong(song_id, user_id);

                return Ok(song);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        [HttpGet("is_liked_song")]
        public async Task<IActionResult> CheckLikeSong(Guid song_id, Guid user_id)
        {
            try
            {
                var song = await _songInterface.CheckLikeSongAsync(song_id, user_id);

                return Ok(song);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        [HttpGet("get_song_by_random")]
        public async Task<IActionResult> GetSongByRandom()
        {
            try
            {
                var song = await _songInterface.GetSongByRandomAsync();

                return Ok(song);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        [HttpGet("get_song_liked_by_user")]
        public async Task<IActionResult> GetSongLikedByUser(Guid user_id)
        {
            try
            {
                var song = await _songInterface.GetSongLikedByUserAsync(user_id);

                return Ok(song);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
    }
}
