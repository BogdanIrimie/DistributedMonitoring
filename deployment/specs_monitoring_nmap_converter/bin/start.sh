#!/bin/sh

# Reload profile if it exists.
if [ -f /etc/profile ];
then
    echo "Reload profile data."
    source /etc/profile
fi

# Load config file if it exists.
if [ -f /etc/sysconfig/converter_config ];
then
  echo "Load config."
  source /etc/sysconfig/converter_config
fi

if [ ! -z "$CONVERTER_HOME" ];
then
  # CONVERTER_HOME is set, us it.
  component_home=$CONVERTER_HOME
else
  # CONVERTER_HOME is not set.
  component_home=$(pwd)/..
fi

# Use JAVA_HOME if it is set.
if [ ! -z "$JAVA_HOME" ];
then
    # JAVA_HOME is set, use it.
    $JAVA_HOME/bin/java -jar $component_home/lib/Converter.jar -p $component_home/var/specs_monitoring_nmap_converter.pid &
    disown
else
    # JAVA_HOME is NOT set.
    java -jar $component_home/lib/Converter.jar -p $component_home/var/specs_monitoring_nmap_converter.pid &
    disown
fi
