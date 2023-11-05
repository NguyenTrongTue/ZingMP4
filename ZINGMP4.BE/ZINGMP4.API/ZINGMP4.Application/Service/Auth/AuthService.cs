using AutoMapper;
using System.Security.Cryptography;
using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.User;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Domain.Interface.Auth;
using ZINGMP4.Domain.MISAException;
using ZINGMP4.Domain.Resource;
using Microsoft.Extensions.Configuration;

namespace ZINGMP4.Application.Service.Auth
{
    public class AuthService : IAuthInterface
    {
        protected readonly IMapper _mapper;
        private readonly IUserRepository _userRepository;
        private readonly IConfiguration _iconfiguration;


        public AuthService(IMapper mapper, IUserRepository userRepository, IConfiguration iconfiguration)
        {
            _mapper = mapper;
            _userRepository = userRepository;
            _iconfiguration = iconfiguration;
        }
        private static void CreatePassword(string password, out byte[] passwordHash, out byte[] passwordSalt)
        {
            try
            {
                using var hmac = new HMACSHA512();
                passwordSalt = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        private static bool VetifyPassword(string password, byte[] passwordHash, byte[] passwordSalt)
        {
            using var hmac = new HMACSHA512(passwordSalt);
            var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));

            return computedHash.SequenceEqual(passwordHash);
        }
           
        public async Task<UserDto> Login(UserLoginDto userLoginDto)
        {
            var userExists = await _userRepository.GetUserByEmailAsync(userLoginDto.email);
            if (userExists == null)
            {
                throw new ConflictException(Resource.UserNotExists);
            }
            if (!VetifyPassword(userLoginDto.password, userExists.password_hash, userExists.password_salt))
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
                CreatePassword(userDto.password, out byte[] passwordHash, out byte[] passwordSalt);
                var user = _mapper.Map<UserEntity>(userDto);

                user.password_hash = passwordHash;
                user.password_salt = passwordSalt;

                await _userRepository.InsertUser(user);

                var result = _mapper.Map<UserDto>(user);

                return result;
            }
            catch (Exception ex)
            {
                throw;
            }
        }

    }
}
