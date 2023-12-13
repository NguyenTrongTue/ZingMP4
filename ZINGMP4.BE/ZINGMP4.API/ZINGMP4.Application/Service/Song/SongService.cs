using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using ZINGMP4.Application.Interface.Song;
using TagLib;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Application.Helper;
using ZINGMP4.Application.Request;
using ZINGMP4.Domain.Model;
using ZINGMP4.Domain.Cache;

namespace ZINGMP4.Application.Service
{
    public class SongService : ISongInterface
    {
        #region Fields
        private readonly IConfiguration _configuration;
        private readonly ISongRepository _songRepository;
        private readonly IRedisCache _redisCache;
        #endregion


        #region Constructor
        public SongService(IConfiguration configuration, ISongRepository songRepository, IRedisCache redisCache)
        {
            _configuration = configuration;
            _songRepository = songRepository;
            _redisCache = redisCache;
        }
        #endregion

        #region Functions
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

        public async Task UpdateNumberOfListens(Guid song_id, Guid user_id)
        {
            await _songRepository.UpdateNumberOfListens(song_id, user_id);
        }

        public async Task<List<SongEntity>> GetTrendingAsync()
        {
            string key = "trending";

            var result = await _redisCache.GetDataAsync<SongEntity>(key);

            if (result is null || result.Count == 0)
            {
                var res = await _songRepository.GetTrendingAsync();

                await _redisCache.SetDataAsync<SongEntity>(key, res);

                return res;
            } 
            return result;
        }

        public async Task<List<SongEntity>> SearchSongAsync(FilterSongRequest request)
        {
            var res = await _songRepository.SearchSongAsync(request.take, request.skip, request.filter);
            return res;

        }

        private async Task<SongEntity> WriteFile(IFormFile file)
        {
            try
            {
                string fileName = FileHelper.GenerateFileNameAsync(file);

                var baseUrl = _configuration.GetSection("BaseUrl");

                var exactPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/songs/lyrics", fileName);

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
                    FileHelper.SavePictureToFile(picture, Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/songs/images", $"{filePictureName}.jpg"));

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

        public async Task<SongModal> LikeSong(Guid song_id, Guid user_id)
        {
            var song = await _songRepository.LikeSong(song_id, user_id);

            return song;
        }

        public async Task<bool> CheckLikeSongAsync(Guid song_id, Guid user_id)
        {
            var song = await _songRepository.CheckLikeSongAsync(song_id, user_id);

            return song;
        }

        public async Task<SongEntity> GetSongByRandomAsync()
        {
            var result = await _songRepository.GetSongByRandomAsync();

            string key = "random_song";

            var preSong = new SongEntity();

            var newSong = new SongEntity();

            var cacheSong = await _redisCache.GetRecordAsync<SongEntity>(key);

            if (cacheSong != null)
            {
                preSong = cacheSong;
            }


            do
            {
                newSong = await _songRepository.GetSongByRandomAsync();
            } while (preSong != null && newSong.song_id == preSong.song_id);

            await _redisCache.SetRecordAsync<SongEntity>(key, newSong);
            
            return newSong;

        }

        public async Task<List<SongEntity>> GetSongLikedByUserAsync(Guid user_id)
        {
            var result = await _songRepository.GetSongLikedByUserAsync(user_id);

            return result;
        }
        #endregion
    }
}
