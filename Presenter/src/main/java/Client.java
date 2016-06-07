import dmon.core.commons.helpers.CommandLineArgumentParser;
import dmon.core.commons.helpers.PidManipulation;
import dmon.core.commons.helpers.ProgramArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import rabbit.Receiver;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        MDC.put("pid", PidManipulation.getPid());
        logger.info("Presenter is started!");
        new CommandLineArgumentParser(args).parse();

        if (ProgramArguments.getPidFile() != null) {
            PidManipulation.writeOwnPidToFile(ProgramArguments.getPidFile());
        }
        else {
            PidManipulation.writeOwnPidToFile("../var/specs_monitoring_nmap_presenter.pid");
        }

        Receiver rec = new Receiver();
        rec.startReceiving();
        MDC.clear();
    }

    private static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }
}
