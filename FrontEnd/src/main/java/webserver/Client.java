package webserver;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Client {

    public static void main(String[] args) {
        MDC.put("pid", getPid());
        //WebServer ws = new WebServer();
        SpringApplication.run(Client.class, args);
        MDC.clear();
    }

    private static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }
}
