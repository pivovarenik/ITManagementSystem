package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;
import dao.ChatDAO;
import dao.MessageDAO;
import entities.Chat;
import entities.Message;
import util.InstantAdapter;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;

public class MessageService {
    public void getChatMessages(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            MessageDAO msgDAO = new MessageDAO();
            int id = Integer.parseInt(reader.readLine());
            List<Message> messages = msgDAO.findMessagesByChatId(id);
            writer.println(gson.toJson(messages));
            System.out.println("Chats sent: " + gson.toJson(messages));
        } catch (Exception e) {
            System.out.println("Error while fetching chats: " + e.getMessage());
            writer.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    public void addMsg(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            MessageDAO msgDAO = new MessageDAO();
            String request = reader.readLine();
            Message msg = gson.fromJson(request, Message.class);
            if (msg.getId()==0){
                msg.setId(null);
            }
            System.out.println(msg);
            msgDAO.save(msg);
            writer.println("true");
        } catch (Exception e) {
            System.out.println("Error while fetching chats: " + e.getMessage());
            e.printStackTrace();
            writer.println("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
