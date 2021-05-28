package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataOutputStream output = null;
        BufferedReader input = null;

        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String received;
            while ((received = input.readLine()) != null) {
                String requestArray[] = received.split(" ");

                if (requestArray[0].equals("GET")) {
                    System.out.println(requestArray[1]);

                    // If the resource equals "/" it should open index.html
                    String resourceName = requestArray[1].equals("/") ? "/index.html" : requestArray[1];

                    // Get the resource name and read its contents in the /www folder
                    String resourcePath = "./www" + resourceName;
                    Path filePath = Paths.get(resourcePath);

                    boolean fileExists = Files.exists(filePath, LinkOption.NOFOLLOW_LINKS);

                    if(!fileExists) filePath = Paths.get("./www/not-found.html");

                    String response = null;
                    byte[] fileContent = null;
                    int contentLength = 0;

                    if(fileExists) response = "HTTP/1.1 200 OK\r\n";
                    else response = "HTTP/1.1 404\r\n";

                    String mimeType = Files.probeContentType(filePath);
                    fileContent = Files.readAllBytes(filePath);
                    contentLength = fileContent.length;

                    response += "Content-Type: " + mimeType + "\r\n" +
                            "Content-Length: " + String.valueOf(contentLength) +
                            "\r\n\r\n";

                    System.out.println("MIME Type: " + mimeType);

                    // Send the header
                    output.writeBytes(response);
                    // Send the content. Since first to last position
                    output.write(fileContent, 0, contentLength);

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
}