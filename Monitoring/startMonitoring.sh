#!/bin/sh

sudo atop -o | unbuffer -p awk '/ATOP -/ {dateTime =$4"_"$5} NF==12{printf "%s %d %s %s %.4fKbps %.4fKbps %s\n", dateTime, $1, $2, $3,($4 * $5 + $6 * $7) / 125, ($8 * $9 + $10 * $11) / 125, $12 }' > /tmp/performanceMonitoring.log

