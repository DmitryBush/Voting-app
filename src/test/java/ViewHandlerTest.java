import server.handlers.request.ViewRequestHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.ServerState;

import java.util.NoSuchElementException;

public class ViewHandlerTest {
    @Test
    public void testView() {
        var handler = new ViewRequestHandler();

        Assertions.assertEquals("", handler.handle("view", ""));
    }

    @Test
    public void testIncompleteCommand() {
        var handler = new ViewRequestHandler();

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", ""));
    }

    @Test
    public void testIncompleteServerCommand() {
        var handler = new ViewRequestHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testAbsentTopic() {
        var handler = new ViewRequestHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(NoSuchElementException.class, () -> handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testAbsentVote() {
        var handler = new ViewRequestHandler();
        ServerState.getInstance().login("username", "1");
        ServerState.getInstance().createTopic("qwerty", "1");

        Assertions.assertThrows(NoSuchElementException.class, ()
                -> handler.handle("view -t=topic -v=task", "1"));
    }
    @Test
    public void testServerView() {
        var handler = new ViewRequestHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");
        serverState.createTopic("topic", "1");

        Assertions.assertEquals("There are no votes in the topic",
                handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testEmptyServerCommand() {
        var handler = new ViewRequestHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testEmptyCommand() {
        var handler = new ViewRequestHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
}
