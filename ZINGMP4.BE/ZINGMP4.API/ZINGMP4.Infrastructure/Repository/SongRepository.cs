using Dapper;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

namespace ZINGMP4.Infrastructure.Repository
{
    public class SongRepository : ISongRepository
    {

        #region Fields
        private readonly IUnitOfWork _unitOfWork;
        private readonly IConfiguration _configuration;
        #endregion

        #region Constructor
        #endregion
     
        public SongRepository(IUnitOfWork unitOfWork, IConfiguration configuration)
        {
            _unitOfWork = unitOfWork;
            _configuration = configuration;
        }

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

        public Task<List<SongEntity>> GetTrending()
        {
            throw new NotImplementedException();
        }

        public async Task<SongEntity> InsertEntity(SongEntity songEnity)
        {
            
            var param = new DynamicParameters();
            var entityProperty = typeof(SongEntity).GetProperties();


            foreach (var property in entityProperty)
            {
                var paramName = property.Name;

                var paramValue = property.GetValue(songEnity);

                param.Add(paramName, paramValue);
            }

            var sql = "INSERT INTO public.song (song_id, song_name, singer_name, thumnail, number_of_listens, location, lyrics, link_song) VALUES (@song_id, @song_name, @singer_name, @thumnail, @number_of_listens, @location, @lyrics, @link_song);";

            await _unitOfWork.Connection.ExecuteAsync(sql, param);

            return songEnity;
        }

        public Task<SongEntity> UpdateSong(SongEntity entity)
        {
            throw new NotImplementedException();
        }

  
        #endregion
    }
}
