package rabbit;

import org.junit.Test;

public class SenderTest {

    @Test
    public void multipleInstantiationTest() {
        for (int i = 0; i < 10; i++) {
            Sender sender = new Sender();
        }
    }

    @Test
    public void closeSameConnectionMultipleTimes() {
        Sender sender = new Sender();
        for (int i = 0; i < 10; i++) {
            sender.closeConnection();
        }
    }

    // no test for send ???

}
