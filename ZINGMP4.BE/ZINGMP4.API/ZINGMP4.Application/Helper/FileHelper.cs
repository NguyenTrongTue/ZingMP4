using Microsoft.AspNetCore.Http;
using TagLib;

namespace ZINGMP4.Application.Helper
{
    public class FileHelper
    {
        /// <summary>
        /// Hàm tạo tên file
        /// </summary>
        /// <param name="file"></param>
        /// <returns></returns>
        /// Created by: nttue - 20/11/2023
        public static string GenerateFileNameAsync(IFormFile file)
        {
            string fileName = "";

            var extension = Path.GetExtension(file.FileName);
            var fileId = Guid.NewGuid();
            fileName = fileId + extension;

            return fileName;
        }

        /// <summary>
        /// Hàm lưu dữ liệu byte thành file.
        /// </summary>
        /// <param name="picture"></param>
        /// <param name="filePath"></param>
        /// Created by: nttue - 20/11/2023
        public static void SavePictureToFile(IPicture picture, string filePath)
        {
            try
            {
                using (FileStream fs = System.IO.File.Create(filePath))
                {
                    fs.Write(picture.Data.Data, 0, picture.Data.Count);
                }
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
