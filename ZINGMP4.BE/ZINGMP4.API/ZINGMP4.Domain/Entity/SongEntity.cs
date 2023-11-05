using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Domain.Entity
{
    public class SongEntity
    {
        [Required]
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
    }
}
