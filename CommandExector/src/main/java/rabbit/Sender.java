package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import convertors.JobConverter;
import convertors.MeasurementConverter;
import datamodel.Job;
import datamodel.Measurement;
import mongo.MongoManager;

import java.io.IOException;


public class Sender {
    private final String hostName;
    private final String queueName;
    private Connection connection;
    private RabbitMqConfig rmq = new RabbitMqConfig();
    private Channel channel;

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

    public void Send (String clientId, String message) {
        Measurement measurement = new Measurement(clientId, message);
        String measurementString = MeasurementConverter.measurementToJsonString(measurement);

        //put data in DB
        MongoManager mm = new MongoManager();
        String id = mm.pushJson(measurementString);
        String jsonRepresentation = mm.pullJsonById(id);
        System.out.println(jsonRepresentation);
        mm.closeConnection();;

        try {
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, /*(message + i).getBytes()*/
                    //ObjToJsonConvertor.map(new Job("ls -l")).getBytes());
                    JobConverter.jobToJsonString(new Job(id)).getBytes());
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
