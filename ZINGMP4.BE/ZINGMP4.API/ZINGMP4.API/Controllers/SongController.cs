using Microsoft.AspNetCore.Mvc;
using ZINGMP4.Application.Dto.Song;
using ZINGMP4.Application.Interface.Song;

namespace ZINGMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SongController : ControllerBase
    {
        #region Fields
        private readonly ISongInterface _songInterface;
        #endregion
        public SongController(ISongInterface songInterface)
        {
            _songInterface = songInterface;
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("add_song")]
        public async Task<IActionResult> AddSong(IFormFile file)
        {
            try
            {
                var result = await _songInterface.AddSong(file);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("update_song")]
        public async Task<IActionResult> UpdateSong(SongDto songDto)
        {
            return Ok(1);
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("delete_song")]
        public async Task<IActionResult> DeleteSong(SongDto songDto)
        {
            return Ok(1);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("update_listen_of_number")]
        public async Task<IActionResult> UpdateNumberOfListens(Guid song_id)
        {
            try
            {
                var res = await _songInterface.UpdateNumberOfListens(song_id);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        /// <summary>
        /// 
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
    }
}
