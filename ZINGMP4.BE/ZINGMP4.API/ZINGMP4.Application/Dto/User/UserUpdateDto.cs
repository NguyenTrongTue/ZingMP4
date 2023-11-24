using Microsoft.AspNetCore.Http;
using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Application.Dto
{
    public class UserUpdateDto
    {
        /// <summary>
        /// Tên đăng nhập của người dùng
        /// </summary>
        [MinLength(4, ErrorMessage = "Tên người dùng tối thiểu 4 ký tự")]
        [MaxLength(20, ErrorMessage = "Tên đăng nhập người dùng tối đa 20 ký tự")]
        [Required(ErrorMessage = "Tên đăng nhập của người dùng không được để trống")]
        public string username { get; set; } = string.Empty;

        /// <summary>
        /// Mật khẩu được mã hóa và lưu vào db.
        /// </summary>
        [MinLength(4, ErrorMessage = "Mật khẩu dùng tối thiểu 4 ký tự")]
        [MaxLength(20, ErrorMessage = "Mật khẩu người dùng tối đa 20 ký tự")]
        [Required(ErrorMessage = "Mật khẩu của người dùng không được để trống")]

        public string password { get; set; } = string.Empty;

        /// <summary>
        /// Email người dùng
        /// </summary>
        [Required, EmailAddress]
        public string email { get; set; } = string.Empty;
    }
}
