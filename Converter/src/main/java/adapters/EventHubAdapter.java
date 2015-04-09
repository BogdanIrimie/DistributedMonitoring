package adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.JsonConverter;
import datamodel.EventHubMessage;
import datamodel.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class EventHubAdapter implements Adapter {
    private static final Logger logger = LoggerFactory.getLogger(EventHubAdapter.class);
    public static final UUID uuid = UUID.randomUUID();

    public String adaptMessage(String filteredJson, Measurement measurement) {
        String command = measurement.getCommand();
        String usedTool = command.substring(0, command.indexOf(' '));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = null;

        try {
            data = mapper.readTree(filteredJson);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        EventHubMessage eventHubMessage = new EventHubMessage();
        eventHubMessage.setComponent(uuid.toString());
        eventHubMessage.setObject(usedTool);
        eventHubMessage.setLabels(new String[]{"userId-" + measurement.getClientId()});
        eventHubMessage.setType(null);
        eventHubMessage.setData(data);
        eventHubMessage.setTimestamp((new Date().getTime()) / 1000);
        return JsonConverter.objectToJsonString(eventHubMessage);
    }
}
