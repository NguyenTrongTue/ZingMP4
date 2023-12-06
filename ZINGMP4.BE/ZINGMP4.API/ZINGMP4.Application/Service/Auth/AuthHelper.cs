using System.Security.Cryptography;
using System.Text;

namespace ZINGMP4.Application.Service
{
    public class AuthHelper
    {
        public static void CreatePassword(string password, out byte[] passwordHash, out byte[] passwordSalt)
        {
            try
            {
                using var hmac = new HMACSHA512();
                passwordSalt = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
            catch (Exception)
            {
                throw;
            }
        }

        public static bool VetifyPassword(string password, byte[] passwordHash, byte[] passwordSalt)
        {
            using var hmac = new HMACSHA512(passwordSalt);
            var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));

            return computedHash.SequenceEqual(passwordHash);
        }

        public static string GenerateRandomPassword()
        {
            const string lowerChars = "abcdefghijklmnopqrstuvwxyz";
            const string upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            const string digitChars = "0123456789";

            string allChars = lowerChars + upperChars + digitChars;

            Random random = new Random();
            StringBuilder password = new StringBuilder();

            for (int i = 0; i < 6; i++)
            {
                int randomIndex = random.Next(0, allChars.Length);
                password.Append(allChars[randomIndex]);
            }

            return password.ToString();
        }
    }
}
