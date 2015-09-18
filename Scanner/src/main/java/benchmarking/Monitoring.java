package benchmarking;


import converters.JsonConverter;
import datamodel.PerformanceSelfMonitoring;
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

    public PerformanceSelfMonitoring getResults(long pid) {
        PerformanceSelfMonitoring perfSelf = new PerformanceSelfMonitoring();
        perfSelf.setCpuUsageResults(performanceMonitoring.parseForPid(pid));
        perfSelf.setExecutionTime(totalTime);

        return perfSelf;
    }

    public void saveResultsInDb (long pid, MongoManager mm) {
        Thread writeRestuls = new Thread(new Runnable() {
            @Override
            public void run() {
                PerformanceSelfMonitoring perfSelf = new PerformanceSelfMonitoring();
                perfSelf.setCpuUsageResults(performanceMonitoring.parseForPid(pid));
                perfSelf.setExecutionTime(totalTime);
                mm.pushJson(JsonConverter.objectToJsonString(perfSelf), "selfPerformance");            }
        });
        writeRestuls.start();
    }

}
