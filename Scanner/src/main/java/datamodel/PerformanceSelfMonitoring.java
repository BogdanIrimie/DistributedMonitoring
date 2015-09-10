package datamodel;

public class PerformanceSelfMonitoring {
    private CpuUsageResults cpuUsageResults;
    private long executionTime;

    public CpuUsageResults getCpuUsageResults() {
        return cpuUsageResults;
    }

    public void setCpuUsageResults(CpuUsageResults cpuUsageResults) {
        this.cpuUsageResults = cpuUsageResults;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
