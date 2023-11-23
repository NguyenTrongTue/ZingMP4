using Microsoft.AspNetCore.Http;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Interface.Song
{
    public interface ISongInterface
    {
        Task<SongEntity> AddSong(IFormFile songDto);
    }
}
