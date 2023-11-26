using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ZINGMP4.Domain.Entity
{
    [Table("user_verify")]
    public class UserEntity
    {
        /// <summary>
        /// Id người dùng
        /// </summary>
        [Key]
        public Guid user_id { get; set; } = Guid.Empty;
        /// <summary>
        /// Email người dùng
        /// </summary>
        [Required, EmailAddress]
        public string email { get; set; } = string.Empty;
        /// <summary>
        /// Mật khẩu được mã hóa và lưu vào db.
        /// </summary>
        public byte[] password_hash { get; set; }

        /// <summary>
        /// Mật khẩu này đảm bảo rằng nếu người dùng cùng 1 mật khẩu thì chúng sẽ được mã hóa khác nhau 
        /// Đảm bảo rằng trong db chúng được lưu khác nhau.
        /// </summary>
        public byte[] password_salt { get; set; }

        /// <summary>
        /// Tên đăng nhập của người dùng
        /// </summary>
        public string username { get; set; } = string.Empty;

        /// <summary>
        /// Ảnh đại diện của người dùng
        /// </summary>
        public string avatar { get; set; } = string.Empty;
    }
}
