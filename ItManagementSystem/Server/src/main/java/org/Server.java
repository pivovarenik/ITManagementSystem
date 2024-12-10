package org;

import com.google.gson.Gson;
import dao.UserDAO;
import entities.User;
import service.LoginService;
import service.ProjectService;
import service.RoleService;
import service.UserService;
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
                case ALL_PROJECTS:
                    ProjectService prs = new ProjectService();
                    prs.getAllProjects(reader,writer);
                    break;
                case ALL_USERS:
                    UserService us = new UserService();
                    us.getAllUsers(reader,writer);
                    break;
                case USER_BY_NAME:
                    us = new UserService();
                    us.getUserByName(reader,writer);
                    break;
                case ROLE_BY_ID:
                    RoleService rls= new RoleService();
                    rls.getRoleById(reader,writer);
                    break;
                case MERGE_USER:
                    us= new UserService();
                    us.mergeUser(reader,writer);
                    break;
                case DELETE_USER:
                    us= new UserService();
                    us.deleteUser(reader,writer);
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
