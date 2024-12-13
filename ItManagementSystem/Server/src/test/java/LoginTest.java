import com.google.gson.Gson;
import dao.UserDAO;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.LoginService;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginTest {

    private UserDAO userDAO;
    private Gson gson;

    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAO.class); // Mocking UserDAO
        gson = new Gson();
    }

    @Test
    void testUserNotFound() throws Exception {
        User inputUser = new User();
        String inputJson = gson.toJson(inputUser);

        BufferedReader reader = new BufferedReader(new StringReader(inputJson));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(userDAO.findByUsername("")).thenReturn(null);
        writer.println(inputJson);
        loginService.login(reader, writer);
        writer.flush();

        assertEquals("Вы не авторизованы\n", stringWriter.toString());
    }

    @Test
    void testInvalidPassword() throws Exception {
        // Arrange
        User inputUser = new User("admin", "wrongPassword", "", "", "", "", 0, "", "", "", "");
        String inputJson = gson.toJson(inputUser);

        BufferedReader reader = new BufferedReader(new StringReader(inputJson));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        User existingUser = new User("admin", "admin", "Пивоварчик Егор", "egorpivovarcik@gmail.com", "", "", 20, "Беларусь", "Барановичи", "+375336732473", "");

        when(userDAO.findByUsername("admin")).thenReturn(existingUser);
        when(userDAO.comparePasswords(inputUser)).thenReturn(false);

        LoginService loginService = new LoginService(userDAO);

        // Act
        loginService.login(reader, writer);
        writer.flush();

        // Assert
        assertEquals("Неверный пароль!\n", stringWriter.toString());
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // Arrange
        User inputUser = new User("admin", "admin", "", "", "", "", 0, "", "", "", "");
        String inputJson = gson.toJson(inputUser);

        BufferedReader reader = new BufferedReader(new StringReader(inputJson));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        User existingUser = new User("admin", "admin", "Пивоварчик Егор", "egorpivovarcik@gmail.com", "", "", 20, "Беларусь", "Барановичи", "+375336732473", "");

        when(userDAO.findByUsername("admin")).thenReturn(existingUser);
        when(userDAO.comparePasswords(inputUser)).thenReturn(true);

        LoginService loginService = new LoginService(userDAO);

        // Act
        loginService.login(reader, writer);
        writer.flush();

        // Assert
        assertEquals("Успешная авторизация!\n", stringWriter.toString());
    }
}
