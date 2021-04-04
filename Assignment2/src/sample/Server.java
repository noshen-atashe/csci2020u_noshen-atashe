package sample;

import java.io.*;
import java.net.*;


public class Server {
    protected ServerSocket serverSocket;
    private static Socket clientSocket = null;
    public static int port = 1785;

    /**
     * General Constructor, launches the server.
     * @throws IOException
     */
    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server Started");
    }

    /**
     * Creates a new thread for any new clients which connect
     * @throws IOException
     */
    public void handleRequests() throws IOException {
        System.out.println("Server is listening on port: " + port);
        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("Accepted connection : " + clientSocket);
            ClientConnectionHandler handler = new ClientConnectionHandler(clientSocket);
            Thread handlerThread = new Thread(handler);
            handlerThread.start();
        }
    }
    public static void main(String[] args) {
        try{
            Server server = new Server();
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
