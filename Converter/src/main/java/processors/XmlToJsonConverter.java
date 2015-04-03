package processors;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert data from xml to json fromat.
 */
public class XmlToJsonConverter implements Processor{
    private static final Logger logger = LoggerFactory.getLogger(XmlToJsonConverter.class);

    /**
     * Converts String in xml format to json format
     *
     * @param xmlString contains xml data
     * @return json representation of XML
     */
    @Override
    public String process(String xmlString, JsonNode configuration) throws Throwable {
        String jsonString = null;
        try {
            JSONObject jsonObj = XML.toJSONObject(xmlString);
            jsonString = jsonObj.toString();
        }
        catch (JSONException e) {
            logger.error(e.getMessage(), e);
        }
        return jsonString;
    }

}
