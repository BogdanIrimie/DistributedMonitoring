package datamodel;

import java.util.Calendar;

/**
 * Performance data for the monitored process
 */
public class MonitoringResult {
    private Calendar timeFromLog;
    private String cpu;
    private String memory;
    private String networkIn;
    private String networkOut;

    public Calendar getTimeFromLog() {
        return timeFromLog;
    }

    public void setTimeFromLog(Calendar timeFromLog) {
        this.timeFromLog = timeFromLog;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getNetworkIn() {
        return networkIn;
    }

    public void setNetworkIn(String networkIn) {
        this.networkIn = networkIn;
    }

    public String getNetworkOut() {
        return networkOut;
    }

    public void setNetworkOut(String networkOut) {
        this.networkOut = networkOut;
    }
}
