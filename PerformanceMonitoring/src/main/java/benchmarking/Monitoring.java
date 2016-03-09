package benchmarking;

import datamodel.ComponentPerformance;
import datamodel.MonitoringResult;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.mongo.MongoManager;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * Start timer and save monitoring data to DB
 */
public class Monitoring implements Monitorable {
    private final static Logger logger = LoggerFactory.getLogger(Monitoring.class);
    Calendar startTime, endTime;

    /**
     * Set start time of monitoring.
     */
    public void startMonitoring() {
        startTime = Calendar.getInstance();
    }

    /**
     * Set end time of monitoring.
     */
    public void stopMonitoring() {
        endTime = Calendar.getInstance();
    }


    public ComponentPerformance getMonitoringEvent(String jobId, MongoManager mm) {
        String jsonComponentPerformance = mm.pushJson(jobId, "selfPerformanceMonitoring");
        ComponentPerformance cp = JsonConverter.jsonStringToObject(jsonComponentPerformance, ComponentPerformance.class);
        return cp;
    }

    /**
     * Update ComponentPerformance object with data extracted from logs.
     *
     * @param componentPerformance will be updated with data from log file.
     * @param mm manage interactions with database.
     */
    public void addPerformanceResultsAndSave(ComponentPerformance componentPerformance, MongoManager mm) {
        Thread writeResults = new Thread(new Runnable() {
            @Override
            public void run() {
                componentPerformance.setMonitoringResults(parseForPid(componentPerformance));
                mm.updateJsonWithId(componentPerformance.getJobId(), JsonConverter.objectToJsonString(componentPerformance));
            }
        });
        writeResults.start();
    }

    /**
    * Parse logs and extract metrics.
    *
    * @param componentPerformance contains data regarding monitoring job.
    * @return list with monitoring results with 1 second resolution
    */
    public List<MonitoringResult> parseForPid(ComponentPerformance componentPerformance) {
        BufferedReader br = null;
        String line;
        List<MonitoringResult> monitoringResults = new ArrayList<MonitoringResult>();

        Calendar startTimeOfMonitoring = componentPerformance.getJobStartTime();
        // Subtract 1 second from the beginning time to round time
        startTimeOfMonitoring.add(Calendar.SECOND, -1);

        Calendar endTimeOfMonitoring = componentPerformance.getJobEndTime();
        // Add 1 second to the end time to round time
        endTimeOfMonitoring.add(Calendar.SECOND, 1);

        try {
            Thread.sleep(1500);
            String strpath="/tmp/performanceMonitoring.log";
            ReversedLinesFileReader reverseFileReader = new ReversedLinesFileReader(new File(strpath));;
            do {
                line = reverseFileReader.readLine();
                if (line == null) {
                    continue;
                }

                String[] logLine = line.split("\\s+");

                if ((logLine.length >= 6) &&                                                                        // at least a timestamp, a pid and a %
                        (logLine[0].trim().length() > 18)) {                                                        // a valid timestamp (date + time)

                    String[] dateAndTime = logLine[0].split("_");
                    String date = dateAndTime[0];
                    String time = dateAndTime[1];

                    if (date.trim().length() == 0 || time.trim().length() == 0) {
                        continue;
                    }

                    String[] dateSplit = date.split("/");
                    String[] timeSplit = time.split(":");

                    Calendar timeFromLog = Calendar.getInstance();

                    timeFromLog.set(Calendar.SECOND, Integer.parseInt(timeSplit[2]));
                    timeFromLog.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));
                    timeFromLog.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    timeFromLog.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[2]));
                    timeFromLog.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                    timeFromLog.set(Calendar.YEAR, Integer.parseInt(dateSplit[0]));

                    if (timeFromLog.after(startTimeOfMonitoring) && timeFromLog.before(endTimeOfMonitoring) && isNumeric(logLine[1].trim())) {
                        long resultsPid = Long.parseLong(logLine[1]);
                        if (resultsPid == componentPerformance.getPid()) {
                            String cpu = logLine[2];
                            String memory = logLine[3];
                            String networkIn = logLine[4];
                            String networkOut = logLine[5];

                            MonitoringResult monitoringResult = new MonitoringResult();
                            monitoringResult.setTimeFromLog(timeFromLog);
                            monitoringResult.setCpu(cpu);
                            monitoringResult.setMemory(memory);
                            monitoringResult.setNetworkIn(networkIn);
                            monitoringResult.setNetworkOut(networkOut);
                            monitoringResults.add(monitoringResult);
                        }
                    }

                    // look untill the time read from log is before the process began
                    if (timeFromLog.before(startTimeOfMonitoring)) {
                        break;
                    }
                }
            } while (line != null);
            reverseFileReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        Collections.reverse(monitoringResults);
        return monitoringResults;
    }

    /**
     * Verify if the string only contains digits
     * @param str string that should be verified
     * @return if the string is a number or not
     */
    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

}
