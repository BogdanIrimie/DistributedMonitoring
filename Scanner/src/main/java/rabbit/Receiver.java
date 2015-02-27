package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import converters.JsonConverter;
import datamodel.Command;
import datamodel.Job;
import datamodel.Measurement;
import executors.CommandExecutor;
import executors.ResultFormat;
import mongo.MongoManager;

import java.io.IOException;

/**
 * Received messages that come from a queue
 */
public class Receiver {
    private Channel channel;
    private QueueingConsumer consumer;
    private Connection connection;
    private final String hostName;
    private final String queueName;
    private RabbitMqConfig rmqConf = new RabbitMqConfig();

    /**
     * Set parameters for RabbitMQ receiver
     */
    public Receiver() {
        hostName = rmqConf.getHost();
        queueName = rmqConf.getReceiveQueue();
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            /*
            * a client can be assigned only 1 message, the next message will be
            * assigned only after it finishes processing the first message
            */
            int prefetchCount = 1;

            channel.basicQos(prefetchCount);
            boolean durable = true;
            channel.queueDeclare(queueName, durable, false, false, null);
            consumer = new QueueingConsumer(channel);
            boolean autoAck = false;
            channel.basicConsume(queueName, autoAck, consumer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listen for messages
     */
    public void startReceiving() {
        Sender sender = new Sender();
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Job job = JsonConverter.jsonStringToObject(message, Job.class);

                MongoManager mm = new MongoManager();
                String measurementString = mm.pullJsonById(job.getId());

                // ugly remove of oid
                measurementString = measurementString.replace("{ \"$oid\" : \""  + job.getId() + "\"}", "\"" + job.getId() + "\"");

                Measurement measurement = JsonConverter.jsonStringToObject(measurementString, Measurement.class);
                String xmlResult = executeCommand(measurement.getCommand());

                mm.updateJsonWithId(job.getId(), "xmlDocument", xmlResult);
                mm.closeConnection();

                // send job over the queue
                sender.Send(job);

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println("[X] Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Start a command with received message
     *
     * @param command terminal command that will be executed
     * @throws InterruptedException
     */
    private String executeCommand(String command) throws InterruptedException {
        CommandExecutor cmd = new CommandExecutor();
        return cmd.execute(new Command(command));
    }

    /**
     * Close receiving connection.
     */
    public void closeConnection() {
        try {
            if (connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
