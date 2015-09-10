package benchmarking;


import datamodel.PerformanceSelfMonitoring;

public class Monitoring {
    CpuMonitor cpuMonitor = new CpuMonitor();
    long startTime = -1, endTime = -1, totalTime = -1;
    private PerformanceSelfMonitoring perfSelf = new PerformanceSelfMonitoring();

    public void startMonitoring() {
        cpuMonitor.startMonitoring();
        startTime = System.currentTimeMillis();
    }

    public void stopMonitoring() {
        cpuMonitor.stopMonitoring();
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
    }

    public PerformanceSelfMonitoring getResults(long pid) {
        perfSelf.setCpuUsageResults(cpuMonitor.parseForPid(pid));
        perfSelf.setExecutionTime(totalTime);

        return perfSelf;
    }

}
