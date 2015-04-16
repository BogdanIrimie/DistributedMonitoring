package processors;

import com.fasterxml.jackson.databind.JsonNode;
import converters.JsonConverter;
import java.util.*;

public class TlsEcrypt2Level implements Processor {
    private static Map<String, Integer> ciphersLevel = new LinkedHashMap<String, Integer>();

    // the ciphers should be put in descending order of cipherLevel
    static {
        ciphersLevel.put("NULL", 0);                // 0 bit
        ciphersLevel.put("RC4_40", 1);              // 40 bit
        ciphersLevel.put("RC2_CBC_40", 1);          // 40 bit
        ciphersLevel.put("DES40_CBC", 1);           // 40 bit
        ciphersLevel.put("RC4_128_EXPORT40", 1);    // 40 bit
        ciphersLevel.put("DES_CBC", 1);             // 56 bit
        ciphersLevel.put("RC4_128", 7);             // 128 bit
        ciphersLevel.put("AES_128_CBC", 7);         // 128 bit
        ciphersLevel.put("AES_128_GCM", 7);         // 128 bit
        ciphersLevel.put("CAMELLIA_128_CBC", 7);    // 128 bit
        ciphersLevel.put("SEED_CBC", 7);            // 128 bit
        ciphersLevel.put("IDEA_CBC", 7);            // 128 bit
        ciphersLevel.put("3DES_EDE_CBC", 7);        // 168 bit
        ciphersLevel.put("AES_256_CBC", 8);         // 256 bit
        ciphersLevel.put("CAMELLIA_256_CBC", 8);    // 256 bit

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

}
