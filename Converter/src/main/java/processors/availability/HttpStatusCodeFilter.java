package processors.availability;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import datamodel.HttpResponseForAvailability;
import dmon.core.commons.converters.JsonConverter;
import processors.Processor;

/**
 * Extract status code form HTTP request.
 */
public class HttpStatusCodeFilter implements Processor {

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        HttpResponseForAvailability httpResponseForAvailability =
                JsonConverter.jsonStringToObject(textToProcess, HttpResponseForAvailability.class);

        ObjectMapper mapper = new ObjectMapper();
        String[] ignorableFieldNames = { "body"};
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("httpFilter",SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
        ObjectWriter writer = mapper.writer(filters);
        return writer.writeValueAsString(httpResponseForAvailability);
    }

}
