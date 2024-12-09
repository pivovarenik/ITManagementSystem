package org;

import util.HibernatePreloader;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);
            HibernatePreloader.preload();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен");
                new Thread(new Server(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
