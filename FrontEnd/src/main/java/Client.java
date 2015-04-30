import org.slf4j.MDC;
import webserver.WebServer;

public class Client {

    public static void main(String[] args) {
        MDC.put("pid", getPid());
        WebServer ws = new WebServer();
        MDC.clear();
    }

    private static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }
}
