package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import converters.JsonConverter;
import datamodel.Measurement;
import datamodel.Job;
import mongo.MongoManager;

import java.io.IOException;

/**
 * send messages over queue
 */
public class Sender {
    private final String hostName;
    private final String queueName;
    private Connection connection;
    private RabbitMqConfig rmq = new RabbitMqConfig();
    private Channel channel;

    /**
     * Set parameters for RabbitMQ sender
     */
    public Sender() {
        hostName = rmq.getHost();
        queueName = rmq.getQueue();

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

    /**
     * send messages over queue and DB.
     * @param clientId id of the client
     * @param message command to be executed
     */
    public void send(String clientId, String message, String responseAddress) {
        Measurement measurement = new Measurement(clientId, message, responseAddress);
        String measurementString = JsonConverter.objectToJsonString(measurement);

        //put data in DB
        MongoManager mm = new MongoManager();
        String id = mm.pushJson(measurementString);
        String jsonRepresentation = mm.pullJsonById(id);
        System.out.println(jsonRepresentation);
        mm.closeConnection();

        try {
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    JsonConverter.objectToJsonString(new Job(id)).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
