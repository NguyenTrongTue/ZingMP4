using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Domain.Entity
{
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
