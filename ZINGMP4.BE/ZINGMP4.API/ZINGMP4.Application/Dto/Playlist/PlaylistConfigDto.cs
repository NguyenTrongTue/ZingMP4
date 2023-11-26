using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Application.Dto.Playlist
{
    public class PlaylistConfigDto
    {
        [Required]
        public Guid playlist_id { get; set; } = Guid.Empty;
        [Required]
        public Guid song_id { get; set; } = Guid.Empty;
    }
}
