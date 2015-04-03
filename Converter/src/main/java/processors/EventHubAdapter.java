package processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.JsonConverter;
import datamodel.EventHubMessage;
import datamodel.Job;
import datamodel.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public class EventHubAdapter implements Adapter {
    private static final Logger logger = LoggerFactory.getLogger(EventHubAdapter.class);

    public String adaptMessage(String filteredJson, Job job, Measurement measurement) {
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
        eventHubMessage.setComponent(null);
        eventHubMessage.setObject(usedTool);
        eventHubMessage.setLabels(new String[]{"userId-" + measurement.getClientId()});
        eventHubMessage.setType(null);
        eventHubMessage.setData(data);
        eventHubMessage.setTimestamp(Long.toString((new Date().getTime()) / 1000));
        return JsonConverter.objectToJsonString(eventHubMessage);
    }
}
