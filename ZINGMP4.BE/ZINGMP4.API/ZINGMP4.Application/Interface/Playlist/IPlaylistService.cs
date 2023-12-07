using ZINGMP4.Application.Dto.Playlist;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Modal;

namespace ZINGMP4.Application.Interface.Playlist
{
    public interface IPlaylistService
    {
        /// <summary>
        /// Hàm tạo playlist cho người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        Task<PlaylistEntity> AddPlaylistAsync(PlaylistDto playlistDto);
        /// <summary>
        /// Hàm thêm bài hát vào playlist người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        Task AddSongToPlaylistAsync(PlaylistConfigDto playlistConfig);

        /// <summary>
        /// Hàm thêm bài hát vào playlist người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        Task DeleteSongToPlaylistAsync(PlaylistConfigDto playlistConfig);
        /// <summary>
        /// Hàm lấy ra playlist của người dùng
        /// </summary>
        /// <param name="userDto"></param>
        /// <returns></returns>
        /// Created by: nttue 24/12/2023
        Task<PlaylistResponseDto> GetPlaylistAsync(Guid playlist_id);

        Task<List<PlaylistEntity>> GetPlaylistByUserAsync(Guid user_id);


    }
}
