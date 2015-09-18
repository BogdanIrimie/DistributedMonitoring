package datamodel.monitoring;

public class PerformanceMonitoringResults {
    private String vmIp;
    private String jobId;
    private ComponentResults componentResults;
    private ComponentResults subcomponents;

    public ComponentResults getSubcomponents() {
        return subcomponents;
    }

    public void setSubcomponents(ComponentResults subcomponents) {
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

    public ComponentResults getComponentResults() {
        return componentResults;
    }

    public void setComponentResults(ComponentResults componentResults) {
        this.componentResults = componentResults;
    }
}
