package socket;

import model.DataSerialize;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServersSocket implements Runnable {
    private String serverPort;
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean isListening;
    private ExecutorService pool;


    public ServersSocket(DataSerialize d, String port) {
        serverPort = port;
        connections = new ArrayList<>();
        isListening = false;
    }


    @Override
    public void run() {
        try {
            server = new ServerSocket(Integer.parseInt(serverPort));
            pool = Executors.newCachedThreadPool();
            while (!isListening) {
                Socket client = server.accept();
                ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
                objOut.writeObject(DataSerialize.getInstance().getEnterpriseClassByPort(serverPort));
                objOut.flush();


                ConnectionHandler ch = new ConnectionHandler(client, objOut);
                connections.add(ch);
                pool.execute(ch);
            }
        } catch (IOException e) {
            // Ignore
        } finally {
            try {
                shutDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void shutDown() throws IOException {
        if (!server.isClosed()) {
            server.close();
            pool.shutdown();
            isListening = true;
        }


        for (ConnectionHandler ch : connections) {
            ch.shutConnectionDown();
        }
    }


    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objOut;


        public ConnectionHandler(Socket client, ObjectOutputStream objOut) {
            this.client = client;
            this.objOut = objOut;
        }


        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;


                while ((message = in.readLine()) != null) {
                    if (message.equals("ping")) {
                        out.flush();
                        out.println("here");
                    } else if (message.equals("resendEnterprise")) {
                        objOut.writeObject(DataSerialize.getInstance().getEnterpriseClassByPort(serverPort));
                        objOut.flush();
                    } else {
                        String[] messageSplit = message.split(";");
                        if (messageSplit.length == 2) {
                            System.out.println("test");
                            DataSerialize.getInstance().addNewWorkHour("80", messageSplit[1], LocalDate.now().toString(),
                                    messageSplit[0]);
                        }
                        System.out.printf("receive something else from client ! -> %s\n", message);
                    }
                }
            } catch (IOException e) {
                // todo : handle
            }
        }


        public void shutConnectionDown() throws IOException {
            if (!client.isClosed()) {
                client.close();
                out.close();
                in.close();
            }
        }
    }
}
