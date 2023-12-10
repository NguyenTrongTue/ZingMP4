using System.ComponentModel.DataAnnotations;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Enum;

namespace ZINGMP4.Domain.Modal
{
    public class PlaylistModal
    {
        public Guid playlist_id { get; set; } = Guid.Empty;
        public Guid user_id { get; set; } = Guid.Empty;

        public string username { get; set; } = string.Empty;
        public string playlist_name { get; set; } = string.Empty;

        public PlaybackMode playback_mode { get; set; } = PlaybackMode.Sequentially;

        public bool is_public { get; set; } = true;
        public Guid song_id { get; set; } = Guid.Empty;

        public string song_name { get; set; } = string.Empty;

        public string singer_name { get; set; } = string.Empty;

        public string thumnail { get; set; } = string.Empty;
        public int number_of_listens { get; set; } = 0;

        public string location { get; set; } = string.Empty;

        public string link_song { get; set; } = string.Empty;

        public int liked { get; set; } = 0;

    }
}
