using RabbitMQ.Client;
using System.Text;
using System.Text.Json;

namespace RabbitMQ
{
    public static class QueueProducer
    {

        public static void Publish(IModel channel)
        {
            channel.QueueDeclare("demo_queue", durable: true, exclusive: false);


            var message = new { Name = "Provider", Mesasge = "Trong Nguyen RabbitMQ" };

            var body = Encoding.UTF8.GetBytes(JsonSerializer.Serialize(message));

            channel.BasicPublish("", "demo_queue", null, body);
        }
    }
}
