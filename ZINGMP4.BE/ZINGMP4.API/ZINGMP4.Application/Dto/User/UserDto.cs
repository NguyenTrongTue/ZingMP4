using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Application.Dto
{
    public class UserDto
    {
        /// <summary>
        /// Id người dùng
        /// </summary>
        [Required]
        public Guid user_id { get; set; } = Guid.Empty;
        /// <summary>
        /// Tên đăng nhập của người dùng
        /// </summary>
        
        public string username { get; set; } = string.Empty;
        /// <summary>
        /// Email người dùng
        /// </summary>
        [Required, EmailAddress]
        public string email { get; set; } = string.Empty;

        /// <summary>
        /// Ảnh đại diện của người dùng
        /// </summary>
        public string avatar { get; set; } = string.Empty;
    }
}
