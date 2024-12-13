package service;

import com.google.gson.Gson;
import dao.UserDAO;
import entities.User;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class LoginService {
    public void login(BufferedReader reader, PrintWriter writer) {
        try {
            String message;
            message = reader.readLine();
            Gson gson = new Gson();
            User user = gson.fromJson(message, User.class);
            UserDAO userDAO = new UserDAO();
            if(userDAO.findByUsername(user.getUsername()) == null) {
                writer.println("Вы не авторизованы");
            }
            else if(!userDAO.comparePasswords(user)){
                writer.println("Неверный пароль!");
            }
            else{
                writer.println("Успешная авторизация!");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void findByUsername(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            UserDAO userdao = new UserDAO();
            String fullname = reader.readLine();
            User user = userdao.findByUsername(fullname);
            if(user != null) {
                writer.println(gson.toJson(user, User.class));
            }
            else writer.println("Error");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
