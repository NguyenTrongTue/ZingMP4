using Dapper;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Infrastructure.Repository.Base;
using System.Data;
using ZINGMP4.Domain.Model;

namespace ZINGMP4.Infrastructure.Repository
{
    public class SongRepository : BaseRepository<SongEntity>, ISongRepository
    {

        #region Fields
        private readonly IUnitOfWork _unitOfWork;
        #endregion

        #region Constructor
        public SongRepository(IUnitOfWork unitOfWork) : base(unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<bool> CheckLikeSongAsync(Guid song_id, Guid user_id)
        {
            var param = new DynamicParameters();

            param.Add("p_song_id", song_id);
            param.Add("p_user_id", user_id);

            var sql = "select count(1) from public.liked_song_config where song_id = @p_song_id and user_id = @p_user_id";

            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<int>(sql, param);

            return result == 0 ? false : true;
        }

        public async Task<SongEntity> GetSongByIdAsync(Guid song_id)
        {
            var param = new DynamicParameters();
            param.Add("p_song_id", song_id);

            var sql = "select * from public.song where song_id = @p_song_id";
            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<SongEntity>(sql, param);
            return result;
        }

        public async Task<SongEntity> GetSongByRandomAsync()
        {
            var sql = "select * from public.song order by random() limit 1";

            var song = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<SongEntity>(sql);

            return song;
        }
        #endregion

        #region Functions
        public async Task<List<SongEntity>> GetTrendingAsync()
        {
            var sql = "select * from public.song order by number_of_listens desc limit 10";

            var res = await _unitOfWork.Connection.QueryAsync<SongEntity>(sql);

            return res.ToList();
        }

        public async Task<SongModal> LikeSong(Guid song_id, Guid user_id)
        {
            var param = new DynamicParameters();

            param.Add("p_song_id", song_id);
            param.Add("p_user_id", user_id);

            var sql = "select * from public.func_like_song(@p_song_id, @p_user_id)";

            var result = await _unitOfWork.Connection.QueryFirstOrDefaultAsync<SongModal>(sql, param, commandType: CommandType.Text);

            return result;
        }

        public async Task<List<SongEntity>> SearchSongAsync(int take, int skip, string filter)
        {
            var param = new DynamicParameters();


            param.Add("p_take", take);
            param.Add("p_skip", skip);
            param.Add("p_filter", filter);


            var sql = "select * from public.func_get_song_by_filter(@p_take, @p_skip, @p_filter)";

            var result = await _unitOfWork.Connection.QueryAsync<SongEntity>(sql, param, commandType: CommandType.Text);

            return result.ToList();
        }

        public async Task UpdateNumberOfListens(Guid song_id, Guid user_id)
        {
            var param = new DynamicParameters();


            param.Add("p_song_id", song_id);
            param.Add("p_user_id", user_id);
            param.Add("p_recently_played_id", Guid.NewGuid());
            param.Add("p_play_time", DateTime.Now);


            var sql = "select * from public.func_update_rencently_played(@p_recently_played_id, @p_song_id, @p_user_id, @p_play_time)";

            await _unitOfWork.Connection.ExecuteAsync(sql, param, commandType: CommandType.Text);
        }

        #endregion
    }
}
