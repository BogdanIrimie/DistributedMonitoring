package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Job;
import dmon.core.commons.rabbit.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * send Job to the next queue
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String queueName;
    private Connection connection;
    private Channel channel;

    /**
     * Initialize send queue.
     */
    public Sender() {
        RabbitMqConfig rmq = new RabbitMqConfig();
        String hostName = rmq.getHost();
        String userName = rmq.getUsername();
        String password = rmq.getPassword();
        queueName = rmq.getSendQueue();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setUsername(userName);
        factory.setPassword(password);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = true;

            channel.queueDeclare(queueName, durable, false, false, null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * send job over the queue
     *
     * @param job contains details about the job
     */
    public void send(Job job) {
        try {
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    JsonConverter.objectToJsonString(job).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Close queue connection
     */
    public void closeConnection() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
            if (connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
