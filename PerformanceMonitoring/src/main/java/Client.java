import benchmarking.Monitoring;
import datamodel.ComponentPerformance;
import dmon.core.commons.mongo.MongoManager;

import java.awt.*;

public class Client {
    public static void main(String[] args) {
        Monitoring monit = new Monitoring();
        MongoManager mm = new MongoManager();

        ComponentPerformance cp = monit.getMonitoringEvent("123", mm);
        monit.addPerformanceResultsAndSave(cp, mm);
    }
}
