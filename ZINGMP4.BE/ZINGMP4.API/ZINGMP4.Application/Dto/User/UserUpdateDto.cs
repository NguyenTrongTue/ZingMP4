using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Application.Dto
{
    public class UserUpdateDto
    {
        /// <summary>
        /// Id người dùng
        /// </summary>
        public Guid user_id { get; set; } = Guid.Empty;
        /// <summary>
        /// Tên đăng nhập của người dùng
        /// </summary>
        public string username { get; set; } = string.Empty;

        /// <summary>
        /// Mật khẩu được mã hóa và lưu vào db.
        /// </summary>
        public string password { get; set; } = string.Empty;

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
