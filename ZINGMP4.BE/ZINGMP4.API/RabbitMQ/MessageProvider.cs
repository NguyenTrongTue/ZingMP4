﻿using RabbitMQ.Client;
using System.Text;
using System.Text.Json;

namespace RabbitMQ
{
    public class MessageProvider : IMessagerProvider
    {
        public void SendingMessage<T>(T message)
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

            channel.QueueDeclare("add_song", durable: true, exclusive: false);

            var jsonString = JsonSerializer.Serialize(message);

            var body = Encoding.UTF8.GetBytes(jsonString);

            channel.BasicPublish("", "add_song", body: body);


        }
    }
}
