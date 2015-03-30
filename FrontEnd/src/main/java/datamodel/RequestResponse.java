package datamodel;

/**
 * Response sent to a HTTP request.
 */
public class RequestResponse {

    private Status status;
    private String jobId;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
