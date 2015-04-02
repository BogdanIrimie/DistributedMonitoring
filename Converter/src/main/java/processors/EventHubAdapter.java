package processors;

import datamodel.EventHubMessage;
import datamodel.Job;
import datamodel.Measurement;

import java.util.Date;

public class EventHubAdapter {

    public static EventHubMessage createEventHubMessage(String data, Job job, Measurement measurement) {
        String command = measurement.getCommand();
        String usedTool = command.substring(0, command.indexOf(' '));

        EventHubMessage eventHubMessage = new EventHubMessage();
        eventHubMessage.setComponent(null);
        eventHubMessage.setObject(usedTool);
        eventHubMessage.setLabels(new String[]{"userId-" + measurement.getClientId()});
        eventHubMessage.setType(null);
        eventHubMessage.setData(data);
        eventHubMessage.setTimestamp(Long.toString((new Date().getTime()) / 1000));
        return eventHubMessage;
    }
}
