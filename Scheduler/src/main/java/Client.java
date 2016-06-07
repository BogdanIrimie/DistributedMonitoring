import dmon.core.commons.helpers.CommandLineArgumentParser;
import dmon.core.commons.helpers.PidManipulation;
import dmon.core.commons.helpers.ProgramArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import rabbit.Receiver;

public class Client {
    public static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        MDC.put("pid", PidManipulation.getPid());
        logger.info("Scanner is started!");

        new CommandLineArgumentParser(args).parse();
        if (ProgramArguments.getPidFile() != null) {
            PidManipulation.writeOwnPidToFile(ProgramArguments.getPidFile());
        }
        else {
            PidManipulation.writeOwnPidToFile("../var/specs_monitoring_nmap_scheduler.pid");
        }

        Receiver receiver = new Receiver();
        receiver.startReceiving();

        MDC.clear();
    }
}
