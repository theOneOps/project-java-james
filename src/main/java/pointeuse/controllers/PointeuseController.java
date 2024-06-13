package pointeuse.controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import model.DataNotSendSerialized;
import model.JobClasses.Enterprise;
import pointeuse.views.Pointeuse;
import socket.ClientSocket;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PointeuseController {
    private ClientSocket clientSocket;
    private Thread clientThread;
    private Thread threadConnection;
    private boolean connected;
    private ScheduledExecutorService scheduler;
    private CountDownLatch latch;

    private static Pointeuse pointer;
    private static Enterprise ent;
    private ArrayList<String> workhours = new ArrayList<>();
    private String ipConnectTo = "";
    private String portConnectTo = "";
    private DataNotSendSerialized dataNotSendSerialized;

    public PointeuseController(Stage stage) {

        pointer = new Pointeuse(stage);
        ent = null;
        dataNotSendSerialized = new DataNotSendSerialized();
        //parameterSerialize = new ParameterSerialize();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.connected = false;

        initializeConnection();
        setupEventHandlers(stage);
    }

    /**
     * Initializes the connection by starting the scheduled connection attempts.
     */
    private void initializeConnection() {
        startScheduledConnection();
    }

    /**
     * Sets up the event handlers for various UI components.
     *
     * @param stage the Stage object
     */
    private void setupEventHandlers(Stage stage) {
        pointer.getQuitButton().setOnAction(e -> {
            stage.close();
            stopScheduledConnection();
        });

        stage.setOnCloseRequest(e -> stopScheduledConnection());

        pointer.getCheckInOutButtonClick().setOnAction(e -> {
            try {
                // todo : pop up to change the port to connect with
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        pointer.getConfig().getAllBtns().getBtn2().setOnAction(event -> {
            try {
                //
                pointer.saveNewConfigPointer();
                restartScheduledConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        pointer.getCheckInOutButtonClick().setOnAction(e -> handleCheckInOut());
    }

    /**
     * Handles the check-in/out operation by sending the check-in/out message to the server
     * or storing it locally if the server is not connected.
     */
    private void handleCheckInOut() {
        if (!pointer.getEmployees().getLCBComboBox().getValue().equals("choose your name")) {
            StringBuilder res = new StringBuilder(String.format("%s", ent.getEntPort()));
            int startIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf('(');
            int endIndex = pointer.getEmployees().getLCBComboBox().getValue().indexOf(')');

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String result = pointer.getEmployees().getLCBComboBox().getValue().substring(startIndex + 1, endIndex);
                res.append(String.format("|%s", result));
            }

            res.append(String.format("|%s|%s", LocalDate.now(), pointer.getDateHours().roundTime()));

            if (clientSocket != null && clientSocket.isServerConnected()) {
                clientSocket.clientSendMessage(res.toString());
            } else {
                Platform.runLater(() -> Pointer.PrintAlert("Server not connected",
                        "The server is not connected. Data will be sent once the connection is restored."));
                workhours.add(res.toString());
                connected = false;
            }
        }
    }

    /**
     * Starts the scheduled connection attempts.
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
                if (!connected) {
                    startConnectionThread();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Stops the scheduled connection attempts.
     */
    public void stopScheduledConnection() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            stopConnectionThread();
        }
    }

    /**
     * Restarts the scheduled connection attempts.
     */
    public void restartScheduledConnection() {
        stopScheduledConnection();
        startScheduledConnection();
    }

    /**
     * Starts the connection thread to attempt connecting to the server.
     *
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void startConnectionThread() throws IOException, ClassNotFoundException {
        ArrayList<String> parametersConnection = parameterSerialize.loadData();

        if (parametersConnection.size() == 2) {
            ipConnectTo = parametersConnection.get(0);
            portConnectTo = parametersConnection.get(1);
        }
        String ip = ipConnectTo;
        String port = portConnectTo;

        if (ip.isEmpty() || port.isEmpty() || !port.matches("\\d+")) {
            System.out.println("Invalid IP or Port.");
            return;
        }

        Runnable connectTask = () -> connectToServer(ip, port);
        threadConnection = new Thread(connectTask);
        threadConnection.start();
    }

    /**
     * Connects to the server with the given IP and port.
     *
     * @param ip the IP address of the server
     * @param port the port of the server
     */
    private void connectToServer(String ip, String port) {
        try {
            latch = new CountDownLatch(1);
            clientSocket = new ClientSocket(ip, port, latch);

            clientThread = new Thread(clientSocket);
            clientThread.start();

            if (!latch.await(2, TimeUnit.SECONDS)) {
                System.out.println("Connection timeout. Server not responding.");
                clientSocket.clientClose();
                clientThread.interrupt();
                return;
            }

            if (clientSocket.getCorrectEnterprise() != null) {
                ent = clientSocket.getCorrectEnterprise();
                handleServerConnection();
            } else {
                requestEnterpriseData();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for connection.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException or ClassNotFoundException during connection: " + e.getMessage());
        }
    }

    /**
     * Handles the server connection by setting the connected flag and reloading the employee combo box.
     */
    private void handleServerConnection() {
        if (ent == null) {
            System.out.println("ent still null");
        } else {
            connected = true;
            Platform.runLater(this::reloadEmployeesCombox);
            try {
                sendPendingData();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Requests the enterprise data from the server.
     *
     * @throws InterruptedException if the thread is interrupted
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    private void requestEnterpriseData() throws InterruptedException, IOException, ClassNotFoundException {
        clientSocket.clientSendMessage("resendEnterprise");
        if (latch.await(2, TimeUnit.SECONDS)) {
            ent = clientSocket.getCorrectEnterprise();
            if (ent != null) {
                connected = true;
                Platform.runLater(this::reloadEmployeesCombox);
                sendPendingData();
            }
        }
    }

    /**
     * Sends the pending data to the server.
     *
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    private void sendPendingData() throws IOException, ClassNotFoundException {
        try {
            ArrayList<String> notSended = dataNotSendSerialized.loadData();

            if (!notSended.isEmpty()) {
                for (String res : notSended) {
                    clientSocket.clientSendMessage(res);
                }
            }

            if (!workhours.isEmpty()) {
                for (String res : workhours) {
                    clientSocket.clientSendMessage(res);
                }
            }

            dataNotSendSerialized.saveData(new ArrayList<>());
            workhours.clear();
            System.out.println("Emptied the dataNotSendSerialized");
        } catch (EOFException eof) {
            // todo : ignore
        }
    }

    /**
     * Stops the connection thread and closes the client socket.
     */
    public void stopConnectionThread() {
        if (threadConnection != null && threadConnection.isAlive()) {
            threadConnection.interrupt();

            if (clientThread != null && clientThread.isAlive()) {
                if (clientSocket != null) {
                    clientSocket.setRunningThreadPingToFalse();
                    clientSocket.clientClose();
                }
                clientThread.interrupt();

                if (!workhours.isEmpty()) {
                    try {
                        System.out.printf("all workhours saved : %s", workhours);
                        dataNotSendSerialized.saveData(workhours);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            connected = false;
            scheduler = Executors.newScheduledThreadPool(1);
            latch = new CountDownLatch(1);
        }
    }

    /**
     * Reloads the employee combo box with the updated list of employees.
     */
    public void reloadEmployeesCombox() {
        if (ent != null) {
            ArrayList<String> empData = new ArrayList<>();
            empData.add("choose your name");
            empData.addAll(ent.getAllEmployeesName());
            pointer.getUserComboBox();
            pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
        }
    }
}
}
