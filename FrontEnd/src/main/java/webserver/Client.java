package webserver;

import helpers.CommandLineArgumentParser;
import helpers.PidManipulation;
import helpers.ProgramArguments;
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
        PidManipulation.writeOwnPidToFile(ProgramArguments.getPidFile());

        SpringApplication.run(Client.class, args);
        MDC.clear();
    }
}
