package org.heartbeat;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeartbeatMonitor {

    private static final int PORT = 5000;        // Port to receive heartbeats
    private static final int TIMEOUT = 1000;     // Interval to check missed heartbeat
    private static AtomicBoolean heartbeatReceived = new AtomicBoolean(false);

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Heartbeat Monitor listening on port " + PORT);

            // Timer to check heartbeat periodically
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!heartbeatReceived.get()) {
                        System.err.println("ALERT: Heartbeat from Obstacle Detection Module is missed! Start the module using start-worker.sh script.");
                        // Here we can potentially restart the worker
                    }
                    heartbeatReceived.set(false); // reset for next interval
                }
            }, TIMEOUT, TIMEOUT);

            // Main loop to accept heartbeats
            while (true) {
                Socket socket = serverSocket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg = reader.readLine();
                    if ("HEARTBEAT".equals(msg)) {
                        heartbeatReceived.set(true);
                        System.out.println("Heartbeat received");
                    }
                } catch (Exception e) {
                    System.err.println("Error reading heartbeat: " + e.getMessage());
                }
            }
        }
    }
}


