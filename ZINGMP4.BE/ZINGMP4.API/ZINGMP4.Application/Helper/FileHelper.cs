using Microsoft.AspNetCore.Http;

namespace ZINGMP4.Application.Helper
{
    public class FileHelper
    {
        public static string GenerateFileNameAsync(IFormFile file)
        {
            string fileName = "";

            var extension = Path.GetExtension(file.FileName);
            var fileId = Guid.NewGuid();
            fileName = fileId + extension;

            return fileName;
        }
    }
}
