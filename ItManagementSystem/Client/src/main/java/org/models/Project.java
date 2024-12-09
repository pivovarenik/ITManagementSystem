package org.models;

import java.time.LocalDate;
import java.util.Arrays;

public class Project {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private String status;
    private String description;
    private Task[] tasks;
    private User[] users;
    private double percentage;

    public Project(int id, double percentage, User[] users, Task[] tasks, String description, String status, LocalDate deadline, LocalDate startDate, String name) {
        this.id = id;
        this.percentage = percentage;
        this.users = users;
        this.tasks = tasks;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.startDate = startDate;
        this.name = name;
    }

    public Project(LocalDate startDate, int id, String name, LocalDate deadline, String status, String description, double percentage) {
        this.startDate = startDate;
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.status = status;
        this.description = description;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + Arrays.toString(tasks) +
                ", users=" + Arrays.toString(users) +
                ", percentage=" + percentage +
                '}';
    }
}
