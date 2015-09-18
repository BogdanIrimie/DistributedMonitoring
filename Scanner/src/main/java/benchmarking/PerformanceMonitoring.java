package benchmarking;

import datamodel.monitoring.MonitoringResult;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class PerformanceMonitoring implements Monitorable {
    private final static Logger logger = LoggerFactory.getLogger(PerformanceMonitoring.class);

    private Calendar startTimeOfMonitoring;
    private Calendar endTimeOfMonitoring;

    @Override
    public void startMonitoring() {
        startTimeOfMonitoring = Calendar.getInstance();
        startTimeOfMonitoring.add(Calendar.SECOND, -1);
    }

    @Override
    public void stopMonitoring() {
        endTimeOfMonitoring = Calendar.getInstance();
        endTimeOfMonitoring.add(Calendar.SECOND, 1);
    }

    public List<MonitoringResult> parseForPid(long pid) {
        BufferedReader br = null;
        String line;
        List<MonitoringResult> monitoringResults = new ArrayList<MonitoringResult>();

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
                        if (resultsPid == pid) {
                            String cpu = logLine[2];
                            String memory = logLine[3];
                            String networkIn = logLine[4];
                            String networkOut = logLine[5];

                            MonitoringResult monitoringResult = new MonitoringResult();
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
