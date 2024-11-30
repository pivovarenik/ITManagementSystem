
CREATE DATABASE it_personnel_management;
USE it_personnel_management;

CREATE TABLE companies (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           profile_picture VARCHAR(255),
                           phone VARCHAR(20),
                           email VARCHAR(255)
);

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255),
                       email VARCHAR(255) NOT NULL,
                       age INT,
                       country VARCHAR(100),
                       city VARCHAR(100),
                       profile_picture VARCHAR(255),
                       phone VARCHAR(20)
);
CREATE TABLE user_companies (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                user_id INT NOT NULL,
                                company_id INT NOT NULL,
                                job_title VARCHAR(255),
                                salary INT,
                                start_date DATE,
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE positions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           company_id INT NOT NULL, -- Компания, к которой относится позиция
                           title VARCHAR(255) NOT NULL, -- Название позиции
                           description TEXT, -- Описание позиции
                           requirements TEXT, -- Требования
                           is_open BOOLEAN DEFAULT TRUE, -- Открыта ли позиция
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Когда была добавлена позиция
                           FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE job_applications (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  user_id INT NOT NULL, -- Кто подал заявку
                                  position_id INT NOT NULL, -- На какую позицию
                                  application_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  status VARCHAR(50) DEFAULT 'в ожидании', -- Статус заявки
                                  FOREIGN KEY (user_id) REFERENCES users(id),
                                  FOREIGN KEY (position_id) REFERENCES positions(id)
);
CREATE TABLE company_orders (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                company_id INT,
                                order_description TEXT,
                                order_date DATE,
                                deadline DATE,
                                status VARCHAR(50),
                                FOREIGN KEY (company_id) REFERENCES companies(id)
);
CREATE TABLE tasks (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       company_id INT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       created_by INT NOT NULL, -- ID начальника компании
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       deadline DATE,
                       status VARCHAR(50) DEFAULT 'waiting', -- Например: "в процессе", "завершено"
                       FOREIGN KEY (company_id) REFERENCES companies(id),
                       FOREIGN KEY (created_by) REFERENCES users(id)
);
CREATE TABLE subtasks (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          task_id INT NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          assigned_by INT NOT NULL, -- ID HR, который назначил подзадачу
                          assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          deadline DATE,
                          status VARCHAR(50) DEFAULT 'waiting', -- Например: "в процессе", "завершено"
                          FOREIGN KEY (task_id) REFERENCES tasks(id),
                          FOREIGN KEY (assigned_by) REFERENCES users(id)
);
CREATE TABLE task_assignments (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  subtask_id INT NOT NULL,
                                  user_id INT NOT NULL,
                                  assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (subtask_id) REFERENCES subtasks(id),
                                  FOREIGN KEY (user_id) REFERENCES users(id)
);