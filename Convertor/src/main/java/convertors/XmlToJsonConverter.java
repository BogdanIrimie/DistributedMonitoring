package convertors;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmlToJsonConverter {
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
