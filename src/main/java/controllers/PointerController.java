package controllers;

import model.DataNotSendSerialized;
import socket.ClientSocket;


import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The PointerController class manages the connection to the server and handles data sending.
 * It schedules connection attempts and ensures data is sent or serialized if the connection is unavailable.
 */
public class PointerController {

    private ScheduledExecutorService scheduler;
    //private Enterprise ent;
    private ClientSocket socket;
    private Thread clientThread;
    private boolean connectedFirstTime;
    private CountDownLatch latch;
    private DataNotSendSerialized ds;

    private String ip;
    private String port;

    /**
     * Constructs a PointerController with default IP address and port.
     * Initializes the CountDownLatch, scheduler, and client socket.
     */
    public PointerController() {
        this.ip = "127.0.0.1";
        this.port = "80";
        this.ds = new DataNotSendSerialized();
        // Init co
        this.latch = new CountDownLatch(1);
        //TODO config ip et port de la pointeuse
        // Ip localhost par défaut pour test
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.connectedFirstTime = false;
        // Par défaut: ip = 127.0.0.1 / port = 80
        this.socket = new ClientSocket(ip, port, latch);
        startScheduledConnection();
    }

    /**
     * Sets a new IP address and port for the socket connection.
     * Stops the current connection and scheduler, then starts a new connection with the new IP and port.
     *
     * @param ip   the new IP address of the server.
     * @param port the new port number of the server.
     */
    public void setSocket(String ip, String port) {
        // Set new ip & new port
        this.ip = ip;
        this.port = port;
        // Stop socket
        socket.clientClose();
        // Stop scheduler
        stopScheduledConnection();
        // New connection
        socket = new ClientSocket(ip, port, latch);
        startScheduledConnection();
    }

    /**
     * Starts the scheduler for periodic connection attempts to the server.
     * If the scheduler is shutdown or terminated, a new scheduler is created.
     */
    public void startScheduledConnection() {
        // If the scheduler is shutdown or terminated, create a new one
        // the scheduler is responsible for the connection attempts
        // so we need to make sure it is running
        if (scheduler.isShutdown() || scheduler.isTerminated()) {
            // we create a new scheduler with a single thread hence the 1
            // this is because we only need one thread to handle the connection attempts
            scheduler = Executors.newScheduledThreadPool(1);
        }

        scheduler.scheduleAtFixedRate(() -> {
            try {
                startConnectionThread(ip, port);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Stops the scheduler and interrupts the client thread.
     */
    public void stopScheduledConnection() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            clientThread.interrupt();
        }
    }

    /**
     * Starts a new thread to attempt a connection to the server.
     *
     * @param ip   the IP address of the server.
     * @param port the port number of the server.
     * @throws IOException if an I/O error occurs when creating the socket.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public void startConnectionThread(String ip, String port) throws IOException, ClassNotFoundException {
        Runnable connectTask = () -> connectToServer(ip, port);
        clientThread = new Thread(connectTask);
        clientThread.start();
    }

    /**
     * Attempts to connect to the server using the specified IP address and port.
     * If the connection is successful, requests enterprise data from the server.
     *
     * @param ip   the IP address of the server.
     * @param port the port number of the server.
     */
    private void connectToServer(String ip, String port) {
        try {
            System.out.println(ip.concat(" / ").concat(port));

            clientThread = new Thread(socket);
            clientThread.start();

            if (!latch.await(2, TimeUnit.SECONDS)) {
                System.out.println("Connection timeout. Server not responding.");
                socket.clientClose();
                clientThread.interrupt();
                return;
            }
            requestEnterpriseData();
            if (!socket.isServerConnected()) connectedFirstTime = false;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for connection.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException or ClassNotFoundException during connection: " + e.getMessage());
        }
    }

    /**
     * Sends pending data to the server. If the server is not connected, serializes the data for later sending.
     *
     * @param data the data to be sent.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public void sendPendingData(String data) throws IOException, ClassNotFoundException {
        System.out.println("Data".concat(data));
        if (socket.isServerConnected()) {
            socket.clientSendMessage(data);
        } else {
            // On séréaise
            System.out.println("Serealize");
            ArrayList<String> tmp = ds.loadData();
            tmp.add(data);
            ds.saveData(tmp);
            // TEST print
            ds.printData();
        }
    }

    /**
     * Requests enterprise data from the server.
     *
     * @throws InterruptedException if interrupted while waiting.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    private void requestEnterpriseData() throws InterruptedException, IOException, ClassNotFoundException {
        socket.clientSendMessage("Enterprise");
    }

    /**
     * Rounds the given time to the nearest 15-minute interval.
     *
     * @param time the time to be rounded.
     * @return the rounded time as a string in HH:mm format.
     */
    public String roundTime(LocalTime time) {
        int minutes = time.getMinute();
        int modMinutes = minutes % 15;
        int minutesToAdd = (modMinutes <= 7) ? -modMinutes : (15 - modMinutes);


        LocalTime roundedTime = time.plusMinutes(minutesToAdd);
        String res;
        if (roundedTime.getHour() <= 9)
            res = String.format("0%s", roundedTime.getHour());
        else
            res = String.valueOf(roundedTime.getHour());
        return String.format("%s:%02d", res, roundedTime.getMinute());
    }
}
