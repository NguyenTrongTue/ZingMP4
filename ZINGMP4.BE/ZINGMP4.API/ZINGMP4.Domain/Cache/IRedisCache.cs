namespace ZINGMP4.Domain.Cache
{
    public interface IRedisCache
    {
        /// <summary>
        /// Hàm set dữ liệu là 1 list vào cache
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <param name="data"></param>
        /// <param name="absoluteExpireTime"></param>
        /// <param name="unusedExpireTime"></param>
        /// <returns></returns>
        Task SetDataAsync<T>(string key, List<T> data, TimeSpan? absoluteExpireTime = null, TimeSpan? unusedExpireTime = null);
        /// <summary>
        /// Hàm lấy dữ liệu từ cache
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <returns></returns>
        Task<List<T>> GetDataAsync<T>(string key);

        /// <summary>
        /// Hàm set dữ liệu là 1 bản ghi vào cache
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <param name="data"></param>
        /// <param name="absoluteExpireTime"></param>
        /// <param name="unusedExpireTime"></param>
        /// <returns></returns>
        Task SetRecordAsync<T>(string key, T data, TimeSpan? absoluteExpireTime = null, TimeSpan? unusedExpireTime = null);
        /// <summary>
        /// Hàm lấy dữ liệu từ cache
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <returns></returns>
        Task<T> GetRecordAsync<T>(string key);
    }
}
