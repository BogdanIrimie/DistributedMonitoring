#!/bin/sh

# Reload profile if it exists.
if [ -f /etc/profileT ];
then
    echo "Reload profile data"
    source /etc/profile
fi

# USe JAVA_HOME if it is set.
if [ ! -z "$JAVA_HOME" ];
then
    # JAVA_HOME is set, use it.
    $JAVA_HOME/bin/java -jar ../lib/FrontEnd.jar -p ../var/specs_monitoring_nmap_frontend.pid
else
    # JAVA_HOME is NOT set.
    java -jar ../lib/FrontEnd.jar -p ../var/specs_monitoring_nmap_frontend.pid
fi
