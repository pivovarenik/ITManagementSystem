package org.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.models.Chat;
import org.models.User;
import org.util.InstantAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ChatRequest {
    public static List<Chat> getUserChats(User user){
        String host = "localhost";
        int port = 8080;
        String message = "all_chats_of_user";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            writer.println(user.getId());
            String jsonResponse = reader.readLine();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            Type listType = new TypeToken<List<Chat>>() {}.getType();
            if(!Objects.equals(jsonResponse, "Error")) {
                return gson.fromJson(jsonResponse, listType);
            }
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean createNewChat(Chat chat){
        String host = "localhost";
        int port = 8080;
        String message = "NEW_CHAT";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            String jsonRequest = gson.toJson(chat);
            writer.println(jsonRequest);
            String jsonResponse = reader.readLine();
            if(!Objects.equals(jsonResponse, "Error")) {
                return true;
            }
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteChat(Chat selectedChat) {
        String host = "localhost";
        int port = 8080;
        String message = "delete_chat";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            String jsonRequest = gson.toJson(selectedChat);
            writer.println(jsonRequest);
            String jsonResponse = reader.readLine();
            if(!Objects.equals(jsonResponse, "Error")) {
                return true;
            }
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
