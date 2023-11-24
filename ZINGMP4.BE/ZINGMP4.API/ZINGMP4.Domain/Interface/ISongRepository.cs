using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;

namespace ZINGMP4.Domain.Interface
{
    public interface ISongRepository : IBaseCRUDRepostory<SongEntity>
    {
        Task<int> UpdateNumberOfListens(Guid song_id);

        Task<List<SongEntity>> GetTrendingAsync();
    }
}
