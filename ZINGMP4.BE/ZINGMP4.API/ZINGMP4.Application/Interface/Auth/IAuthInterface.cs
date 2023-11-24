using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Application.Request;

namespace ZINGMP4.Domain.Interface.Auth
{
    public interface IAuthInterface
    {

        Task<UserDto> Register(UserUpdateDto userDto);

        Task<UserDto> Login(UserLoginDto userLoginDto);

        Task<UserDto> EditUserInfoAsync(UserEditRequest userEditRequest);

    }
}
