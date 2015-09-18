package datamodel.monitoring;

import java.util.List;

public class PerformanceMonitoringResults {
    private String vmIp;
    private String jobId;
    private Component component;
    private List<Subcomponent> subcomponents;

    public List<Subcomponent> getSubcomponents() {
        return subcomponents;
    }

    public void setSubcomponents(List<Subcomponent> subcomponents) {
        this.subcomponents = subcomponents;
    }

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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
