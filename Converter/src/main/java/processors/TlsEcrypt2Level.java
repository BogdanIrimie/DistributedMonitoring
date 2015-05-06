package processors;

import com.fasterxml.jackson.databind.JsonNode;
import converters.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class TlsEcrypt2Level implements Processor {
    public static final Logger logger = LoggerFactory.getLogger(TlsEcrypt2Level.class);
    private static Map<String, Integer> ciphersLevel = new LinkedHashMap<String, Integer>();

    public TlsEcrypt2Level() {
        // read from file the ciphersuites
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            BufferedReader br = new BufferedReader(new FileReader(classLoader.getResource("ciphersuites.csv").getFile()));
            String line;
            br.readLine();  // the first row with column head
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                ciphersLevel.put(columns[0].trim(), Integer.parseInt(columns[1].trim())); // columns[0] cipher; columns[1] encrypt2level
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
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
