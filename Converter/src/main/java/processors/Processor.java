package processors;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Apply some filtering or processing
 */
public interface Processor {
    //public abstract void process(InputStream input, OutputStream output, JsonNode jsonNode) throws Throwable;

    public abstract String process(String textToProcess, JsonNode configuration) throws Throwable;
}
