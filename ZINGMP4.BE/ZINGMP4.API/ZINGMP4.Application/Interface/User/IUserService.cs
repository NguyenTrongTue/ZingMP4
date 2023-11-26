using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Application.Interface
{
    public interface IUserService
    {
        /// <summary>
        /// Lấy các hát truy cập gần đây
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 20/11/2023
        Task<List<SongEntity>> GetRecentlyPlayedAsync(Guid user_id);
    }
}
