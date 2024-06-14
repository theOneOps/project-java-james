package controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import mainApp.model.DataSerialize;
//import mainApp.model.JobClasses.Employee;
//import mainApp.model.JobClasses.Enterprise;
import socket.ClientSocket;


import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PointerController {

    private ScheduledExecutorService scheduler;
    //private Enterprise ent;
    private ClientSocket socket;
    private Thread clientThread;
    private boolean connected;
    private CountDownLatch latch;


    public PointerController() {
        //ent = setEnt("e2");
        // Init co
        this.latch = new CountDownLatch(1);
        //TODO config ip et port de la pointeuse
        // Ip localhost par défaut pour test
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.connected = false;
        this.socket = new ClientSocket("127.0.0.1", "80", latch);
        startScheduledConnection();
    }
    /*
       public Enterprise setEnt(String entName) {
           // Recupère l'entreprise via dataserialize
           return DataSerialize.getInstance().getEntByName(entName);
       }


       public ObservableList<Employee> getEmployee() {
           ArrayList<Employee> tmp = new ArrayList<>();
           for (Map.Entry entry : ent.getEmployees().entrySet()) {
               tmp.add((Employee) entry.getValue());
           }
           return FXCollections.observableArrayList(tmp);
       }
    *
       public ArrayList<String> reloadEmployeesCombox() {
           if (ent != null) {
               ArrayList<String> empData = new ArrayList<>();
               empData.add("choose your name");
               empData.addAll(ent.getAllEmployeesName());
               return empData;
               //pointer.getEmployees().clearLCBComboBox();
               //pointer.getEmployees().setLCBComboBox(empData.toArray(new String[0]));
           }
           return new ArrayList<>();
       }
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


    public void stopScheduledConnection() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            //stopConnectionThread();
            clientThread.interrupt();
        }
    }
    public void startConnectionThread() throws IOException, ClassNotFoundException {
       /* ArrayList<String> parametersConnection = parameterSerialize.loadData();


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
       */
        Runnable connectTask = () -> connectToServer("127.0.0.1", "80");
        clientThread = new Thread(connectTask);
        clientThread.start();
    }


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
           /*if (socket.getCorrectEnterprise() != null) {
               //ent = socket.getCorrectEnterprise();
               handleServerConnection();
           } else {
               requestEnterpriseData();
           }*/
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for connection.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException or ClassNotFoundException during connection: " + e.getMessage());
        }
    }
    /*
       private void handleServerConnection() {
           if (ent == null) {
               System.out.println("ent still null");
           } else {
               connected = true;
               Platform.runLater(this::reloadEmployeesCombox);
           }
       }
    */
    public void sendPendingData(String data) throws IOException, ClassNotFoundException {
        socket.clientSendMessage(data);
    }


    private void requestEnterpriseData() throws InterruptedException, IOException, ClassNotFoundException {
        socket.clientSendMessage("Enterprise");
    }


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
