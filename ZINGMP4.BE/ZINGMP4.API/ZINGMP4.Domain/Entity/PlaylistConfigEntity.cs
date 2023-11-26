using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ZINGMP4.Domain.Entity
{
    [Table("playlist_config")]
    public class PlaylistConfigEntity
    {
        [Required]
        [Key]
        public Guid playlist_config_id { get; set; } = Guid.Empty;
        [Required]
        public Guid playlist_id { get; set; } = Guid.Empty;
        [Required]
        public Guid song_id { get; set; } = Guid.Empty;
    }
}
