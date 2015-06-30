#!/bin/bash

for i in `seq 1 10`;
do
    java -jar Converter.jar &
    disown
done
