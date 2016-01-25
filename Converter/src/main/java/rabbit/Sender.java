package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import converters.JsonConverter;
import datamodel.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * send Job to the next queue
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String presenterQueue;
    private Connection connection;
    private Channel channel;
    private static final String EXCHANGE_NAME = "present";

    /**
     * Initialize send queue.
     */
    public Sender() {
        RabbitMqConfig rmq = new RabbitMqConfig();
        String hostName = rmq.getHost();
        String userName = rmq.getUsername();
        String password = rmq.getPassword();
        presenterQueue = rmq.getSendQueue();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setUsername(userName);
        factory.setPassword(password);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = true;

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(presenterQueue, durable, false, false, null);
            channel.queueBind(presenterQueue, EXCHANGE_NAME, "");
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
            channel.basicPublish(EXCHANGE_NAME, "",
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
