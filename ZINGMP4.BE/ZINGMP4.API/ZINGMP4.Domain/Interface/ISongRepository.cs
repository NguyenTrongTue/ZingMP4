using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;

namespace ZINGMP4.Domain.Interface
{
    public interface ISongRepository<SongEntity> : IBaseCRUDRepostory<SongEntity>
    {
        Task <List<SongEntity>> GetTrending();
    }
}
