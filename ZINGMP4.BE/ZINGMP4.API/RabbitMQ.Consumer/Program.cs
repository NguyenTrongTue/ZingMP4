using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;

namespace RabbitMQ.Consumer
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
            channel.QueueDeclare("demo-queue",
                durable: true,
                exclusive: false,
                autoDelete: false,
                arguments: null);

            var consumer = new EventingBasicConsumer(channel);

            consumer.Received += (sender, e) =>
            {
                var body = e.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);

                Console.WriteLine(message);

            };

            channel.BasicConsume("demo-queue", true, consumer);
        }
    }
}
