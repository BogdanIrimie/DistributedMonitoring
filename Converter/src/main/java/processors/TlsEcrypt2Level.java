package processors;

import com.fasterxml.jackson.databind.JsonNode;
import converters.JsonConverter;

import java.util.*;

public class TlsEcrypt2Level implements Processor {
    private static Map<String, Integer> ciphersLevel = new LinkedHashMap<String, Integer>();

    static {
        ciphersLevel.put("3DES_EDE", 6);
        ciphersLevel.put("RC4_128", 7);
        ciphersLevel.put("AES_128", 7);
        ciphersLevel.put("AES_256", 8);
    }

    private class Encrypt2Details {
        private String weakestCipher;
        private int ecript2Level;

        Encrypt2Details(String weakestCipher, int ecript2Level) {
            this.weakestCipher = weakestCipher;
            this.ecript2Level = ecript2Level;
        }

        public String getWeakestCipher() {
            return weakestCipher;
        }

        public void setWeakestCipher(String weakestCipher) {
            this.weakestCipher = weakestCipher;
        }

        public int getEcript2Level() {
            return ecript2Level;
        }

        public void setEcript2Level(int ecript2Level) {
            this.ecript2Level = ecript2Level;
        }
    }

    @Override
    public String process(String textToProcess, JsonNode configuration) throws Throwable {
        Iterator it = ciphersLevel.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
            String cipher = entry.getKey();

            if (textToProcess != null && textToProcess.contains(cipher)) {
                int level = entry.getValue();
                Encrypt2Details encrypt2Details = new Encrypt2Details(cipher, level);
                return JsonConverter.objectToJsonString(encrypt2Details);
            }
        }
        Encrypt2Details encrypt2Details = new Encrypt2Details(null, -1);
        return JsonConverter.objectToJsonString(encrypt2Details);
    }

}
