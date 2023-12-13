using Microsoft.Extensions.Caching.Distributed;
using System.Text.Json;
using ZINGMP4.Domain.Cache;

namespace ZINGMP4.Infrastructure.Cache
{
    public class RedisCache : IRedisCache
    {
        private readonly IDistributedCache _distributedCache;

        public RedisCache(IDistributedCache distributedCache)
        {
            _distributedCache = distributedCache;
        }

        public async Task SetDataAsync<T>(string key, List<T> data, TimeSpan? absoluteExpireTime = null, TimeSpan? unusedExpireTime = null)
        {
            var options = new DistributedCacheEntryOptions();

            options.AbsoluteExpirationRelativeToNow = absoluteExpireTime ?? TimeSpan.FromMinutes(
                10);

            options.SlidingExpiration = unusedExpireTime;

            var jsonData = JsonSerializer.Serialize(data);

            await _distributedCache.SetStringAsync(key, jsonData, options);
        }

        public async Task<List<T>> GetDataAsync<T>(string key)
        {
            var jsonData = await _distributedCache.GetStringAsync(key);

            if (jsonData is null)
            {
                return null;
            }

            return JsonSerializer.Deserialize<List<T>>(jsonData);
        }

        public async Task SetRecordAsync<T>(string key, T data, TimeSpan? absoluteExpireTime = null, TimeSpan? unusedExpireTime = null)
        {
            var options = new DistributedCacheEntryOptions();

            options.AbsoluteExpirationRelativeToNow = absoluteExpireTime ?? TimeSpan.FromMinutes(
                10);

            options.SlidingExpiration = unusedExpireTime;

            var jsonData = JsonSerializer.Serialize(data);

            await _distributedCache.SetStringAsync(key, jsonData, options);
        }

        public async Task<T> GetRecordAsync<T>(string key)
        {
            var jsonData = await _distributedCache.GetStringAsync(key);

            if (jsonData is null)
            {
                return default(T);
            }

            return JsonSerializer.Deserialize<T>(jsonData);
        }
    }
}
