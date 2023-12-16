using RabbitMQ.Client;
using System.Text;
using System.Text.Json;

namespace RabbitMQ
{
    static class Program
    {

        static void Main(string[] args)
        {
            var factory = new ConnectionFactory
            {
                Uri = new Uri("ampq://guest:guest@localhost:5672")
            };
            using var connection = factory.CreateConnection();
            using var channel = connection.CreateModel();
            channel.QueueDeclare("demo-queue", durable: true, exclusive: false, autoDelete: false, arguments: null);

            var message = new { Name = "Provider", Message = "Hello RabbitMQ" };

            var body = Encoding.UTF8.GetBytes(JsonSerializer.Serialize(message));

            channel.BasicPublish("", "demo-queue", null, body);
        }
    }
}
