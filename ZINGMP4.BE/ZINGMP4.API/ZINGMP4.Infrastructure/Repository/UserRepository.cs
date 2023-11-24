using Dapper;
using Microsoft.Extensions.Configuration;
using Npgsql;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Infrastructure.Repository.Base;

namespace ZINGMP4.Infrastructure.Repository
{
    public class UserRepository : BaseRepository<UserEntity>, IUserRepository

    {
        private readonly IUnitOfWork _unitOfWork;
        #region Constructor
        public UserRepository(IUnitOfWork unitOfWork) : base(unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<UserEntity> EditUserInfoAsync(string user_name, string avatar_url, string email)
        {
            var param = new DynamicParameters();

            param.Add("username", user_name);
            param.Add("avatar", avatar_url);
            param.Add("email", email);

            var sql = "update public.user_verify set username = @username, avatar = @avatar where email = @email";

            await _unitOfWork.Connection.ExecuteAsync(sql, param);

            var sql1 = "select * from public.user_verify where email = @email";
            var newInfoUser = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<UserEntity>(sql1,
param);

            return newInfoUser;

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
