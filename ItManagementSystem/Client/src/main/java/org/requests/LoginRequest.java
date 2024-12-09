package org.requests;

import com.google.gson.Gson;
import org.models.User;

import java.io.*;
import java.net.Socket;

public class LoginRequest {
    public static String send(User user) {
        String host = "localhost";
        int port = 8080;
        String message = "login";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            Gson gson = new Gson();
            String request = gson.toJson(user);
            writer.println(message);
            writer.println(request);
            return (reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

