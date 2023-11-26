using AutoMapper;
using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Domain.Interface.Auth;
using ZINGMP4.Domain.MISAException;
using ZINGMP4.Domain.Resource;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Application.Request;
using ZINGMP4.Application.Helper;

namespace ZINGMP4.Application.Service
{
    public class AuthService : IAuthInterface
    {
        protected readonly IMapper _mapper;
        private readonly IUserRepository _userRepository;
        private readonly IConfiguration _configuration;


        public AuthService(IMapper mapper, IUserRepository userRepository, IConfiguration iconfiguration)
        {
            _mapper = mapper;
            _userRepository = userRepository;
            _configuration = iconfiguration;
        }

        public async Task<UserDto> EditUserInfoAsync(UserEditRequest userEditRequest)
        {
            var file = userEditRequest.avatar;
            string fileName = FileHelper.GenerateFileNameAsync(file);
            var baseUrl = _configuration.GetSection("BaseUrl");

            var exactPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot\\songs\\lyrics", fileName);

            using (var stream = new FileStream(exactPath, FileMode.Create))
            {
                await file.CopyToAsync(stream);
            }

            var avatar_url = baseUrl.Value + "/users/" + fileName;


            var userEntity = await _userRepository.EditUserInfoAsync(userEditRequest?.user_name, avatar_url, userEditRequest.email);


            var result = _mapper.Map<UserDto>(userEntity);
            return result;
        }

        public async Task<UserDto> Login(UserLoginDto userLoginDto)
        {
            var userExists = await _userRepository.GetUserByEmailAsync(userLoginDto.email);
            if (userExists == null)
            {
                throw new ConflictException(Resource.UserNotExists);
            }
            if (!AuthHelper.VetifyPassword(userLoginDto.password, userExists.password_hash, userExists.password_salt))
            {
                throw new AuthException(Resource.IncorrectPassword);
            }

            var result = _mapper.Map<UserDto>(userExists);

            return result;
        }

        public async Task<UserDto> Register(UserUpdateDto userDto)
        {
            try
            {
                var userExists = await _userRepository.GetUserByEmailAsync(userDto.email);
                if (userExists != null)
                {
                    throw new NotFoundException(Resource.UserExists);
                }
                AuthHelper.CreatePassword(userDto.password, out byte[] passwordHash, out byte[] passwordSalt);
                var user = _mapper.Map<UserEntity>(userDto);

                user.password_hash = passwordHash;
                user.password_salt = passwordSalt;

                user.user_id = Guid.NewGuid();
                user.avatar = string.Empty;

                await _userRepository.InsertEntity(user);

                var result = _mapper.Map<UserDto>(user);

                return result;
            }
            catch (Exception)
            {
                throw;
            }
        }

    }
}
