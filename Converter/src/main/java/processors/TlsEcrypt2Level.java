package processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVReader;
import converters.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class TlsEcrypt2Level implements Processor {
    public static final Logger logger = LoggerFactory.getLogger(TlsEcrypt2Level.class);
    private static Map<String, Integer> ciphersLevel = new LinkedHashMap<String, Integer>();
    private List<Ciphersuite> ciphersuites;

    public TlsEcrypt2Level() {
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader br = null;
        ciphersuites = new ArrayList<Ciphersuite>();
        try {
            CSVReader reader = new CSVReader(new FileReader(classLoader.getResource("ciphersuites2.csv").getFile()));
            reader.readNext();
            String[] attributes;
            while ((attributes = reader.readNext()) != null) {
                Ciphersuite ciphersuite = new Ciphersuite();

                ciphersuite.setCipherId(Integer.parseInt(attributes[0].trim().substring(2), 16));
                ciphersuite.setName(attributes[1]);
                ciphersuite.setProtocol(attributes[2]);
                ciphersuite.setKeyExchange(attributes[3]);
                ciphersuite.setAuthentication(attributes[4]);
                ciphersuite.setSymetricEncryptionAlgorithm(attributes[5]);
                ciphersuite.setBits(Integer.parseInt(attributes[6].trim().isEmpty() ? "0" : attributes[6].trim()));
                ciphersuite.setHashAlgorithm(attributes[7]);

                ciphersuites.add(ciphersuite);
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


    private class Ciphersuite {
        private int cipherId;
        private String name;
        private String protocol;
        private String keyExchange;
        private String authentication;
        private String symetricEncryptionAlgorithm;
        private int bits;
        private String hashAlgorithm;

        public int getCipherId() {
            return cipherId;
        }

        public void setCipherId(int cipherId) {
            this.cipherId = cipherId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getKeyExchange() {
            return keyExchange;
        }

        public void setKeyExchange(String keyExchange) {
            this.keyExchange = keyExchange;
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public String getSymetricEncryptionAlgorithm() {
            return symetricEncryptionAlgorithm;
        }

        public void setSymetricEncryptionAlgorithm(String symetricEncryptionAlgorithm) {
            this.symetricEncryptionAlgorithm = symetricEncryptionAlgorithm;
        }

        public int getBits() {
            return bits;
        }

        public void setBits(int bits) {
            this.bits = bits;
        }

        public String getHashAlgorithm() {
            return hashAlgorithm;
        }

        public void setHashAlgorithm(String hashAlgorithm) {
            this.hashAlgorithm = hashAlgorithm;
        }
    }

}
