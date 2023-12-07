using Microsoft.AspNetCore.Http;
using System.ComponentModel.DataAnnotations;

namespace ZINGMP4.Application.Request
{
    public class UserEditRequest
    {
        [MinLength(4, ErrorMessage = "Tên người dùng tối thiểu 4 ký tự")]
        [MaxLength(20, ErrorMessage = "Tên đăng nhập người dùng tối đa 20 ký tự")]
        public string? user_name { get; set; }


        /// <summary>
        /// Email người dùng
        /// </summary>
        [Required, EmailAddress]
        public string email { get; set; } = string.Empty;
        public IFormFile avatar { get; set; }
    }
}
