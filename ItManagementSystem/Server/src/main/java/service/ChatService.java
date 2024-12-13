package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ChatDAO;
import entities.Chat;
import util.InstantAdapter;
import util.LocalDateAdapter;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ChatService {
    public void getChatsByUserId(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            ChatDAO chatDAO = new ChatDAO();
            int userId = Integer.parseInt(reader.readLine());
            List<Chat> chats = chatDAO.findChatsByUserId(userId);
            writer.println(gson.toJson(chats));
            System.out.println("Chats sent: " + gson.toJson(chats));
        } catch (Exception e) {
            System.out.println("Error while fetching chats: " + e.getMessage());
            writer.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    public void createChat(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            ChatDAO chatDAO = new ChatDAO();
            String message = reader.readLine();
            Chat chat = gson.fromJson(message, Chat.class);
            if(chat.getId()==0){
                chat.setId(null);
            }
            chatDAO.save(chat);
        } catch (Exception e) {
            System.out.println("Error while fetching chats: " + e.getMessage());
            writer.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    public void deleteChat(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            ChatDAO chatDAO = new ChatDAO();
            String message = reader.readLine();
            Chat chat = gson.fromJson(message, Chat.class);
            if(chat.getId()==0){
                chat.setId(null);
            }
            chatDAO.save(chat);
        } catch (Exception e) {
            System.out.println("Error while fetching chats: " + e.getMessage());
            writer.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}