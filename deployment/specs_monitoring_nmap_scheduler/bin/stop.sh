#!/bin/sh

# Load config file if it exists.
if [ -f /etc/sysconfig/scheduler_config ];
then
  echo "Load config."
  source /etc/sysconfig/scheduler_config
fi

if [ ! -z "$SCHEDULER_HOME" ];
then
  # SCHEDULER_HOME is set, us it.
  component_home=$SCHEDULER_HOME
else
  # SCHEDULER_HOME is not set.
  component_home=$(pwd)/..
fi

PID_FILE="$component_home/var/specs_monitoring_nmap_scheduler.pid"

# Check if PID file exists.
if [ ! -f $PID_FILE ];
then
    echo "No PID file found!"
    exit -1
fi

# Read PID from file.
PID=$(cat "$PID_FILE")

# Check if file content is a number.
numberRegex='^[0-9]+$'
if ! [[ $PID =~ $numberRegex ]];
then
    echo "The content of the file is not a valid PID!"
    echo "The file will be removed!"
    rm $PID_FILE
    exit -2
fi

# Kill running process.
if kill $PID;
then
    echo "Process with PID $PID was killed!"
else
    echo "There is no process with $PID running!"
fi

# Remove PID file
echo "Removing PID file!"
rm $PID_FILE
