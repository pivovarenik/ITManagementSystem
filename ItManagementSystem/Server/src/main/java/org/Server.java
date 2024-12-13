package org;

import com.google.gson.Gson;
import dao.UserDAO;
import entities.User;
import service.*;
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
                    UserService us = new UserService();
                    us.addUser(reader,writer);
                    break;
                case REG_USER:
                    us = new UserService();
                    us.register(reader,writer);
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
                    us = new UserService();
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
                case ALL_ROLES:
                    rls= new RoleService();
                    rls.getAllRoles(reader,writer);
                    break;
                case FIND_USER_BY_USERNAME:
                    lgs= new LoginService();
                    lgs.findByUsername(reader,writer);
                    break;
                case ALL_CHATS_OF_USER:
                    ChatService chs = new ChatService();
                    chs.getChatsByUserId(reader,writer);
                    break;
                case GET_CHAT_MESSAGES:
                    MessageService ms = new MessageService();
                    ms.getChatMessages(reader,writer);
                    break;
                case ADD_NEW_MSG:
                    ms = new MessageService();
                    ms.addMsg(reader,writer);
                    break;
                case ALL_BUT_USERS:
                    us= new UserService();
                    us.allButUsers(reader,writer);
                    break;
                case NEW_CHAT:
                    chs= new ChatService();
                    chs.createChat(reader,writer);
                    break;
                case DELETE_CHAT:
                    chs= new ChatService();
                    chs.deleteChat(reader,writer);
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
