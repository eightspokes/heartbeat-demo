#!/bin/bash

MONITOR_PORT=5000
CLASSPATH="target/classes"

# Compile only the monitor module
echo "Compiling HeartbeatMonitor..."
javac -d $CLASSPATH src/main/java/org/heartbeat/HeartbeatMonitor.java

# Kill any existing process using the monitor port
PID=$(lsof -ti :$MONITOR_PORT)
if [ -n "$PID" ]; then
  echo "Port $MONITOR_PORT is already in use by PID $PID. Killing..."
  kill -9 $PID
  sleep 1
else
  echo "Port $MONITOR_PORT is free."
fi

# Start the monitor
echo "Starting Heartbeat Monitor..."
java -classpath $CLASSPATH org.heartbeat.HeartbeatMonitor
