package org;

import com.google.gson.Gson;
import dao.UserDAO;
import entities.User;
import service.LoginService;
import service.ProjectService;
import util.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable {
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            String message;
            message = reader.readLine();
            Command command = Command.valueOf(message.toUpperCase());
            switch (command) {
                case ADD_USER :
                    UserDAO userDAO = new UserDAO();
                    message = reader.readLine();
                    Gson gson = new Gson();
                    User user = gson.fromJson(message, User.class);
                    userDAO.save(user);
                    break;
                case LOGIN:
                   LoginService lgs = new LoginService();
                   lgs.login(reader,writer);
                   break;
                case PROJECTS:
                    ProjectService prs = new ProjectService();
                    prs.getAllProjects(reader,writer);
                    break;
                default :
                    System.out.println("Invalid command");
                    break;
            }

        }
        catch (SocketException e) {
        }
        catch (IOException e) {
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
