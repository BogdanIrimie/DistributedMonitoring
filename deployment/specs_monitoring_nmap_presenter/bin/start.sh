#!/bin/sh

# Reload profile if it exists.
if [ -f /etc/profile ];
then
    echo "Reload profile data."
    source /etc/profile
fi

# Load config file if it exists.
if [ -f /etc/sysconfig/presenter_config ];
then
  echo "Load config."
  source /etc/sysconfig/presenter_config
fi

if [ ! -z "$PRESENTER_HOME" ];
then
  # PRESENTER_HOME is set, us it.
  component_home=$PRESENTER_HOME
else
  # PRESENTER_HOME is not set.
  component_home=$(pwd)/..
fi

# Use JAVA_HOME if it is set.
if [ ! -z "$JAVA_HOME" ];
then
    # JAVA_HOME is set, use it.
    $JAVA_HOME/bin/java -jar $component_home/lib/Presenter.jar -p $component_home/var/specs_monitoring_nmap_presenter.pid &
    disown
else
    # JAVA_HOME is NOT set.
    java -jar $component_home/lib/Presenter.jar -p $component_home/var/specs_monitoring_nmap_presenter.pid &
    disown
fi
