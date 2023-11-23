using Dapper;
using Microsoft.Extensions.Configuration;
using Npgsql;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

namespace ZINGMP4.Infrastructure.Repository
{
    public class UserRepository : IUserRepository

    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IConfiguration _configuration;
        #region Constructor
        public UserRepository(IUnitOfWork unitOfWork, IConfiguration configuration)
        {
            _unitOfWork = unitOfWork;
            _configuration = configuration;
        }

        public Task<List<PlaylistEntity>> GetPlayListsAsync(Guid userId)
        {
            throw new NotImplementedException();
        }

        public Task<List<SongEntity>> GetRecentSongsAsync(Guid userId)
        {
            throw new NotImplementedException();
        }

        public Task<List<SongEntity>> GetSongsByPlayListAsync(Guid PlaylistId)
        {
            throw new NotImplementedException();
        }
        #endregion
        public Task<UserEntity> GetUserAsync(Guid Id)
        {
            throw new NotImplementedException();
        }

        public async Task<UserEntity> GetUserByEmailAsync(string email)
        {
            var param = new DynamicParameters();

            param.Add("email", email);
            var funtionName = "select * from public.user_verify where email = @email;";

            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<UserEntity>(funtionName, param, transaction: _unitOfWork.Transaction);

            return result;
        }

        public async Task<int> InsertUser(UserEntity userEntity)
        {
            var param = new DynamicParameters();
            var entityProperty = typeof(UserEntity).GetProperties();


            foreach (var property in entityProperty)
            {
                var paramName = property.Name;

                var paramValue = property.GetValue(userEntity);

                param.Add(paramName, paramValue);
            }

            var sql = "INSERT INTO public.user_verify (user_id, username, password_hash, password_salt, avatar, email) VALUES (@user_id, @username, @password_hash, @password_salt, @avatar, @email);";

            var result = await _unitOfWork.Connection.ExecuteAsync(sql, param);

            return result;

        }

        public async Task<int> UpdateUser(UserEntity userEntity)
        {
            var param = new DynamicParameters();
            var entityProperty = typeof(UserEntity).GetProperties();


            foreach (var property in entityProperty)
            {
                var paramName = property.Name;

                var paramValue = property.GetValue(userEntity);

                param.Add(paramName, paramValue);
            }

            var sql = "UPDATE public.user_verify " +
          "SET username = @username, password_hash = @password_hash, password_salt = @password_salt, avatar = @avatar, email = @email " +
          "WHERE user_id = @user_id;";


            var result = await _unitOfWork.Connection.ExecuteAsync(sql, param);

            return result;
        }
    }
}
