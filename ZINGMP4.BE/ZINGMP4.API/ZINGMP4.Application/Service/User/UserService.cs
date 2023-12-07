using ZINGMP4.Application.Interface;
using ZINGMP4.Domain.Entity;
using ZINGMP4.Domain.Interface;

namespace ZINGMP4.Application.Service
{
    public class UserService : IUserService
    {
        private readonly IUserRepository _userRepository;

        public UserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public async Task<List<SongEntity>> GetRecentlyPlayedAsync(Guid user_id)
        {
            var result = await _userRepository.GetRecentSongsAsync(user_id);
            return result;
            
        }
    }
}
