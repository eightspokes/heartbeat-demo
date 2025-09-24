#!/bin/bash

WORKER_PORT=5001
CLASSPATH="target/classes"

# Compile  the worker module
echo "Compiling ObstacleDetectionModule..."
javac -d $CLASSPATH src/main/java/org/heartbeat/ObstacleDetectionModule.java

# Kill any existing process using the worker port
PID=$(lsof -ti :$WORKER_PORT)
if [ -n "$PID" ]; then
  echo "Port $WORKER_PORT is already in use by PID $PID. Killing..."
  kill -9 $PID
  sleep 1
else
  echo "Port $WORKER_PORT is free."
fi

# Start the worker
echo "Starting ObstacleDetectionModule..."
java -classpath $CLASSPATH org.heartbeat.ObstacleDetectionModule

