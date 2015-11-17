package processors;

import com.fasterxml.jackson.databind.JsonNode;
import converters.JsonConverter;
import datamodel.HttpResponseForAvailability;

public class HttpStatusCodeFilter implements Processor {

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        HttpResponseForAvailability httpResponseForAvailability = JsonConverter.jsonStringToObject(textToProcess, HttpResponseForAvailability.class);
        return httpResponseForAvailability.getStatusCode();
    }
}
