package rabbit;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ReceiverTest {

    /**
     * Instantiate receiver many times.
     */
    @Test
    public void multipleInstantiation() {
        for (int i = 0; i < 10; i++) {
            Receiver receiver = new Receiver();
        }
    }

    /**
     * Close same connection multiple times.
     */
    @Test
    public void closeSameConnectionMultipleTimes() {
        Receiver receive = new Receiver();
        for (int i = 0; i < 10; i++) {
            receive.closeConnection();
        }
    }

}
