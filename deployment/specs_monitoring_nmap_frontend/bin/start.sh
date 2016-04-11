#!/bin/sh

# Reload profile if it exists.
if [ -f /etc/profile ];
then
    echo "Reload profile data."
    source /etc/profile
fi

# Load config file if it exists.
if [ -f /etc/sysconfig/frontend_config ];
then
  echo "Load config."
  source /etc/sysconfig/frontend_config
fi

if [ ! -z "$FRONTEND_HOME" ];
then
  # FRONTEND_HOME is set, us it.
  component_home=$FRONTEND_HOME/bin
else
  # FRONTEND_HOME is not set.
  component_home=$(pwd)
fi

# Use JAVA_HOME if it is set.
if [ ! -z "$JAVA_HOME" ];
then
    # JAVA_HOME is set, use it.
    $JAVA_HOME/bin/java -jar $component_home/../lib/FrontEnd.jar -p $component_home/../var/specs_monitoring_nmap_frontend.pid
else
    # JAVA_HOME is NOT set.
    java -jar $component_home/../lib/FrontEnd.jar -p $component_home/../var/specs_monitoring_nmap_frontend.pid
fi
