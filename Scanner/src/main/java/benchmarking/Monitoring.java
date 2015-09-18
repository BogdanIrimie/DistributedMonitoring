package benchmarking;


import converters.JsonConverter;
import datamodel.monitoring.ComponentPerformance;
import mongo.MongoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

public class Monitoring {
    private final static Logger logger = LoggerFactory.getLogger(Monitoring.class);
    PerformanceMonitoring performanceMonitoring = new PerformanceMonitoring();
    Calendar startTime, endTime;

    public void startMonitoring() {
        performanceMonitoring.startMonitoring();
        startTime = Calendar.getInstance();
    }

    public void stopMonitoring() {
        performanceMonitoring.stopMonitoring();
        endTime = Calendar.getInstance();
    }

    public void saveResultsInDb (String jobId, long pid, MongoManager mm,String componentName) {
        Thread writeRestuls = new Thread(new Runnable() {
            @Override
            public void run() {
                ComponentPerformance componentPerformance = new ComponentPerformance();
                try {
                    componentPerformance.setVmIp(InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    logger.error(e.getMessage(), e);
                }
                componentPerformance.setJobId(jobId);
                componentPerformance.setPid(pid);
                componentPerformance.setName(componentName);
                componentPerformance.setJobStartTime(startTime);
                componentPerformance.setJobEndTime(endTime);
                componentPerformance.setMonitoringResults(performanceMonitoring.parseForPid(pid));
                mm.pushJson(JsonConverter.objectToJsonString(componentPerformance), "selfPerformanceMonitoring");
            }
        });
        writeRestuls.start();
    }

}
