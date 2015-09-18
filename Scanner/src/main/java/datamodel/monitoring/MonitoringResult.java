package datamodel.monitoring;

public class MonitoringResult {
    private String cpu;
    private String memory;
    private String networkIn;
    private String networkOut;

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
