using AutoMapper;
using ZINGMP4.Application.Dto;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Mapper
{
    public class UserProfile : Profile
    {
        public UserProfile()
        {
           
            CreateMap<UserUpdateDto, UserEntity>();

       
            CreateMap<UserEntity, UserDto>();
        }
    }
}

