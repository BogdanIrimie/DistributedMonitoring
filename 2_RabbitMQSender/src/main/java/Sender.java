import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import convertors.JobConverter;
import convertors.MeasurementConvertor;
import datamodel.Measurement;
import datamodel.Job;
import mongo.MongoManager;

import java.io.IOException;
import java.util.Random;


public class Sender {

    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        // declare que
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        String message = null;
        for (int i = 0; i < 1; i++) {
            message = getMessage(args);
            Random rand = new Random();
            for (int j = 0; j < rand.nextInt(5); j++) {
                message += ".";
            }

            Measurement measurement = new Measurement("13", "nmap -T4 -A -v info.uvt.ro");
            String measurementString = MeasurementConvertor.measurementToJsonString(measurement);

            //put data in DB
            MongoManager mm = new MongoManager();
            String id = mm.pushJson(measurementString);
            String jsonRepresentation = mm.pullJsonById(id);
            System.out.println(jsonRepresentation);
            mm.closeConnection();

            channel.basicPublish("", QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, /*(message + i).getBytes()*/
                    //ObjToJsonConvertor.map(new Job("ls -l")).getBytes());
                    JobConverter.jobToJsonString(new Job(id)).getBytes());
                    //"nmap -T4 -A -v info.uvt.ro".getBytes());
                    Thread.sleep(50);
        }

        System.out.println("[X] Sent '" + message  + "' ");

        // close channel and connection
        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "This is it!";
        }
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }

        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

}
