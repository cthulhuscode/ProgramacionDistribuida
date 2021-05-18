package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        PrintWriter output = null;
        BufferedReader input = null;

        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String received;
            while ((received = input.readLine()) != null) {
                String requestArray[] = received.split(" ");

                if (requestArray[0].equals("GET")) {
                    // Get the resource name and read its contents in the /www folder
                    // If the resource equals "/" it should open index.html
                    System.out.println(requestArray[1]);

                    // Update the htmlResponse variable with the file contents
                    File myPage = new File("./www/index.html");
                    String htmlResponse = readContent(myPage);

                    if(requestArray[1].equals("/about.html")){
                        myPage = new File("./www/about.html");
                        htmlResponse = readContent(myPage);
                    }

                    int contentLength = htmlResponse.length();

                    // This line should not be modified just yet
                    output.write("HTTP/1.1 200 OK\r\nContent-Length: " +
                            String.valueOf(contentLength) + "\r\n\r\n" + htmlResponse);

                    // We already sent the response, break the loop
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String readContent(File myFile){
        try{
            String data = new String(Files.readAllBytes(Paths.get(myFile.getAbsolutePath())));
            return data;
        }catch(IOException e){
            System.out.println("An error ocurred: " + e.getMessage());
            return "";
        }
    }
}