#!/bin/sh

# Check if PID file exists.
if [ ! -f ../var/specs_monitoring_nmap_presenter.pid ];
then
    echo "No PID file found!"
    exit -1
fi

# Read PID from file.
file="../var/specs_monitoring_nmap_presenter.pid"
PID=$(cat "$file")

# Check if file content is a number.
numberRegex='^[0-9]+$'
if ! [[ $PID =~ $numberRegex ]];
then
    echo "The content of the file is not a valid PID!"
    exit -2
fi

# Kill running process.
if kill $PID;
then
    echo "Process with PID $PID was killed!"
else
    echo "There is no process with $PID running!"
fi
