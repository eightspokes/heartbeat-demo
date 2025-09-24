package org.heartbeat;
import java.net.Socket;
import java.util.Random;

public class ObstacleDetectionModule {
    public static void main(String[] args) throws Exception {
        String monitorHost = "localhost";
        int monitorPort = 5000;
        int interval = 1000;    // 1s heartbeat interval

        Random random = new Random();
        long failureTime = random.nextInt(60000); // crash within 0-60s
        long startTime = System.currentTimeMillis();

        while (true) {
            if (System.currentTimeMillis() - startTime > failureTime) {
                System.err.println("Worker crashed!");
                break; // stop sending heartbeats
            }
            System.out.println("Detecting obstacles... Sending heartbeat");

            try (Socket socket = new Socket(monitorHost, monitorPort)) {
                socket.getOutputStream().write("HEARTBEAT\n".getBytes());
            }

            Thread.sleep(interval);
        }
    }
}

