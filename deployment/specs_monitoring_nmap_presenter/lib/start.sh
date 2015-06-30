#!/bin/bash

for i in `seq 1 10`;
do
    java -jar Presenter.jar &
    disown
done
