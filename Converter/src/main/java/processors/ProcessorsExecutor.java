package processors;

import com.fasterxml.jackson.databind.JsonNode;
import datamodel.Job;
import datamodel.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProcessorsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorsExecutor.class);

    public String executeProcesses(String messages ,Measurement measurement) {
        String result = messages;
        String[] processors = measurement.getProcessors();
        if (processors != null) {
            for (String process : processors) {
                try {
                    Class<?> cls = Class.forName(process);
                    Method method = cls.getDeclaredMethod("process", new Class[]{String.class, JsonNode.class});
                    Object obj = cls.newInstance();
                    result = (String) method.invoke(obj, new Object[]{result, null});
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (measurement.getAdapter() != null) {
            try {
                Class<?> cls = Class.forName(measurement.getAdapter());
                Method method = cls.getDeclaredMethod("adaptMessage", new Class[] {String.class, Measurement.class});
                Object obj = cls.newInstance();
                result = (String)method.invoke(obj, new Object[] {result, measurement});
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return result;
    }
}
