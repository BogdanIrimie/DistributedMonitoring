package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Measurement;
import dmon.core.commons.datamodel.Job;
import dmon.core.commons.datamodel.Request;
import dmon.core.commons.mongo.MongoManager;
import dmon.core.commons.rabbit.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * Send messages over queue and DB
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final String queueName;
    private Connection connection;
    private Channel channel;
    private MongoManager mm;

    /**
     * Set parameters for RabbitMQ sender
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

        mm = new MongoManager();

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
        String id = mm.pushJson(measurementString);
        MDC.put("jobId", id);
        logger.info("Json pushed in DB: " + measurementString);
        MDC.remove("jobId");

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
            // close connection to database
            mm.closeConnection();

            // close connection to message queue
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
