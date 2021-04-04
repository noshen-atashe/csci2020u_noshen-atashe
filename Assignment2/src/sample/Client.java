package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;




public class Client extends Application {
    private static Socket sock;
    protected static BufferedReader input;
    private static PrintWriter output;
    protected static String fileName;
    public static int port = 1785;
    private BorderPane layout;

    public static void main(String[] args) throws IOException {
        try {
            sock = new Socket("localhost", port);
            System.out.println("Connected");
            input = new BufferedReader(new InputStreamReader(System.in));
            launch(args);
        } catch (IOException e) {
            System.out.println("Connection Failed, disconnecting");
            sock.close();
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadUI();
        primaryStage.setTitle("File Share Service");
        primaryStage.setScene(new Scene(layout, 600, 600));
        primaryStage.show();
    }


    public static void giveUpload(String fileName){
        System.out.println("Giving File");
        try {
            //Initialize File and get it's length, store into byte array
            final String clientPath = "C:/csci2020u/Assignment2/client/";
            File file = new File(clientPath + fileName);
            byte[] byteSize = new byte[(int) file.length()];

            //Initialize streams
            FileInputStream fileInput = new FileInputStream(file);
            DataInputStream dataInput = new DataInputStream(new BufferedInputStream(fileInput));
            dataInput.readFully(byteSize, 0, byteSize.length);
            OutputStream outStream = sock.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dataOutput = new DataOutputStream(outStream);
            dataOutput.writeUTF(file.getName());
            dataOutput.writeLong(byteSize.length);
            dataOutput.write(byteSize, 0, byteSize.length);
            dataOutput.flush();
            System.out.println("File " + fileName + " has been sent to server");

            //Close socket and stream after transfer
            dataOutput.close();
            sock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getDownload(){
        System.out.println("Getting File");
        try {
            int bytesRead;
            final String clientPath = "C:/csci2020u/Assignment2/client/";

            //Setup data stream for receiving file / initialize buffer size
            DataInputStream clientGet = new DataInputStream(sock.getInputStream());
            String fileName = clientGet.readUTF();
            long size = clientGet.readLong();
            byte[] buffer = new byte[1024];
            File file = new File(clientPath + fileName);

            //If file does not exist, create file.
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file, false);

            //Send the file
            while (size > 0 && (bytesRead = clientGet.read(buffer, 0, buffer.length - 0)) > -1) {
                outStream.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            System.out.println("File " + fileName + " has been received from server");

            //Close socket and streams after transfer
            outStream.close();
            clientGet.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadUI(){
        String clientPath = "C:/csci2020u/Assignment2/client/";
        String serverPath = "C:/csci2020u/Assignment2/shared/";
        //Final variables used to loophole declaring separate functions for listeners
        final String[] clientRequest = new String[1];
        final String[] serverRequest = new String[1];

        //Initialize both list views
        ListView<File> serverList = new ListView<>();
        ListView<File> clientList = new ListView<>();
        serverList.setMinWidth(300);
        serverList.setMinHeight(550);
        clientList.setMinWidth(300);
        clientList.setMinHeight(550);

        //Initialize gridpane for buttons
        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(50);

        //Initialize flowpane for list views
        FlowPane flowPane = new FlowPane();
        flowPane.setMinWidth(600);
        flowPane.setMinHeight(550);
        flowPane.getChildren().addAll(serverList,clientList);

        //Listeners for an item selected in list view
        serverList.getSelectionModel().getSelectedItem();
        serverList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            serverRequest[0] = selected.toString();
        });
        clientList.getSelectionModel().getSelectedItem();
        clientList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selected) -> {
            clientRequest[0] = selected.toString();
        });

        //Declare client output reader for button responses
        try{
            output = new PrintWriter(sock.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

        Button upButton = new Button("Upload");
        upButton.setMinWidth(70);
        upButton.setMinHeight(50);
        upButton.setOnAction(action -> {
            try {
                if (clientRequest[0] != null) {
                    output.println("UPLOAD");
                    output.flush();
                    output.println(clientRequest[0]);
                    output.flush();
                    giveUpload(clientRequest[0]);
                    sock.close();
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button downButton = new Button("Download");
        downButton.setMinWidth(70);
        downButton.setMinHeight(50);
        downButton.setOnAction(action -> {
            try {
                if (serverRequest[0] != null) {
                    output.println("DOWNLOAD");
                    output.flush();
                    output.println(serverRequest[0]);
                    output.flush();
                    getDownload();
                    sock.close();
                    System.exit(0);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        gridPane.add(upButton,1,1);
        gridPane.add(downButton,2,1);

        //Generate ListViews for shared / client folders
        ArrayList<File> serverFolder = getFiles(new File(serverPath));
        ObservableList<File> oServerFolder = FXCollections.observableArrayList(serverFolder);
        serverList.setItems(oServerFolder);

        ArrayList<File> clientFolder = getFiles(new File(clientPath));
        ObservableList<File> oClientFolder = FXCollections.observableArrayList(clientFolder);
        clientList.setItems(oClientFolder);

        //Set up layout
        layout = new BorderPane();
        layout.setCenter(gridPane);
        layout.setBottom(flowPane);


    }


    public ArrayList<File> getFiles(File folder) {
        ArrayList<File> files = new ArrayList<>();
        if(folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    getFiles(file);
                } else {
                    String pFile = file.toString();
                    int breakPath;
                    if (pFile.contains("shared")){
                        breakPath = pFile.lastIndexOf("shared/");
                    }
                    else if(pFile.contains("client")){
                        breakPath = pFile.lastIndexOf("client/");
                    }else {
                        breakPath = 39;
                    }
                    File parsedFile = new File(pFile.substring(breakPath+7));
                    files.add(parsedFile);
                }
            }
        }
        return files;
    }
}
