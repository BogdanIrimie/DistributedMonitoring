import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import rabbit.Receiver;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        MDC.put("pid", getPid());
        logger.info("Converter is started!");
        Receiver rec = new Receiver();
        rec.startReceiving();
        MDC.clear();
    }

    private static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }
}
