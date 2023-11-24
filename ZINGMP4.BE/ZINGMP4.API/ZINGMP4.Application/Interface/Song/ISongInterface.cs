using Microsoft.AspNetCore.Http;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Interface.Song
{
    public interface ISongInterface
    {
        Task<SongEntity> AddSong(IFormFile songDto);

        Task<int> UpdateNumberOfListens(Guid song_id);

        /// <summary>
        /// Hàm lấy các bài hát trending
        /// </summary>
        /// <returns>Danh sách các bài hát</returns>
        /// Created by: nttue 20/11/2023
        Task<List<SongEntity>> GetTrendingAsync();
    }
}
