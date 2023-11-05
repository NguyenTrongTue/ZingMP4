using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Domain.Interface
{
    public interface IUserRepository
    {
        Task<UserEntity> GetUserAsync(Guid Id);
        Task<UserEntity> GetUserByEmailAsync(string email);

        Task<List<PlaylistEntity>> GetPlayListsAsync(Guid userId);

        Task<List<SongEntity>> GetSongsByPlayListAsync(Guid PlaylistId);

        Task<List<SongEntity>> GetRecentSongsAsync(Guid userId);
        Task<int> InsertUser(UserEntity userEntity);

        Task<int> UpdateUser(UserEntity userEntity);
    }
}
