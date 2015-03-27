package datamodel;

/**
 * Response sent to a HTTP request.
 */
public class RequestResponse {

    private String status;
    private String jobId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
