package processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.JsonConverter;
import datamodel.EventHubMessage;
import datamodel.Job;
import datamodel.Measurement;

import java.io.IOException;
import java.util.Date;

public class EventHubAdapter {

    public static String createEventHubMessage(String filteredJson, Job job, Measurement measurement) throws IOException {
        String command = measurement.getCommand();
        String usedTool = command.substring(0, command.indexOf(' '));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(filteredJson);

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
