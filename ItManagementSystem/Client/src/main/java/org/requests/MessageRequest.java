package org.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.models.Chat;
import org.models.Message;
import org.models.User;
import org.util.InstantAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class MessageRequest {
    public static List<Message> getChatMessages(Chat chat){
        String host = "localhost";
        int port = 8080;
        String message = "get_chat_messages";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(message);
            writer.println(chat.getId());
            String jsonResponse = reader.readLine();
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            Type listType = new TypeToken<List<Message>>() {}.getType();
            if(!Objects.equals(jsonResponse, "Error")) {
                return gson.fromJson(jsonResponse, listType);
            }
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean addNewMsg(Message message){
        String host = "localhost";
        int port = 8080;
        String request = "add_new_msg";
        try (Socket socket = new Socket(host, port);
             OutputStream output = socket.getOutputStream();
             InputStream input = socket.getInputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            writer.println(request);
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            String jsonmsg = gson.toJson(message);
            writer.println(jsonmsg);
            String jsonResponse = reader.readLine();
            if(Objects.equals(jsonResponse, "Error")) {
                return false;
            }
            else return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
