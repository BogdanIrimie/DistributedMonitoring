package start;

/**
 * start.Job that will be passed through the queues.
 */
public class Job {
    /**
     * The ID where data can be found in DB
     */
    private String jobId;

    public Job() {}

    public Job(String id) {
        this.jobId = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
