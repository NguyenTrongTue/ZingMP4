using Dapper;
using System.Data;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Domain.Modal;
using ZINGMP4.Infrastructure.Repository.Base;
using static Dapper.SqlMapper;

namespace ZINGMP4.Infrastructure.Repository
{
    public class PlaylistRepository : BaseRepository<PlaylistEntity>, IPlaylistRepository
    {
        #region Properties
        private readonly IUnitOfWork _unitOfWork; 
        #endregion
        #region Constructor
        public PlaylistRepository(IUnitOfWork unitOfWork) : base(unitOfWork)
        {
            _unitOfWork = unitOfWork;
        } 
        #endregion
        #region Functions
        public async Task AddSongToPlaylistAsync(PlaylistConfigEntity entity)
        {
            var param = new DynamicParameters();

            param.Add("song_id", entity.song_id);

            param.Add("playlist_id", entity.playlist_id);

            param.Add("playlist_config_id", entity.playlist_config_id);

            var sql = "insert into public.playlist_config(playlist_config_id,playlist_id, song_id) values(@playlist_config_id,@playlist_id, @song_id);";

            await _unitOfWork.Connection.ExecuteAsync(sql, param);
        }

        public async Task DeleteSongToPlaylistAsync(PlaylistConfigEntity entity)
        {
            var param = new DynamicParameters();

            param.Add("song_id", entity.song_id);

            param.Add("playlist_id", entity.playlist_id);

            var sql = "delete from public.playlist_config where song_id = @song_id and playlist_id = playlist_id;";

            await _unitOfWork.Connection.ExecuteAsync(sql, param);
        }

        public async Task<List<PlaylistModal>> GetPlaylistAsync(Guid playlist_id)
        {
            var param = new DynamicParameters();
            param.Add("p_playlist_id", playlist_id);

            var functionName = "select * from public.func_get_playlist_by_id(@p_playlist_id)";

            var result = await _unitOfWork.Connection.QueryAsync<PlaylistModal>(functionName, param, commandType: CommandType.Text);

            return result.ToList();

        } 
        #endregion
    }
}
