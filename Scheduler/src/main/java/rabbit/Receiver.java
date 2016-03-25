package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Job;
import dmon.core.commons.datamodel.Measurement;
import dmon.core.commons.mongo.MongoManager;
import dmon.core.commons.rabbit.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * Received messages that come from a queue
 */
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private Channel channel;
    private QueueingConsumer consumer;
    private Connection connection;
    private MongoManager mm;

    /**
     * Set parameters for RabbitMQ receiver
     */
    public Receiver() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String hostName = rmqConf.getHost();
        String queueName = rmqConf.getReceiveQueue();
        String userName = rmqConf.getUsername();
        String password = rmqConf.getPassword();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setUsername(userName);
        factory.setPassword(password);

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

            // create connection to database
            mm = new MongoManager();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Listen for messages
     */
    public void startReceiving() {
        SenderWithDelay sender = new SenderWithDelay();
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Job job = JsonConverter.jsonStringToObject(message, Job.class);
                MDC.put("jobId", job.getId());
                logger.info("Received message over the queue");

                String measurementString = mm.pullJsonById(job.getId());
                Measurement measurement = JsonConverter.jsonStringToObject(measurementString, Measurement.class);

                measurement.get
                // TODO Add scheduling policies.

                // send job over the queue
                sender.send(job, 60);

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                logger.info("Sent message over queue.");
                MDC.remove("jobId");
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Close receiving connection.
     */
    public void closeConnection() {
        // close connection to database
        mm = new MongoManager();

        // close connection to message queue
        try {
            if (connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
