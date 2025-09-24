# Heartbeat Fault-Detection 

## Overview
This project demonstrates the **Heartbeat architectural tactic** for fault detection in a critical software module. The demo simulates a **self-driving car module** (`ObstacleDetectionModule`) monitored by a **heartbeat monitor** (`HeartbeatMonitor`).

- The **worker module** sends periodic heartbeat signals to the monitor.
- The worker randomly stops sending heartbeats within 60 seconds to simulate a failure.
- The **monitor** detects missed heartbeats and prints alerts.
- Both modules run as **independent processes**, simulating a realistic fault-tolerant system.

This demo highlights how architectural tactics can be implemented to increase **system reliability and availability**, and how independent monitoring allows the system to detect failures and react appropriately.

## Requirements
- Java 21 (JDK)
- Maven (for compilation)  


## Run the Monitor
Make the script executable (first time only):
```chmod +x start-monitor.sh```

Run the script

```./start-monitor.sh```

The monitor binds to port 5000 and listens for heartbeat messages.
If a previous monitor instance is running, it will be killed automatically to free the port.

## Run the Worker
Make the script executable (first time only):
```chmod +x start-worker.sh```

Run the script:
```./start-worker.sh```

The worker sends heartbeat messages every  second to the monitor.
It randomly stops sending heartbeats within 60 seconds to simulate a crash, demonstrating fault detection.

## Observed Behaviour 

While the worker is alive, the monitor prints: Heartbeat received

When the worker stops sending heartbeats, the monitor prints: ALERT: Heartbeat from Obstacle Detection Module is missed!

Both processes run independently, simulating a distributed system where components can fail and be detected without impacting other modules.

## Design Notes

### HeartbeatMonitor.java

Listens for heartbeats on port 5000.
Uses AtomicBoolean for thread-safe heartbeat tracking.
Prints alerts when heartbeats are missed.
Can be extended to restart workers automatically.

### ObstacleDetectionModule.java
Sends heartbeat messages every second.
Randomly stops sending heartbeats within 60 seconds to simulate non-deterministic failure.
Represents a critical module in a self-driving car system, such as obstacle detection.

### Bash Scripts
Ensure no old processes are running on the relevant ports.
Compile the required module (javac) instead of the entire project.
Start monitor and worker independently, simulating separate processes in a real system.
