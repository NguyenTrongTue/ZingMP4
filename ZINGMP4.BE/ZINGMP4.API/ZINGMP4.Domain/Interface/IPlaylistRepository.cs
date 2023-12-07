using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface.BaseCRUD;
using ZINGMP4.Domain.Modal;

namespace ZINGMP4.Domain.Interface
{
    public interface IPlaylistRepository : IBaseCRUDRepostory<PlaylistEntity>
    {
        Task AddSongToPlaylistAsync(PlaylistConfigEntity playlistConfigEntity);

        Task DeleteSongToPlaylistAsync(PlaylistConfigEntity playlistConfigEntity);
        Task<List<PlaylistModal>> GetPlaylistAsync(Guid playlist_id);

        Task<List<PlaylistEntity>> GetPlaylistByUserAsync(Guid user_id);
    }
}
