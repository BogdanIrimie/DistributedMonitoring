import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabbit.Receiver;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        logger.info("Converter is started!");
        Receiver rec = new Receiver();
        rec.startReceiving();
    }

}
