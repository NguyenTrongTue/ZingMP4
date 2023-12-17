using RabbitMQ.Client.Events;
using RabbitMQ.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RabbitMQ
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var factory = new ConnectionFactory()
            {
                HostName = "localhost",
                UserName = "guest",
                Password = "guest",
                VirtualHost = "/"
            };

            var connection = factory.CreateConnection();

            var channel = connection.CreateModel();

            channel.QueueDeclare("demo_queue", durable: true, exclusive: false);


            var consumer = new EventingBasicConsumer(channel);

            consumer.Received += (sender, e) =>
            {
                var body = e.Body.ToArray();

                var message = Encoding.UTF8.GetString(body);
                Console.WriteLine(message);

            };

            channel.BasicConsume("demo_queue", true, consumer);

        }
    }
}
