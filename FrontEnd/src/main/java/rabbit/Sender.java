package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import converters.JsonConverter;
import datamodel.Measurement;
import datamodel.Job;
import mongo.MongoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * send messages over queue
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String hostName;
    private final String queueName;
    private final String userName;
    private final String password;
    private Connection connection;
    private RabbitMqConfig rmq = new RabbitMqConfig();
    private Channel channel;

    /**
     * Set parameters for RabbitMQ sender
     */
    public Sender() {
        hostName = rmq.getHost();
        queueName = rmq.getQueue();
        userName = rmq.getUsername();
        password = rmq.getPassword();

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
     * Send messages over queue and DB.
     * @param clientId id of the client
     * @param message command to be executed
     */
    public String send(String clientId, String message, String responseAddress) {
        Measurement measurement = new Measurement(clientId, message, responseAddress);
        String measurementString = JsonConverter.objectToJsonString(measurement);

        //put data in DB
        MongoManager mm = new MongoManager();
        String id = mm.pushJson(measurementString);
        logger.info("Json pushed in DB: " + measurementString);
        mm.closeConnection();

        try {
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    JsonConverter.objectToJsonString(new Job(id)).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Close connection used for sending messages.
     */
    public void closeConnection() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
            if (connection.isOpen()) {
                connection.close();
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
