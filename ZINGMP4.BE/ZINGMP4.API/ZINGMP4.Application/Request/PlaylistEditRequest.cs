using System.ComponentModel.DataAnnotations;
using ZINGMP4.Domain.Enum;

namespace ZINGMP4.Application.Request
{
    public class PlaylistEditRequest
    {
        [Required]
        public Guid playlist_id { get; set; } = Guid.Empty;
        [Required]
        public Guid user_id { get; set; } = Guid.Empty;

        public string playlist_name { get; set; } = string.Empty;

        public PlaybackMode playback_mode { get; set; } = PlaybackMode.Sequentially;

        public bool is_public { get; set; } = true;
    }
}
