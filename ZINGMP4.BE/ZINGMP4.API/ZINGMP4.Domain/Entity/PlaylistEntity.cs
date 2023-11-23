﻿using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ZINGMP4.Domain.Entity
{
    [Table("playlist")]
    public class PlaylistEntity
    {
        [Required]
        public Guid playlist_id { get; set; } = Guid.Empty;
        [Required]
        public Guid user_id { get; set; } = Guid.Empty;
        [Required]
        public Guid song_id { get; set; } = Guid.Empty;
        public string playlist_name { get; set; } = string.Empty;
        public string playlist_image { get; set; } = string.Empty;
    }
}
