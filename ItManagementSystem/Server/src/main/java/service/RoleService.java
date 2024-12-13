package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.RoleDAO;
import entities.Role;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

public class RoleService {
    public void getRoleById(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            RoleDAO roleDAO = new RoleDAO();
            Role role = roleDAO.findRoleById(Integer.parseInt(reader.readLine()));
            writer.println(gson.toJson(role, Role.class));
            System.out.println(gson.toJson(role, Role.class));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void getAllRoles(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new Gson();
            RoleDAO roleDAO = new RoleDAO();
            List<Role> role = roleDAO.findAll();
            Type listType = new TypeToken<List<Role>>() {}.getType();
            writer.println(gson.toJson(role, listType));
            System.out.println(gson.toJson(role, listType));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
