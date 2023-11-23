using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Application.Interface.Song;
using TagLib;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

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
            }catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private async Task<SongEntity> WriteFile(IFormFile file)
        {
            string fileName = "";
            try
            {
                var extension = Path.GetExtension(file.FileName);
                var fileId = Guid.NewGuid();
                fileName = fileId + extension;

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
                    lyrics = songInfo["Lyrics"],
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
                var lyrics = file.Tag.Lyrics;


                result.Add("Title", title);
                result.Add("Artist", artist);
                result.Add("Album", album);
                result.Add("Lyrics", lyrics);
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


        private static void  SavePictureToFile(IPicture picture, string filePath)
        {
            try
            {
                using (FileStream fs = System.IO.File.Create(filePath))
                {
                    fs.Write(picture.Data.Data, 0, picture.Data.Count);
                }
            }
            catch(Exception)
            {
                throw;
            }
        }
    }
}
