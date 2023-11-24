using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;

namespace ZINGMP4.Domain.Interface
{
    public interface IUserRepository : IBaseCRUDRepostory<UserEntity>
    {
        Task<UserEntity> GetUserAsync(Guid Id);
        Task<UserEntity> GetUserByEmailAsync(string email);
        Task<UserEntity> EditUserInfoAsync(string user_name, string avatar_url, string email);
        Task<List<PlaylistEntity>> GetPlayListsAsync(Guid userId);

        Task<List<SongEntity>> GetSongsByPlayListAsync(Guid PlaylistId);

        Task<List<SongEntity>> GetRecentSongsAsync(Guid userId);

        Task<int> UpdateUser(UserEntity userEntity);


    }
}
