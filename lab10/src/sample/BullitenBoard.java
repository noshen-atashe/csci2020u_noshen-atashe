package sample;

import java.io.*;
import java.net.*;

import static javafx.application.Application.launch;


public class BullitenBoard implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    public String message;

    public BullitenBoard(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            out = new DataOutputStream(outputStream);
            while ((message = in.readLine()) != null){
                System.out.println(message);
                FileWriter fw = new FileWriter("messages.txt");
                fw.write(message);
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
