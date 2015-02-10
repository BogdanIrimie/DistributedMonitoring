package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import convertors.JobConverter;
import datamodel.Job;

import java.io.IOException;


public class Sender {
    private final String hostName;
    private final String queueName;
    private Connection connection;
    private RabbitMqConfig rmq = new RabbitMqConfig();
    private Channel channel;

    public Sender() {
        hostName = rmq.getHost();
        queueName = rmq.getSendQueue();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = true;

            channel.queueDeclare(queueName, durable, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Send(Job job) {

        try {
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    JobConverter.jobToJsonString(job).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() {

        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
