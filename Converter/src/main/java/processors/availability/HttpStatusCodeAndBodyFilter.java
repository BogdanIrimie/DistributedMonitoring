package processors.availability;

import com.fasterxml.jackson.databind.JsonNode;
import processors.Processor;

public class HttpStatusCodeAndBodyFilter implements Processor {

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        return textToProcess;
    }
}
