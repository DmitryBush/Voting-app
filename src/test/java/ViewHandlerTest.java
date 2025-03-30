import server.handlers.ViewHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.ServerState;

import java.util.NoSuchElementException;

public class ViewHandlerTest {
    @Test
    public void testView() {
        var handler = new ViewHandler();

        Assertions.assertEquals("", handler.handle("view", ""));
    }

    @Test
    public void testIncompleteCommand() {
        var handler = new ViewHandler();

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", ""));
    }

    @Test
    public void testIncompleteServerCommand() {
        var handler = new ViewHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testAbsentTopic() {
        var handler = new ViewHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(NoSuchElementException.class, () -> handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testAbsentVote() {
        var handler = new ViewHandler();
        ServerState.getInstance().login("username", "1");
        ServerState.getInstance().createTopic("qwerty", "1");

        Assertions.assertThrows(NoSuchElementException.class, ()
                -> handler.handle("view -t=topic -v=task", "1"));
    }
    @Test
    public void testServerView() {
        var handler = new ViewHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");
        serverState.createTopic("topic", "1");

        Assertions.assertEquals("There are no votes in the topic",
                handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testEmptyServerCommand() {
        var handler = new ViewHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testEmptyCommand() {
        var handler = new ViewHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
}
