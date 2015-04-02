package processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.EventHubMessage;

import java.io.InputStream;
import java.io.OutputStream;

public class TlsEventHubAdapter implements Processor {
    @Override
    public String  process(String input, JsonNode customConfiguration) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(input);
        JsonNode tableNode = rootNode.findPath("table");
        return tableNode.toString();
    }
}
