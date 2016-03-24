package rabbit;

import com.rabbitmq.client.*;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Job;
import dmon.core.commons.rabbit.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * send Job to the next queue
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String delayedQueue;
    private Connection connection;
    private Channel channel;
    private static final String EXCHANGE_NAME = "delayedMessages";

    /**
     * Initialize send queue.
     */
    public Sender() {
        RabbitMqConfig rmq = new RabbitMqConfig();
        String hostName = rmq.getHost();
        String userName = rmq.getUsername();
        String password = rmq.getPassword();
        delayedQueue = rmq.getSendQueue();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setUsername(userName);
        factory.setPassword(password);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = true;

            Map<String, Object> exchangeArgs = new HashMap<String, Object>();
            exchangeArgs.put("x-delayed-type", "direct");
            channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true, false, exchangeArgs);
            channel.queueDeclare(delayedQueue, durable, false, false, null);
            channel.queueBind(delayedQueue, EXCHANGE_NAME, "");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * send job over the queue
     *
     * @param job contains details about the job
     */
    public void send(Job job, int delay) {

        try {
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("x-delay", delay * 1000);
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
            channel.basicPublish("my-exchange", "", props.build(),
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
