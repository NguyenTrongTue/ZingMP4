using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Application.Request;

namespace ZINGMP4.Domain.Interface.Auth
{
    public interface IAuthService
    {
        /// <summary>
        /// Hàm đăng ký
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        Task<UserDto> Register(UserUpdateDto userDto);

        /// <summary>
        /// Login
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        Task<UserDto> Login(UserLoginDto userLoginDto);

        /// <summary>
        /// Thay đổi thông tin người dùng.
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        Task<UserDto> EditUserInfoAsync(UserEditRequest userEditRequest);

        Task<UserDto> GetUserInfoByIdAsync(Guid id);

        Task GetNewPasswordAsync(string email);
    }
}
