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
        int lowestLevel = Integer.MAX_VALUE;
        Encrypt2Details encrypt2Details = null;
        for (Ciphersuite ciphersuite : ciphersuites) {
            String ciphersuiteName = ciphersuite.getName();
            if (textToProcess.contains(ciphersuiteName)) {
                int level = computeLevelFromKeyLength(ciphersuite.getBits());
                if (level < lowestLevel) {
                    encrypt2Details = new Encrypt2Details(ciphersuite.getSymetricEncryptionAlgorithm(), level);
                    lowestLevel = level;
                }
            }
        }
        if (encrypt2Details != null) {
            return JsonConverter.objectToJsonString(encrypt2Details);
        }
        else {
            encrypt2Details = new Encrypt2Details(null, -1);
            return JsonConverter.objectToJsonString(encrypt2Details);
        }
    }

    /**
     * Computes ecrypt 2 level
     * @param keyLength length of they key for the symmetric encryption
     * @return ecrypt 2 level
     */
    private int computeLevelFromKeyLength(int keyLength) {
        if (keyLength < 32) {
            return 0;
        }
        if (keyLength < 64) {
            return 1;
        }
        if (keyLength < 72) {
            return 2;
        }
        if (keyLength < 80) {
            return 3;
        }
        if (keyLength < 96) {
            return 4;
        }
        if (keyLength < 112) {
            return 5;
        }
        if (keyLength < 128) {
            return 6;
        }
        if (keyLength < 256) {
            return 7;
        }
        if (keyLength >= 256) {
            return 8;
        }

        return -1;
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

    /**
     * Represents the model for ciphersuites used in TLS
     */
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
