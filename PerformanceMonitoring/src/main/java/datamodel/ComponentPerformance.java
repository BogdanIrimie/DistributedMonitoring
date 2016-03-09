package datamodel;

import java.util.Calendar;
import java.util.List;

/**
 * Data about the job that is monitored
 */
public class ComponentPerformance {
    private String vmIp;
    private String jobId;
    private String name;
    private long pid;
    private Calendar jobStartTime;
    private Calendar jobEndTime;
    private List<MonitoringResult> monitoringResults;

    public String getVmIp() {
        return vmIp;
    }

    public void setVmIp(String vmIp) {
        this.vmIp = vmIp;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public Calendar getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(Calendar jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public Calendar getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(Calendar jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public List<MonitoringResult> getMonitoringResults() {
        return monitoringResults;
    }

    public void setMonitoringResults(List<MonitoringResult> monitoringResults) {
        this.monitoringResults = monitoringResults;
    }
}
