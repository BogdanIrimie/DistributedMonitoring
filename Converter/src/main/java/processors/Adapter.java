package processors;

import datamodel.Job;
import datamodel.Measurement;

public interface Adapter {
    public String adaptMessage(String message, Job job, Measurement measurement);
}
