package rabbit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RabbitMqConfigTest {

    @Test
    public void retrieveHost() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String host = rmqConf.getHost();
        assertTrue(host.length() > 0);
    }

    @Test
    public void retrieveSendQueue() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String queue = rmqConf.getSendQueue();
        assertTrue(queue.length() > 0);
    }

    @Test
    public void retrieveRetrieveQueue() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String queue = rmqConf.getReceiveQueue();
        assertTrue(queue.length() > 0);
    }
}
