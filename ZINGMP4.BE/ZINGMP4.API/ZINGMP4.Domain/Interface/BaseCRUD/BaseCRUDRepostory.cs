namespace ZINGMP4.Domain.Interface.BaseCRUD
{
    public interface IBaseCRUDRepostory<TEntity>
    {
        /// <summary>
        /// Hàm insert 1 bản ghi vào database.
        /// </summary>
        /// <param name="entity"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        Task<TEntity> InsertEntity(TEntity entity);
    }
}
