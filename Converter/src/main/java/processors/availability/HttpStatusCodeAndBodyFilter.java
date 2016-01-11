package processors.availability;

import com.fasterxml.jackson.databind.JsonNode;
import processors.Processor;

/**
 * Extract status code and body for HTTP request.
 */
public class HttpStatusCodeAndBodyFilter implements Processor {

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        return textToProcess;
    }
}
