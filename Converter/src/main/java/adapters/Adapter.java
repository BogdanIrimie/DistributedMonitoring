package adapters;

import datamodel.Job;
import datamodel.Measurement;

public interface Adapter {
    public String adaptMessage(String message, Measurement measurement);
}