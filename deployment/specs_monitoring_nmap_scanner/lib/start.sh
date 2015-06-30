#!/bin/bash

for i in `seq 1 10`;
do
    java -jar Scanner.jar &
    disown
done
