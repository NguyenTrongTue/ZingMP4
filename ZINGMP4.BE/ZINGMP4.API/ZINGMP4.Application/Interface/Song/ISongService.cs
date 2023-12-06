using Microsoft.AspNetCore.Http;
using ZINGMP4.Application.Request;
using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Interface.Song
{
    public interface ISongInterface
    {
        /// <summary>
        /// Hàm thêm bài hát 
        /// </summary>
        /// <param name="file"></param>
        /// <returns></returns>        
        /// Created by: nttue - 20/11/2023
        Task<SongEntity> AddSong(IFormFile songDto);
        /// <summary>
        /// Hàm cập nhật số lượt nghe, và bài truy cập gần đây
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        Task UpdateNumberOfListens(Guid song_id, Guid user_id);

        /// <summary>
        /// Hàm lấy các bài hát trending
        /// </summary>
        /// <returns>Danh sách các bài hát</returns>
        /// Created by: nttue 20/11/2023
        Task<List<SongEntity>> GetTrendingAsync();

        /// <summary>
        /// Hàm tìm kiếm bài hát theo tên bài hát, tên ca sĩ.
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        /// Created by: nttue 20/11/2023
        Task<List<SongEntity>> SearchSongAsync(FilterSongRequest request);
    }
}
