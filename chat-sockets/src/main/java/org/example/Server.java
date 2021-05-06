package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static int PORT = 4444;
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataIn;
    static DataOutputStream dataOut;

    public static void main(String [] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server running on localhost:"+PORT);
        socket = serverSocket.accept();
        System.out.println("A new client is connected" + socket);

        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());

        Thread inputThread = new InputThread(dataIn);
        inputThread.start();

        Thread outputThread = new OutputThread(dataOut);
        outputThread.start();
    }
}

class InputThread extends Thread implements Runnable{
    DataInputStream dataIn;

    public InputThread(DataInputStream dataIn){
        this.dataIn = dataIn;
    }

    @Override
    public void run() {
        while(true){
            try{
                String msg = dataIn.readUTF();
                System.out.println("Client: " + msg);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

class OutputThread extends Thread implements Runnable{
    DataOutputStream dataOut;
    Scanner reader = new Scanner(System.in);

    public OutputThread(DataOutputStream dataOut){
        this.dataOut = dataOut;
    }

    @Override
    public void run() {
        while(true){
            try{
                //System.out.print("You: ");
                String msg = reader.nextLine();
                if(msg.trim() != "" && msg.trim() != null && !msg.isEmpty())
                    dataOut.writeUTF(msg.trim());
                dataOut.flush();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}