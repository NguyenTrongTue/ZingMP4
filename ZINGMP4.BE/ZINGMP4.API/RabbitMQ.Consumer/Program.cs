using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;

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

        using var channel = connection.CreateModel();

        channel.QueueDeclare("demo_queue", durable: true, exclusive: false);


        var consumer = new EventingBasicConsumer(channel);

        consumer.Received += (model, eventArgs) =>
        {

            var body = eventArgs.Body.ToArray();

            var message = Encoding.UTF8.GetString(body);

            Console.WriteLine(message);

        };

        channel.BasicConsume("demo_queue", true, consumer);

    }
}

