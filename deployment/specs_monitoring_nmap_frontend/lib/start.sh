#!/bin/bash

for i in `seq 1 10`;
do
    java -jar FrontEnd.jar &
    disown
done
