package rabbit;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SenderTest {

    /**
     * Instantiate sender many times.
     */
    @Test
    public void multipleInstantiationTest() {
        for (int i = 0; i < 10; i++) {
            Sender sender = new Sender();
        }
    }

    /**
     * Close same connection multiple times.
     */
    @Test
    public void closeSameConnectionMultipleTimes() {
        Sender sender = new Sender();
        for (int i = 0; i < 10; i++) {
            sender.closeConnection();
        }
    }

    // no test for send ???

}
