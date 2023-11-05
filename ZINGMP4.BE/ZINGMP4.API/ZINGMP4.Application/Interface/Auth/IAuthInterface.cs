using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;

namespace ZINGMP4.Domain.Interface.Auth
{
    public interface IAuthInterface
    {

        Task<UserDto> Register(UserUpdateDto userDto);

        Task<UserDto> Login(UserLoginDto userLoginDto);

    }
}
