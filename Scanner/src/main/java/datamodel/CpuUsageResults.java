package datamodel;

import java.util.Calendar;
import java.util.List;

public class CpuUsageResults {
    private long pid;
    private Calendar startTime;
    private List<String> cpuPerSecond;

    public CpuUsageResults(long pid, Calendar startTime, List<String> cpuPerSecond) {
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

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public List<String> getCpuPerSecond() {
        return cpuPerSecond;
    }

    public void setCpuPerSecond(List<String> cpuPerSecond) {
        this.cpuPerSecond = cpuPerSecond;
    }
}
