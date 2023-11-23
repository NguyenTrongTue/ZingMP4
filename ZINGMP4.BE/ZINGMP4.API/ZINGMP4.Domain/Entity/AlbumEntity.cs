using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ZINGMP4.Domain.Entity
{
    [Table(name: "album")]
    public class AlbumEntity
    {
        [Required]
        public Guid album_id { get; set; } = Guid.Empty; 
        [Required]
        public Guid user_id { get; set; } = Guid.Empty;
        [Required]
        public Guid song_id { get; set; } = Guid.Empty;
    }
}
