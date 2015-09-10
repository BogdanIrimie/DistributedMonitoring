package datamodel;

import java.util.Date;
import java.util.List;

public class CpuUsageResults {
    private long pid;
    private Date startTime;
    private List<String> cpuPerSecond;

    public CpuUsageResults(long pid, Date startTime, List<String> cpuPerSecond) {
        this.pid = pid;
        this.startTime = startTime;
        this.cpuPerSecond = cpuPerSecond;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<String> getCpuPerSecond() {
        return cpuPerSecond;
    }

    public void setCpuPerSecond(List<String> cpuPerSecond) {
        this.cpuPerSecond = cpuPerSecond;
    }
}
