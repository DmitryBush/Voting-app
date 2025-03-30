import server.handlers.LoginHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginHandlerTest {
    @Test
    public void testSuccessfulLogin() {
        var handler = new LoginHandler();
        Assertions.assertEquals("", handler.handle("login -u=username", ""));
    }
    @Test
    public void testEmptyCommand() {
        var handler = new LoginHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("", ""));
    }
    @Test
    public void testIncompleteCommand() {
        var handler = new LoginHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("login -u=", ""));
    }
    @Test
    public void testSuccessfulServerLogin() {
        var handler = new LoginHandler();
        Assertions.assertEquals("Successful login", handler.handle("login -u=username", ""));
    }
    @Test
    public void testEmptyServerCommand() {
        var handler = new LoginHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("", ""));
    }
    @Test
    public void testIncompleteServerCommand() {
        var handler = new LoginHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("login -u=", ""));
    }
}
