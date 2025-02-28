import client.ClientController;
import handlers.ViewHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

import java.util.NoSuchElementException;

public class ViewHandlerTest {
    @Test
    public void testView() {
        var handler = new ViewHandler(new ClientController());

        Assertions.assertEquals("", handler.handle("view", ""));
    }

    @Test
    public void testIncompleteCommand() {
        var handler = new ViewHandler(new ClientController());

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", ""));
    }

    @Test
    public void testIncompleteServerCommand() {
        var handler = new ViewHandler(new InputServerHandler());
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testAbsentTopic() {
        var handler = new ViewHandler(new InputServerHandler());
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(NoSuchElementException.class, () -> handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testAbsentVote() {
        var handler = new ViewHandler(new InputServerHandler());
        ServerState.getInstance().login("username", "1");
        ServerState.getInstance().createTopic("qwerty", "1");

        Assertions.assertThrows(NoSuchElementException.class, ()
                -> handler.handle("view -t=topic -v=task", "1"));
    }
    @Test
    public void testServerView() {
        var handler = new ViewHandler(new InputServerHandler());
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");
        serverState.createTopic("topic", "1");

        Assertions.assertEquals("There are no votes in the topic",
                handler.handle("view -t=topic", "1"));
    }
    @Test
    public void testEmptyServerCommand() {
        var handler = new ViewHandler(new InputServerHandler());
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
    @Test
    public void testEmptyCommand() {
        var handler = new ViewHandler(new ClientController());
        var serverState = ServerState.getInstance();
        serverState.login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () -> handler.handle("view -t=", "1"));
    }
}
