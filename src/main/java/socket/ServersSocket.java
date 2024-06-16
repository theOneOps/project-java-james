package socket;

import model.DataSerialize;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ServersSocket class is responsible for managing server-side socket connections.
 * It listens for client connections on a specified port and handles communication with connected clients.
 */
public class ServersSocket implements Runnable {
    private String serverPort;
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean isListening;
    private ExecutorService pool;

    /**
     * Constructor for the ServersSocket class.
     *
     * @param port the port number on which the server listens for client connections.
     */
    public ServersSocket(String port) {
        serverPort = port;
        connections = new ArrayList<>();
        isListening = false;
    }

    /**
     * Runs the server socket, accepting client connections and handling them using a thread pool.
     */
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

    /**
     * Shuts down the server socket and closes all client connections.
     *
     * @throws IOException if an I/O error occurs when closing the server or client connections.
     */
    public void shutDown() throws IOException {
        if (server != null && !server.isClosed()) {
            server.close();
            pool.shutdown();
            isListening = true;
        }
        for (ConnectionHandler ch : connections) {
            ch.shutConnectionDown();
        }
    }

    /**
     * The ConnectionHandler class is responsible for handling individual client connections.
     * It reads messages from the client and processes them accordingly.
     */
    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objOut;

        /**
         * Constructor for the ConnectionHandler class.
         *
         * @param client the client socket.
         * @param objOut the ObjectOutputStream for sending objects to the client.
         */
        public ConnectionHandler(Socket client, ObjectOutputStream objOut) {
            this.client = client;
            this.objOut = objOut;
        }

        /**
         * Runs the connection handler, reading messages from the client and responding or processing them.
         */
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message;


                while ((message = in.readLine()) != null) {
                    // Response client ping
                    if (message.equals("ping")) {
                        out.flush();
                        out.println("here ".concat(serverPort));
                    }
                    /*
                    Pour envoyer des informations à la pointeuse
                    else if (message.equals("resendEnterprise")) {
                        System.out.println("test");
                        //objOut.writeObject(DataSerialize.getInstance().getEnterpriseClassByPort(serverPort));
                        //objOut.flush();
                    }*/
                    else {
                        // On récupère le message de la pointeuse et la traite
                        String[] messageSplit = message.split(";");
                        if (messageSplit.length == 4) {
                            DataSerialize.getInstance().addNewWorkHour(messageSplit[2], messageSplit[1],
                                    LocalDate.now().toString(), messageSplit[0]);
                        }
                        System.out.printf("receive something else from client ! -> %s\n", message);
                    }
                }
            } catch (IOException e) {
                // todo : handle
            }
        }

        /**
         * Shuts down the client connection.
         *
         * @throws IOException if an I/O error occurs when closing the client connection.
         */
        public void shutConnectionDown() throws IOException {
            if (!client.isClosed()) {
                client.close();
                out.close();
                in.close();
            }
        }
    }
}
