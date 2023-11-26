using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;

namespace ZINGMP4.Domain.Interface
{
    public interface IUserRepository : IBaseCRUDRepostory<UserEntity>
    {
        /// <summary>
        /// Hàm lấy ra tài khoản người dùng theo email
        /// </summary>
        /// <param name="email"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        Task<UserEntity> GetUserByEmailAsync(string email);

        /// <summary>
        /// Hàm sửa thông tin tài khoản người dùng
        /// </summary>
        /// <param name="email"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        Task<UserEntity> EditUserInfoAsync(string user_name, string avatar_url, string email);
        /// <summary>
        /// Lấy các hát truy cập gần đây
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 20/11/2023
        Task<List<SongEntity>> GetRecentSongsAsync(Guid userId);
    }
}
