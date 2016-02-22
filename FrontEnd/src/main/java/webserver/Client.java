package webserver;

import dmon.core.commons.helpers.CommandLineArgumentParser;
import dmon.core.commons.helpers.PidManipulation;
import dmon.core.commons.helpers.ProgramArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        MDC.put("pid", PidManipulation.getPid());

        new CommandLineArgumentParser(args).parse();

        if (ProgramArguments.getPidFile() != null) {
            PidManipulation.writeOwnPidToFile(ProgramArguments.getPidFile());
        }
        else {
            PidManipulation.writeOwnPidToFile("../var/specs_monitoring_nmap_frontend.pid");
        }

        SpringApplication.run(Client.class, args);
        MDC.clear();
    }
}
