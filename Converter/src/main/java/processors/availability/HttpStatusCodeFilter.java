package processors.availability;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import converters.JsonConverter;
import datamodel.HttpResponseForAvailability;
import processors.Processor;


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
        String filteredJson = writer.writeValueAsString(httpResponseForAvailability);

        return filteredJson;
    }

}
