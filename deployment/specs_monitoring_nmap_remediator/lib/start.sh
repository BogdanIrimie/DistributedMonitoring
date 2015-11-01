#!/bin/bash

for i in `seq 1 10`;
do
    java -jar Remediator.jar &
    disown
done
