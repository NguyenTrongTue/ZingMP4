using Dapper;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Infrastructure.Repository.Base;
using System.Data;

namespace ZINGMP4.Infrastructure.Repository
{
    public class UserRepository : BaseRepository<UserEntity>, IUserRepository

    {
        #region Properties
        private readonly IUnitOfWork _unitOfWork; 
        #endregion
        #region Constructor
        public UserRepository(IUnitOfWork unitOfWork) : base(unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }
        #endregion
        #region Functions
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

        public async Task<List<SongEntity>> GetRecentSongsAsync(Guid userId)
        {
            var param = new DynamicParameters();

            param.Add("p_user_id", userId);

            var sql = "select * from public.func_get_recently_played(@p_user_id)";

            var res = await _unitOfWork.Connection.QueryAsync<SongEntity>(sql, param, commandType: CommandType.Text);

            return res.ToList();
        }

        public async Task<UserEntity> GetUserByEmailAsync(string email)
        {
            var param = new DynamicParameters();

            param.Add("email", email);
            var funtionName = "select * from public.user_verify where email = @email;";

            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<UserEntity>(funtionName, param, transaction: _unitOfWork.Transaction);

            return result;
        }

        public async Task<UserEntity> GetUserInfoByIdAsync(Guid id)
        {
            var param = new DynamicParameters();
            param.Add("user_id", id);
            var funtionName = "select * from public.user_verify where user_id = @user_id;";
            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<UserEntity>(funtionName, param, transaction: _unitOfWork.Transaction);

            return result;
        }

        public async Task UpdatePasswordAsync(string email, byte[] password_hash, byte[] password_salt)
        {
            var param = new DynamicParameters();
            param.Add("email", email);
            param.Add("password_hash", password_hash);
            param.Add("password_salt", password_salt);
            var funtionName = "update public.user_verify set password_hash = @password_hash, password_salt = @password_salt where email = @email;";

            await _unitOfWork.Connection.ExecuteAsync(funtionName, param);
        }
        #endregion

    }
}
