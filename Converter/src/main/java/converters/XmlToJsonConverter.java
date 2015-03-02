package converters;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Convert data from xml to json fromat.
 */
public class XmlToJsonConverter {

    /**
     * Converts String in xml format to json format
     *
     * @param xmlString contains xml data
     * @return json representation of XML
     */
    public static String convertXmlToJson(String xmlString) {
        String jsonString = null;
        try {
            JSONObject jsonObj = XML.toJSONObject(xmlString);
            jsonString = jsonObj.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
