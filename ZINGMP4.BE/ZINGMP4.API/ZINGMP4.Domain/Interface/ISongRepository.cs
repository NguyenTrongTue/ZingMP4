using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;
using ZINGMP4.Domain.Model;

namespace ZINGMP4.Domain.Interface
{
    public interface ISongRepository : IBaseCRUDRepostory<SongEntity>
    {
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
        Task<List<SongEntity>> SearchSongAsync(int take, int skip, string filter);

        Task<SongModal> LikeSong(Guid song_id, Guid user_id);

        Task<bool> CheckLikeSongAsync(Guid song_id, Guid user_id);

        Task<SongEntity> GetSongByRandomAsync();
    }
}
