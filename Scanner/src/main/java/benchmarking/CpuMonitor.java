package benchmarking;

import datamodel.CpuUsageResults;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpuMonitor {
    private boolean continueMonitoring = true;
    private StringBuilder commandOutput;
    private Date date;

    public void startMonitoring(){
        boolean firstAppearance = true;

        Thread cpuMonit = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                Process process;
                commandOutput = new StringBuilder();

                String processPid = getPid();
                String[] cmd = {
                        "/bin/sh",
                        "-c",
                        " atop 1 -a | unbuffer -p awk '/" + processPid + "|nmap/ {print $1 \" \" $11}'"
                };

                try {
                    process = Runtime.getRuntime().exec(cmd);
                    Field pid = process.getClass().getDeclaredField("pid");
                    pid.setAccessible(true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    while ( continueMonitoring && (line = br.readLine()) != null) {
                        if (firstAppearance  && line.contains(processPid)) {
                            date = new Date();
                        }
                        commandOutput.append(line + "\n");
                    }
                    process.destroy();
                } catch (IOException e) {
                    System.err.println(e.getStackTrace());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                //System.out.println("Command output: " + commandOutput.toString());
            }
        });
        cpuMonit.start();
    }

    public CpuUsageResults parseForPid(long pid) {
        stopMonitoring();
        String cmdOut = commandOutput.toString();
        StringBuilder parsedResultsAfterPid = new StringBuilder();
        List<String> cpuPercentEverySecond = new ArrayList<String>();


        String[] outputLines = cmdOut.split(System.getProperty("line.separator"));
        for (String line : outputLines) {
            String[] processData = line.split(" ");
            try {
                long resultsPid = Long.parseLong(processData[0]);
                String cpuUsage = processData[1];
                if (resultsPid == pid) {
                    cpuPercentEverySecond.add(cpuUsage);
                    //parsedResultsAfterPid.append(resultsPid + "," + cpuUsage + "\n");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return new CpuUsageResults(pid, date, cpuPercentEverySecond);
    }

    private static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }

    public void stopMonitoring() {
        continueMonitoring = false;
    }
}
