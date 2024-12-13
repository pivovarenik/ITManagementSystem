
CREATE DATABASE it_personnel_management;
USE it_personnel_management;

CREATE TABLE projects (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          start_date DATE NOT NULL,
                          deadline DATE NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          description TEXT,
                          percentage DECIMAL(5,2) DEFAULT 0
);
CREATE TABLE departments (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(255) NOT NULL,
                             description TEXT
);
CREATE TABLE roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       description TEXT
);
CREATE TABLE department_roles (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  department_id INT NOT NULL,
                                  role_id INT NOT NULL,
                                  UNIQUE(department_id, role_id),
                                  FOREIGN KEY (department_id) REFERENCES departments(id),
                                  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE,
                       role_id INT NOT NULL,
                       age INT,
                       country VARCHAR(255),
                       city VARCHAR(255),
                       phone VARCHAR(15),
                       profile_picture_url VARCHAR(255),
                       confirmed BOOLEAN DEFAULT FALSE,
                       FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE tasks (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       project_id INT NOT NULL,
                       description TEXT NOT NULL,
                       deadline DATE,
                       status VARCHAR(50) NOT NULL,
                       FOREIGN KEY (project_id) REFERENCES projects(id)
);


CREATE TABLE task_users (
                            task_id INT NOT NULL,
                            user_id INT NOT NULL,
                            FOREIGN KEY (task_id) REFERENCES tasks(id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            PRIMARY KEY (task_id, user_id)
);
CREATE TABLE chats (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       user_one_id INT NOT NULL,
                       user_two_id INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UNIQUE(user_one_id, user_two_id),
                       FOREIGN KEY (user_one_id) REFERENCES users(id),
                       FOREIGN KEY (user_two_id) REFERENCES users(id)
);

CREATE TABLE messages (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          chat_id INT NOT NULL,
                          sender_id INT NOT NULL,
                          message TEXT NOT NULL,
                          sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          is_read BOOLEAN DEFAULT FALSE,
                          FOREIGN KEY (chat_id) REFERENCES chats(id),
                          FOREIGN KEY (sender_id) REFERENCES users(id)
);
