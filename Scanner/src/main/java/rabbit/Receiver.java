package rabbit;

import benchmarking.Monitoring;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import converters.JsonConverter;
import datamodel.*;
import executors.CommandExecutor;
import mongo.MongoManager;
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

            // create connection for database
            mm = new MongoManager();

        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Listen for messages
     */
    public void startReceiving() {
        Sender sender = new Sender();
        while (true) {
            try {
                //Monitoring scannerMonit = new Monitoring();
                //scannerMonit.startMonitoring();

                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Job job = JsonConverter.jsonStringToObject(message, Job.class);
                MDC.put("jobId", job.getId());
                logger.info("Received message over queue.");

                String measurementString = mm.pullJsonById(job.getId());

                Measurement measurement = JsonConverter.jsonStringToObject(measurementString, Measurement.class);

                // start monitoring activities
                //Monitoring nmapMonit = new Monitoring();
                //nmapMonit.startMonitoring();

                CommandPidAndResults results  = executeCommand(measurement.getCommand());

                // finalize monitoring activities
                //nmapMonit.stopMonitoring();
                //nmapMonit.saveResultsInDb(job.getId(), results.getCommadnPid(), mm, "nmap");

                String xmlResult = results.getCommandResults();
                mm.updateJsonWithId(job.getId(), "rawResult", xmlResult);

                // send job over the queue
                sender.send(job);
                //scannerMonit.stopMonitoring();
                //scannerMonit.saveResultsInDb(job.getId(), Long.parseLong(getPid()), mm, "scanner");

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                logger.info("Sent message over queue.");
                MDC.remove("jobId");
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Start a command with received message
     *
     * @param command terminal command that will be executed
     * @throws InterruptedException
     */
    private CommandPidAndResults executeCommand(String command) throws InterruptedException {
        CommandExecutor cmd = new CommandExecutor();
        return cmd.execute(new Command(command));
    }

    /**
     * Obtain the PID of the component
     *
     * @return PID of the component
     */
    private String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }

    /**
     * Close receiving connection.
     */
    public void closeConnection() {
        try {
            // close connection to databse
            mm.closeConnection();

            // close conection to message queue
            if (connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
