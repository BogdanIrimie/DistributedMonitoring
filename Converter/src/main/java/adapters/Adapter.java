package adapters;

import dmon.core.commons.datamodel.Measurement;

public interface Adapter {
    public String adaptMessage(String message, Measurement measurement);
}
