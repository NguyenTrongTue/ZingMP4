using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Application.Interface.Song;
using TagLib;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Application.Helper;

namespace ZINGMP4.Application.Service.Song
{
    public class SongService : ISongInterface
    {
        private readonly IConfiguration _configuration;
        private readonly ISongRepository _songRepository;
        public SongService(IConfiguration configuration, ISongRepository songRepository)
        {
            _configuration = configuration;
            _songRepository = songRepository;
        }

        public async Task<SongEntity> AddSong(IFormFile file)
        {
            try
            {
                var songEntity = await WriteFile(file);

                var result = await _songRepository.InsertEntity(songEntity);

                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private async Task<SongEntity> WriteFile(IFormFile file)
        {
            try
            {
                string fileName = FileHelper.GenerateFileNameAsync(file);

                var baseUrl = _configuration.GetSection("BaseUrl");

                var exactPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot\\songs\\lyrics", fileName);

                using (var stream = new FileStream(exactPath, FileMode.Create))
                {
                    await file.CopyToAsync(stream);
                }

                var songInfo = GetSongInfo(exactPath, baseUrl);

                var fullUrl = baseUrl.Value + "/songs/lyrics/" + fileName;

                return new SongEntity()
                {
                    song_id = Guid.NewGuid(),
                    singer_name = songInfo["Artist"],
                    song_name = songInfo["Title"],
                    number_of_listens = 0,
                    thumnail = songInfo["Thumnail"],
                    link_song = fullUrl

                };
            }
            catch (Exception)
            {
                throw;
            }
        }

        private Dictionary<string, string> GetSongInfo(string filePath, IConfigurationSection baseUrl)
        {
            try
            {
                var result = new Dictionary<string, string>();

                TagLib.File file = TagLib.File.Create(filePath);

                var title = file.Tag.Title;
                var artist = file.Tag.FirstPerformer;
                var album = file.Tag.Album;


                result.Add("Title", title);
                result.Add("Artist", artist);
                result.Add("Album", album);
                if (file.Tag.Pictures.Length > 0)
                {
                    // Get the first picture
                    IPicture picture = file.Tag.Pictures[0];

                    var filePictureName = Guid.NewGuid();

                    // Save the picture to a file
                    SavePictureToFile(picture, Path.Combine(Directory.GetCurrentDirectory(), "wwwroot\\songs\\images", $"{filePictureName}.jpg"));

                    var fullUrl = baseUrl.Value + "/songs/images/" + $"{filePictureName}.jpg";

                    result.Add("Thumnail", fullUrl);
                }


                return result;
            }
            catch (Exception)
            {
                throw;
            }
        }


        private static void SavePictureToFile(IPicture picture, string filePath)
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

        public async Task<int> UpdateNumberOfListens(Guid song_id)
        {
            var res = await _songRepository.UpdateNumberOfListens(song_id);

            return 1;
        }

        public async Task<List<SongEntity>> GetTrendingAsync()
        {
            var res = await _songRepository.GetTrendingAsync();

            return res;
        }
    }
}
