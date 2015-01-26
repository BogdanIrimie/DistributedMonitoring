import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class Receiver {
    private Channel channel;
    QueueingConsumer consumer;
    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "task_queue";

    public Receiver() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        Connection connection = null;
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
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            consumer = new QueueingConsumer(channel);
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startReceiving() {
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                doWork(message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println("[X] Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doWork(String command) throws InterruptedException {
        Command cmd = new Command(command);
        cmd.execute();
    }
}
