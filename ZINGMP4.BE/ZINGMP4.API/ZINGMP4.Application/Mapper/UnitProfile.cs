using AutoMapper;
using ZINGMP4.Application.Dto;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Mapper
{
    public class UnitProfile : Profile
    {
        public UnitProfile()
        {
            // hàm chuyển đối tượng từ UnitInputDto sang Unit
            CreateMap<UserUpdateDto, UserEntity>();

            // hàm chuyển đối tượng từ UnitInputDto sang Unit
            CreateMap<UserEntity, UserDto>();
        }
    }
}

