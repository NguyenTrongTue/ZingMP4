using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.Auth;

namespace ZingMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        #region Fields
        private readonly IAuthInterface _authInterface;
        #endregion
        public UserController(IConfiguration iconfiguration, IAuthInterface authInterface)
        {
            _authInterface = authInterface;
        }

        /// <summary>
        /// API đăng ký.
        /// </summary>
        /// <param name="userDto">Thông tin của người dùng</param>
        /// <returns>Trả về thông tin người dùng đã được đăng ký.</returns>
        [HttpPost("register")]
        public async Task<IActionResult> Register(UserUpdateDto userDto)
        {
            var result = await _authInterface.Register(userDto);

            return Ok(result);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        [HttpPost("login")]
        public async Task<IActionResult> Login(UserLoginDto userDto)
        {
            var result = await _authInterface.Login(userDto);

            return Ok(result);
        }
    }
}
