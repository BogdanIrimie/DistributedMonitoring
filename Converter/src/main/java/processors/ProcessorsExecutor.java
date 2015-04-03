package processors;

import com.fasterxml.jackson.databind.JsonNode;
import datamodel.Job;
import datamodel.Measurement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProcessorsExecutor {

    public String executeProcesses(String messages ,Measurement measurement) {
        String result = messages;
        String[] processors = measurement.getProcessors();
        for (String process : processors) {
            try {
                Class<?> cls = Class.forName(process);
                Method method = cls.getDeclaredMethod("process", new Class[] {String.class, JsonNode.class});
                Object obj = cls.newInstance();
                result = (String)method.invoke(obj, new Object[] {result, null});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (measurement.getAdapter() != null) {
            try {
                Class<?> cls = Class.forName(measurement.getAdapter());
                Method method = cls.getDeclaredMethod("adaptMessage", new Class[] {String.class, Measurement.class});
                Object obj = cls.newInstance();
                result = (String)method.invoke(obj, new Object[] {result, measurement});
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
