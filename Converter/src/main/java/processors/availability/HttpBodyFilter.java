package processors.availability;

import com.fasterxml.jackson.databind.JsonNode;
import converters.JsonConverter;
import datamodel.HttpResponseForAvailability;
import processors.Processor;

public class HttpBodyFilter implements Processor {

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        HttpResponseForAvailability httpResponseForAvailability = JsonConverter.jsonStringToObject(textToProcess, HttpResponseForAvailability.class);
        return httpResponseForAvailability.getBody();
    }
}
