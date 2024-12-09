package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.ProjectDao;
import dao.UserDAO;
import entities.Project;
import entities.User;
import util.LocalDateAdapter;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class ProjectService {
    public void getAllProjects(BufferedReader reader, PrintWriter writer) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            ProjectDao projectDAO = new ProjectDao();
            List<Project> projects = projectDAO.findAll();
            Type listType = new TypeToken<List<Project>>() {}.getType();
            writer.println(gson.toJson(projects, listType));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
