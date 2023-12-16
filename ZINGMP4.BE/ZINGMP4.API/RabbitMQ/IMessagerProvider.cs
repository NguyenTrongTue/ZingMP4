namespace RabbitMQ
{
    public interface IMessagerProvider
    {
        public void SendingMessage<T>(T message);
    }
}
