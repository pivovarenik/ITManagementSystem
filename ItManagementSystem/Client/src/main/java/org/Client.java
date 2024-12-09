package org;


import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import org.models.User;

public class Client {
    public static void send(){
        String host = "localhost";
        int port = 8080;
        try (Socket socket = new Socket(host, port);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
