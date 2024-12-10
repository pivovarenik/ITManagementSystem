package org.requests;

import com.google.gson.Gson;
import org.models.Role;
import org.models.User;

import java.io.*;
import java.net.Socket;

public class RoleRequest {
    public static Role findRoleById(int id) {
        String host = "localhost";
        int port = 8080;
        String message = "role_by_id";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            writer.println(id);
            String jsonResponse = reader.readLine();
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, Role.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
