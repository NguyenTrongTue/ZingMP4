using Microsoft.AspNetCore.Mvc;
using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Application.Interface;
using ZINGMP4.Application.Request;
using ZINGMP4.Domain.Interface.Auth;

namespace ZingMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        #region Fields
        private readonly IAuthService _authService;
        private readonly IUserService _userService;
        #endregion
        public UserController(IAuthService authService, IUserService userService)
        {
            _authService = authService;
            _userService = userService;
        }

        /// <summary>
        /// API đăng ký.
        /// </summary>
        /// <param name="userDto">Thông tin của người dùng</param>
        /// <returns>Trả về thông tin người dùng đã được đăng ký.</returns>
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromForm] UserUpdateDto userDto)
        {
            var result = await _authService.Register(userDto);

            return Ok(result);
        }

        /// <summary>
        /// Login
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("login")]
        public async Task<IActionResult> Login(UserLoginDto userDto)
        {
            var result = await _authService.Login(userDto);

            return Ok(result);
        }

        /// <summary>
        /// Thay đổi thông tin người dùng.
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("edit_user_info")]
        public async Task<IActionResult> EditUserInfoAsync([FromForm] UserEditRequest userEdit)
        {
            var result = await _authService.EditUserInfoAsync(userEdit);

            return Ok(result);
        }

        /// <summary>
        /// Lấy các hát truy cập gần đây
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 20/11/2023
        [HttpGet("get_recently_played")]
        public async Task<IActionResult> GetRecentlyPlayed(Guid user_id)
        {
            var result = await _userService.GetRecentlyPlayedAsync(user_id);

            return Ok(result);
        }

        [HttpGet("get_new_password")]
        public async Task<IActionResult> GetNewPasswordAsync(string email)
        {
            try
            {
               await _authService.GetNewPasswordAsync(email);
                return Ok(1);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        [HttpGet("get_user_by_id")]
        public async Task<IActionResult> GetUserInfoById(Guid id)
        {
            try
            {
                var user = await _authService.GetUserInfoByIdAsync(id);
                return Ok(user);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

    }
}
