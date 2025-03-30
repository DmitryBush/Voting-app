import server.handlers.request.LoginRequestHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginHandlerTest {
    @Test
    public void testSuccessfulLogin() {
        var handler = new LoginRequestHandler();
        Assertions.assertEquals("", handler.handle("login -u=username", ""));
    }
    @Test
    public void testEmptyCommand() {
        var handler = new LoginRequestHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("", ""));
    }
    @Test
    public void testIncompleteCommand() {
        var handler = new LoginRequestHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("login -u=", ""));
    }
    @Test
    public void testSuccessfulServerLogin() {
        var handler = new LoginRequestHandler();
        Assertions.assertEquals("Successful login", handler.handle("login -u=username", ""));
    }
    @Test
    public void testEmptyServerCommand() {
        var handler = new LoginRequestHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("", ""));
    }
    @Test
    public void testIncompleteServerCommand() {
        var handler = new LoginRequestHandler();
        assertThrows(IncorrectCommand.class, () ->handler.handle("login -u=", ""));
    }
}
