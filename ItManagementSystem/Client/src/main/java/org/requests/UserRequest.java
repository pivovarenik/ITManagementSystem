package org.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.models.Project;
import org.models.User;
import org.util.LocalDateAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

public class UserRequest {
    public static List<User> findAll() {
        String host = "localhost";
        int port = 8080;
        String message = "all_users";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            String jsonResponse = reader.readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(jsonResponse, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User findUserByName(String name) {
        String host = "localhost";
        int port = 8080;
        String message = "user_by_name";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            writer.println(name);
            String jsonResponse = reader.readLine();
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean save(User user) {
        String host = "localhost";
        int port = 8080;
        String message = "merge_user";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            Gson gson = new Gson();
            String request = gson.toJson(user);
            System.out.println(request);
            writer.println(message);
            writer.println(request);
            return reader.readLine().equals("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean delete(User user) {
            String host = "localhost";
            int port = 8080;
            String message = "delete_user";
            try (Socket socket = new Socket(host, port);
                 OutputStream output = socket.getOutputStream();
                 InputStream input = socket.getInputStream();
                 PrintWriter writer = new PrintWriter(output, true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                Gson gson = new Gson();
                String request = gson.toJson(user);
                System.out.println(request);
                writer.println(message);
                writer.println(request);
                return reader.readLine().equals("OK");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
}
