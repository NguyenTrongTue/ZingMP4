using System.Data.Common;
namespace ZINGMP4.Domain.Interface
{
    public interface IUnitOfWork : IDisposable, IAsyncDisposable
    {
        #region Properties
        DbConnection Connection { get; }
        DbTransaction? Transaction { get; }
        #endregion

        #region Methods
        /// <summary>
        ///  Hàm mở transaction đồng bộ
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)
        void BeginTransaction();
        /// <summary>
        ///  Hàm mở transaction bất đồng bộ
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)
        Task BeginTransactionAsync();

        /// <summary>
        ///  Hàm commit transaction đồng bộ
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)
        void Commit();

        /// <summary>
        /// Hàm commit transaction bất đồng bộ
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)
        Task CommitAsync();
        /// <summary>
        /// Hàm rollback xử lý đồng bộ transaction
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)

        void Rollback();
        /// <summary>
        /// Hàm rollback xử lý bất đồng bộ transaction
        /// </summary>
        /// <returns></returns>
        /// Created by: nttue(20/07/2023)
        Task RollbackAsync();
        #endregion

    }
}
