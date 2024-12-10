package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dao.ProjectDao;
import dao.UserDAO;
import dto.UserDTO;
import entities.Project;
import entities.Role;
import entities.User;
import jakarta.persistence.EntityManager;
import util.LocalDateAdapter;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    public void getAllUsers(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            List<User> users = userdao.findAll();
            List<UserDTO> userDTOS = users.stream()
                    .map(UserDTO::new)
                    .toList();
            Type listType = new TypeToken<List<UserDTO>>() {}.getType();
            writer.println(gson.toJson(userDTOS, listType));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void getUserByName(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String fullname = reader.readLine();
            User user = userdao.findByName(fullname);
            UserDTO userDTO = new UserDTO(user);
            writer.println(gson.toJson(userDTO, UserDTO.class));
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
            UserDTO userDTO = gson.fromJson(userJson, UserDTO.class);
            User user = userDTO.toEntity();
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
            UserDTO userDTO = gson.fromJson(userJson, UserDTO.class);
            User user = userDTO.toEntity();
            userdao.delete(user);
            writer.println("OK");
        }
        catch(Exception e) {
            writer.println("ERROR");
            e.printStackTrace();
        }
    }
}
