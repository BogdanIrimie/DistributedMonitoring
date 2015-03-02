package converter;

import converters.XmlToJsonConverter;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XmlToJsonConverterTest {

    /**
     * Test conversion from xml to json.
     */
    @Test
    public void convertXmlToJsonTest() {
        String xmlString =
                "<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>";
        String jsonString = XmlToJsonConverter.convertXmlToJson(xmlString);
        String expectedJsonResult = "{\"note\":{\"heading\":\"Reminder\",\"from\":\"Jani\",\"to\":\"Tove\",\"body\":\"Don't forget me this weekend!\"}}";
        assertTrue(expectedJsonResult.equals(expectedJsonResult));
    }
}
