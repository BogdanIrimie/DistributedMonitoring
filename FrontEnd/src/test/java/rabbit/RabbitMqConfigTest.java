package rabbit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RabbitMqConfigTest {

    /**
     * Retrieve host.
     */
    @Test
    public void retrieveHost() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String host = rmqConf.getHost();
        assertTrue(host.length() > 0);
    }

    /**
     * Retrieve queue name.
     */
    @Test
    public void retrieveQueue() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String queue = rmqConf.getQueue();
        assertTrue(queue.length() > 0);
    }

    /**
     * Retrieve username
     */
    @Test
    public void retrieveUser() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String user = rmqConf.getUsername();
        assertTrue(user.length() > 0);
    }

    /**
     * Retrieve password.
     */
    @Test
    public void retrievePassword() {
        RabbitMqConfig rmqConf = new RabbitMqConfig();
        String password = rmqConf.getPassword();
        assertTrue(password.length() > 0);
    }

}
