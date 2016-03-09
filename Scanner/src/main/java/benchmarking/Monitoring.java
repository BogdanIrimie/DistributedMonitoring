package benchmarking;

import datamodel.monitoring.ComponentPerformance;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.mongo.MongoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;


/**
 * Start timer and save monitoring data to DB
 */
public class Monitoring {
    private final static Logger logger = LoggerFactory.getLogger(Monitoring.class);
    Calendar startTime, endTime;

    /**
     * Set start time of monitoring
     */
    public void startMonitoring() {
        startTime = Calendar.getInstance();
    }

    /**
     * Set end time of monitoring
     */
    public void stopMonitoring() {
        endTime = Calendar.getInstance();
    }

    /**
     * Save monitoring results to DB
     * @param jobId of the monitored job
     * @param pid of the monitored process
     * @param mm object for database writing
     * @param componentName of the monitored component
     */
    public void saveResultsInDb (String jobId, long pid, MongoManager mm, String componentName) {
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
                //componentPerformance.setMonitoringResults(performanceMonitoring.parseForPid(pid));
                mm.pushJson(JsonConverter.objectToJsonString(componentPerformance), "selfPerformanceMonitoring");
            }
        });
        writeRestuls.start();
    }

}
