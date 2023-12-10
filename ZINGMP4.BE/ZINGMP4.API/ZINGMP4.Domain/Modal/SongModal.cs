using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Domain.Model
{
    public class SongModal
    {
        public Guid tmp_song_id { get; set; } = Guid.Empty;

       
        public string song_name { get; set; } = string.Empty;

       
        public string singer_name { get; set; } = string.Empty;

       
        public string thumnail { get; set; } = string.Empty;
       
        public int number_of_listens { get; set; } = 0;

        public string location { get; set; } = string.Empty;

       
        public string link_song { get; set; } = string.Empty;

        public int tmp_liked { get; set; }

        public bool is_liked { get; set; }
    }
}
