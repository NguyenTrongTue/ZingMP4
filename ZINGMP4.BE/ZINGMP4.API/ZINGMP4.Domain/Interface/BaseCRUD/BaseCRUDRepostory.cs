using ZINGMP4.Domain.Entity;

namespace ZINGMP4.Domain.Interface.BaseCRUD
{
    public interface IBaseCRUDRepostory<TEntity>
    {
        Task<List<TEntity>> Filter(int take, int skip, string searchKey);

        Task<TEntity> InsertEntity(TEntity entity);

        Task<TEntity> UpdateSong(TEntity entity);

        Task<TEntity> DeleteEntity(TEntity entity);
    }
}
