using Dapper;
using System.ComponentModel.DataAnnotations.Schema;
using System.Reflection;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Domain.Interface.BaseCRUD;

namespace ZINGMP4.Infrastructure.Repository.Base
{
    public class BaseRepository<TEntity> : IBaseCRUDRepostory<TEntity>
    {
        #region Fields
        private readonly IUnitOfWork _unitOfWork;

        public virtual string tableName { get; set; } = typeof(TEntity).GetCustomAttribute<TableAttribute>(false).Name ?? typeof(TEntity).Name;
        #endregion

        #region Constructor
        public BaseRepository(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }
        #endregion

        public virtual async Task<TEntity> InsertEntity(TEntity entity)
        {
            var param = new DynamicParameters();
            var entityProperty = typeof(TEntity).GetProperties();
            

            var propertiesName = new List<string>();

            foreach (var property in entityProperty)
            {
                var paramName = property.Name;

                var paramValue = property.GetValue(entity);

                param.Add(paramName, paramValue);

                propertiesName.Add(paramName);
            }

            var sql = $"INSERT INTO public.{tableName} ({string.Join(",", propertiesName)}) VALUES ({string.Join(",", propertiesName.Select(item => $"@{item}"))})";


            var result = await  _unitOfWork.Connection.ExecuteAsync(sql, param);

            return entity;
        }

    }
}
