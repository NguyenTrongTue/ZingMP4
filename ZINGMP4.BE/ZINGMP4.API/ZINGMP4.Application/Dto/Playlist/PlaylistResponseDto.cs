using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Enum;

namespace ZINGMP4.Application.Dto.Playlist
{
    public class PlaylistResponseDto
    {
        public Guid playlist_id { get; set; } = Guid.Empty;
        public Guid user_id { get; set; } = Guid.Empty;

        public string username { get; set; } = string.Empty;
        public string playlist_name { get; set; } = string.Empty;

        public PlaybackMode playback_mode { get; set; } = PlaybackMode.Sequentially;

        public bool is_public { get; set; } = true;
        public string playlist_image { get; set; } = string.Empty;

        public List<SongEntity> song_entities { get; set; } = new List<SongEntity>();
    }
}
