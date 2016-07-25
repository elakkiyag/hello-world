package com.newt.rabbit;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class ConsumeMessage {

	private static final String EXCHANGE_NAME = "Rabbit-Exchange-1";

	private static Channel channel;

	String custid;

	public String consume() throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String queueName = "RabbitMQueue";
		// String name = channel.queueDeclare().getQueue();

		String qu[] = { "rabbit-key" };

		for (String severity : qu) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
		}

		Consumer consumer = new DefaultConsumer(channel) {

			@SuppressWarnings("unchecked")
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				Map<String, String> map = (Map<String, String>) SerializationUtils.deserialize(body);
				System.out.println("[X] Received From Queue '" + envelope.getRoutingKey() + "'\n[x] Message Received :'"
						+ map.get("msg") + "'");
				custid = map.get("msg");
			}

		};

		channel.basicConsume(queueName, true, consumer);

		return custid;

	}
}
