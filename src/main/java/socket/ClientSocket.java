package socket;


import model.DataNotSendSerialized;
import model.JobClasses.Enterprise;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * The ClientSocket class manages the client-side socket connection to a server.
 * It handles connecting to the server, sending and receiving messages, and maintaining the connection.
 */
public class ClientSocket implements Runnable {
    private String ip;
    private int port;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private CountDownLatch latch;
    private volatile boolean runningThreadPing;
    private volatile boolean serverConnected;
    private Thread pings;
    private Thread readerThread;
    private ArrayList<String> data;
    private DataNotSendSerialized ds;
    private boolean dataSend;

    /**
     * Constructs a ClientSocket with the specified IP address, port, and CountDownLatch.
     *
     * @param ip     the IP address of the server.
     * @param port   the port number of the server.
     * @param ilatch the CountDownLatch used to signal connection establishment.
     */
    public ClientSocket(String ip, String port, CountDownLatch ilatch) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.latch = ilatch;
        this.serverConnected = false;
        // Handle data not send
        this.dataSend = false;
        this.ds = new DataNotSendSerialized();
    }

    /**
     * The entry point for the thread. Attempts to connect to the server.
     */
    @Override
    public void run() {
        try {
            connectToServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Server not found or not responding: %s\n", e.getMessage());
            latch.countDown();
        }
    }

    /**
     * Connects to the server and sets up input and output streams.
     * Starts threads for pinging the server and reading messages.
     *
     * @throws IOException            if an I/O error occurs when creating the socket.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    private void connectToServer() throws IOException, ClassNotFoundException {
        clientSocket = new Socket("127.0.0.1", port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

        // Read the enterprise object after connection
        latch.countDown();

        this.serverConnected = true;
        this.runningThreadPing = true;

        // Start the ping thread
        pings = new Thread(this::pingServer);
        pings.start();

        // Start the reader thread
        readerThread = new Thread(this::readServerMessages);
        readerThread.start();
    }

    /**
     * Sends periodic ping messages to the server to keep the connection alive.
     */
    private void pingServer() {
        while (runningThreadPing) {
            try {
                if (out != null) {
                    out.println("ping");
                    System.out.println("ping still running");
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Ping thread stopping");
    }

    /**
     * Reads messages from the server and handles them. If initially connected, sends all unsent data.
     */
    private void readServerMessages() {
        try {
            String serverMessage;
            // Si on est connecté pour la première fois on envoie toutes les données
            if (serverConnected && !dataSend) {
                data = ds.loadData();
                for (String entry : data) {
                    System.out.println("E: ".concat(entry));
                    clientSendMessage(entry);
                }
                dataSend = true;
                // Tout supprimer du fichier .ser
                ds.deleteData();
            }
            while (serverConnected && (serverMessage = in.readLine()) != null) {
                System.out.printf("Server message: %s\n", serverMessage);
                // Additional message handling can be added here
            }
        } catch (SocketException e) {
            System.out.println("Connection reset, server might be down");
        } catch (IOException e) {
            System.out.println("IOException while reading from server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            serverConnected = false;
            dataSend = false;
            System.out.println("Server disconnected");
            clientClose();
            // Attempt to reconnect
            try {
                connectToServer();
            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Reconnection failed: %s\n", e.getMessage());
            }
        }
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return true if connected to the server, false otherwise.
     */
    public boolean isServerConnected() {
        return serverConnected;
    }

    /**
     * Stops the ping thread.
     */
    public void setRunningThreadPingToFalse() {
        this.runningThreadPing = false;
        System.out.println("Put runningThreadPing to false");
        if (pings != null) {
            pings.interrupt();
        }
    }

    /**
     * Closes the client socket and associated streams.
     */
    public void clientClose() {
        try {
            setRunningThreadPingToFalse();
            System.out.println("TEST close connection");
            serverConnected = false;
            if (readerThread != null) {
                readerThread.interrupt();
                System.out.println("TEST close connection 2");
            }

            if (pings != null) pings.interrupt();
            if (clientSocket != null) clientSocket.close();

            try {
                if (in != null) {
                    in.close();
                    System.out.println("TEST 3");
                }
                if (out != null) {
                    out.close();
                    System.out.println("TEST 4");
                }
            } catch (IOException e) {
                System.out.printf("Error closing input stream: %s\n", e.getMessage());
            }

            System.out.println("Client closed");
        } catch (IOException e) {
            System.out.printf("Error closing client: %s\n", e.getMessage());
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to be sent.
     */
    public void clientSendMessage(String message) {
        if (out != null && serverConnected) {
            out.println(message);
        } else {
            System.out.println("Cannot send message, server is disconnected");
        }
    }

}
