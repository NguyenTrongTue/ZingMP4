namespace ZINGMP4.API.Service
{
    public interface IMessagerProvider
    {
        public void SendingMessage<T>(T message);
    }
}
