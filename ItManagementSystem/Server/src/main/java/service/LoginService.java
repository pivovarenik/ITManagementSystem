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
}
