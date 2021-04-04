package sample;

import java.io.*;
import java.net.*;


public class ClientConnectionHandler implements Runnable {
    private Socket socket;
    private BufferedReader requestInput = null;
    public final String sharedPath = "C:/csci2020u/Assignment2/shared/";

    /**
     * Declares the client when a client connects, each client on seperate thread
     * @param socket
     */
    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Continuously waiting for client to send a request for information
     */
    public void run() {
        try {
            requestInput = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String command;
            String fileName;
            while ((command = requestInput.readLine()) != null) {
                switch (command.toUpperCase()) {

                    //Dir not included as UI shows the directory of shared

                    case "UPLOAD":
                        System.out.println("Upload");
                        //Get next input arguement
                        while ((fileName = requestInput.readLine()) != null) {
                            //Function to Download from client, passing filename
                            System.out.println(fileName);
                            getUpload();
                        }
                        break;
                    case "DOWNLOAD":
                        //Get next input arguement
                        while ((fileName = requestInput.readLine()) != null) {
                            //Function to upload to client, passing filename
                            System.out.println(fileName);
                            giveDownload(fileName);
                        }
                        break;
                    default:
                        //Never Reached with UI added
                        System.out.println("Bad Input");
                        break;
                }
                requestInput.close();
                break;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Initiates the download of a file from the client
     */
    public void getUpload(){
        System.out.println("Getting File");
        try {
            int bytesRead;

            //Setup data stream for receiving file / initialize buffer size
            DataInputStream clientGet = new DataInputStream(socket.getInputStream());
            String fileName = clientGet.readUTF();
            long size = clientGet.readLong();
            byte[] buffer = new byte[1024];
            File file = new File(sharedPath  + fileName);

            //If file does not exist, create file.
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file, false);

            //Send File
            while (size > 0 && (bytesRead = clientGet.read(buffer, 0, buffer.length - 0)) > -1) {
                outStream.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            System.out.println("Upload of " + fileName + "Complete");
            outStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /**
     * Initiates upload of file to the client, socket is closed.
     * @param fileName
     */
    public void giveDownload(String fileName){
        System.out.println("Giving File");
        try {
            //Initialize File and get it's length, store into byte array
            File file = new File(sharedPath + fileName);
            byte[] byteSize = new byte[(int) file.length()];

            //Initialize streams
            FileInputStream fileInput = new FileInputStream(file);
            DataInputStream dataInput = new DataInputStream(new BufferedInputStream(fileInput));
            dataInput.readFully(byteSize, 0, byteSize.length);
            OutputStream outStream = socket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dataOutput = new DataOutputStream(outStream);
            dataOutput.writeUTF(file.getName());
            dataOutput.writeLong(byteSize.length);
            dataOutput.write(byteSize, 0, byteSize.length);
            dataOutput.flush();
            System.out.println("File " + fileName + " sent to client.");

          } catch (Exception e) {
                e.printStackTrace();
          }
    }

}
