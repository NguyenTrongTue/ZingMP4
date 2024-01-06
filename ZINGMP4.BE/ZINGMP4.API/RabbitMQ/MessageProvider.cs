using RabbitMQ.Client;
using System.Text;
using System.Text.Json;

namespace RabbitMQ
{
    public class MessageProvider : IMessagerProvider
    {
        public void SendingMessage<T>(T message)
        {
           

        }
    }
}
