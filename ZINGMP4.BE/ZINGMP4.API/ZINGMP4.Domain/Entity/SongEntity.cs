using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ZINGMP4.Domain.Entity
{
    [Table("song")]
    public class SongEntity
    {
        [Required]
        [Key]
        public Guid song_id { get; set; } = Guid.Empty;

        [Required]
        public string song_name { get; set; } = string.Empty;

        [Required]
        public string singer_name { get; set; } = string.Empty;

        [Required]
        public string thumnail { get; set; } = string.Empty;
        [Required]
        public int number_of_listens { get; set; } = 0;

        public string location { get; set; } = string.Empty;

        [Required]
        public string link_song { get; set; } = string.Empty;

        public int liked { get; set; }
    }
}
