package benchmarking;


import converters.JsonConverter;
import datamodel.monitoring.PerformanceMonitoringResults;
import mongo.MongoManager;

public class Monitoring {
    PerformanceMonitoring performanceMonitoring = new PerformanceMonitoring();
    long startTime = -1, endTime = -1, totalTime = -1;

    public void startMonitoring() {
        performanceMonitoring.startMonitoring();
        startTime = System.currentTimeMillis();
    }

    public void stopMonitoring() {
        performanceMonitoring.stopMonitoring();
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
    }

    public void saveResultsInDb (long pid, MongoManager mm) {
        Thread writeRestuls = new Thread(new Runnable() {
            @Override
            public void run() {
                PerformanceMonitoringResults performanceMonitoringResults = new PerformanceMonitoringResults();
                performanceMonitoringResults.setSubcomponents(performanceMonitoring.parseForPid(pid));
                mm.pushJson(JsonConverter.objectToJsonString(performanceMonitoringResults), "selfPerformanceMonitoring");
            }
        });
        writeRestuls.start();
    }

}
