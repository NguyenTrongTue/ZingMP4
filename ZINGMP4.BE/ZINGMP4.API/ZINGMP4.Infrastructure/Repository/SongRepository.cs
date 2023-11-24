using Dapper;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Infrastructure.Repository.Base;

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
        #endregion

        public Task AddSong(SongEntity song)
        {
            throw new NotImplementedException();
        }
        #region Functions
        public Task<SongEntity> DeleteEntity(SongEntity entity)
        {
            throw new NotImplementedException();
        }

        public Task<List<SongEntity>> Filter(int take, int skip, string searchKey)
        {
            throw new NotImplementedException();
        }

        public async Task<List<SongEntity>> GetTrendingAsync()
        {
            var sql = "select * from public.song order by number_of_listens desc";

            var res = await _unitOfWork.Connection.QueryAsync<SongEntity>(sql);

            return res.ToList();
        }

        public async Task<int> UpdateNumberOfListens(Guid song_id)
        {
            var param = new DynamicParameters();


            param.Add("song_id", song_id);


            var sql = "update public.song set number_of_listens = number_of_listens + 1 where song_id = @song_id";

            await _unitOfWork.Connection.ExecuteAsync(sql, param);

            return 1;
        }

        public Task<SongEntity> UpdateSong(SongEntity entity)
        {
            throw new NotImplementedException();
        }


        #endregion
    }
}
