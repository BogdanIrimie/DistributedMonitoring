package benchmarking;

import datamodel.CpuUsageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CpuMonitoring implements MonitoringInterface {
    private final static Logger logger = LoggerFactory.getLogger(CpuMonitoring.class);

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

    public CpuUsageResults parseForPid(long pid) {
        BufferedReader br = null;
        String line;
        StringBuilder nmapMonitoring = new StringBuilder();
        List<String> cpuUsageResults = new ArrayList<String>();
        try {
            // wait for 1 second before reading the file so that all data for PID is written in file
            Thread.sleep(1300);
            br = new BufferedReader(new FileReader("/tmp/everySecondMonitoring.txt"));

            while ((line = br.readLine()) != null) {
                String[] splitedLine = line.split("\\s+");
                if ((splitedLine.length > 2) && (splitedLine[0].trim().length() > 18)) {
                    String[] dateAndTime = splitedLine[0].split("_");
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

                    if (timeFromLog.after(startTimeOfMonitoring) && timeFromLog.before(endTimeOfMonitoring)) {
                        if ((splitedLine.length > 1) && isNumeric(splitedLine[1].trim())) {
                            long resultsPid = Long.parseLong(splitedLine[1]);
                            if (resultsPid == pid) {
                                cpuUsageResults.add(splitedLine[2]);
                            }
                        }
                    }

                    // it is useless to look after the time the monitoring was stoped
                    if (timeFromLog.after(endTimeOfMonitoring)) {
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        return new CpuUsageResults(pid, startTimeOfMonitoring, cpuUsageResults);
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
