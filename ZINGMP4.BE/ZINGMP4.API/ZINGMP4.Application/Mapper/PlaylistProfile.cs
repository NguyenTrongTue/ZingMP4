using AutoMapper;
using ZINGMP4.Application.Dto;
using ZINGMP4.Application.Dto.Playlist;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Modal;

namespace ZINGMP4.Application.Mapper
{
    public class PlaylistProfile : Profile
    {
        public PlaylistProfile()
        {

            CreateMap<PlaylistEntity, PlaylistEntity>();

            CreateMap<PlaylistDto, PlaylistEntity>();

            CreateMap<PlaylistModal, PlaylistResponseDto>();
            CreateMap<PlaylistResponseDto, PlaylistModal>();


        }
    }
}
