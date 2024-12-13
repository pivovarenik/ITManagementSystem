package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import dao.UserDAO;
import entities.User;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class UserService {
    public void getAllUsers(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            List<User> users = userdao.findAll();
            Type listType = new TypeToken<List<User>>() {}.getType();
            writer.println(gson.toJson(users, listType));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
     public void register(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String userSerialized = reader.readLine();
            System.out.println(userSerialized);
            JsonObject userJson = gson.fromJson(userSerialized, JsonObject.class);
            User user = gson.fromJson(userJson,User.class);
            System.out.println(user);
            userdao.update(user);
            writer.println("OK");
        }
        catch(Exception e) {
            writer.println("ERROR");
            e.printStackTrace();
        }
    }
    public void getUserByName(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String fullname = reader.readLine();
            User user = userdao.findByName(fullname);
            if(user != null) {

                writer.println(gson.toJson(user, User.class));
            }
            else writer.println("Error");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void mergeUser(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String userSerialized = reader.readLine();
            JsonObject userJson = gson.fromJson(userSerialized, JsonObject.class);
            System.out.println(userJson.toString());
            User user = gson.fromJson(userJson, User.class);
            userdao.update(user);
            writer.println("OK");
        }
        catch(Exception e) {
            writer.println("ERROR");
            e.printStackTrace();
        }
    }
    public void deleteUser(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String userSerialized = reader.readLine();
            JsonObject userJson = gson.fromJson(userSerialized, JsonObject.class);
            User user = gson.fromJson(userJson, User.class);
            userdao.delete(user);
            writer.println("OK");
        }
        catch(Exception e) {
            writer.println("ERROR");
            e.printStackTrace();
        }
    }
    public void addUser(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String userSerialized = reader.readLine();
            JsonObject userJson = gson.fromJson(userSerialized, JsonObject.class);
            System.out.println(userJson.toString());
            User user = gson.fromJson(userJson, User.class);
            if(user.getId() == 0) {
                user.setId(null);
            }
            userdao.save(user);
            writer.println("OK");
        }
        catch(Exception e) {
            writer.println("ERROR");
            e.printStackTrace();
        }
    }
    public void allButUsers(BufferedReader reader, PrintWriter writer) {
        try {
            String jsonUsers = reader.readLine();
            System.out.println(jsonUsers);
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            Type collectionType = new TypeToken<Collection<Integer>>() {}.getType();
            Collection<Integer> userIds = gson.fromJson(jsonUsers, collectionType);
            System.out.println(userIds);
            List<User> users = userdao.getUsersExcluding(userIds);

            String jsonResponse = gson.toJson(users);
            writer.println(jsonResponse);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
