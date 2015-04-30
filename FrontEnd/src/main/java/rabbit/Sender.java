package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import converters.JsonConverter;
import datamodel.Measurement;
import datamodel.Job;
import datamodel.Request;
import mongo.MongoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * send messages over queue
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String queueName;
    private Connection connection;
    private Channel channel;

    /**
     * Set parameters for RabbitMQ sender
     */
    public Sender() {
        RabbitMqConfig rmq = new RabbitMqConfig();
        String hostName = rmq.getHost();
        String userName = rmq.getUsername();
        String password = rmq.getPassword();
        queueName = rmq.getQueue();

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
     * @param request request from the client
     */
    public String send(Request request, String clientIp) {
        Measurement measurement =
                new Measurement(request.getClientId(), clientIp, request.getCommand(),
                        request.getResponseAddress(), request.getProcessors(), request.getAdapter());
        String measurementString = JsonConverter.objectToJsonString(measurement);

        //put data in DB
        MongoManager mm = new MongoManager();
        String id = mm.pushJson(measurementString);
        MDC.put("jobId", id);
        logger.info("Json pushed in DB: " + measurementString);
        MDC.remove("jobId");
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
