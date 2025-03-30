import server.handlers.DeleteHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.AnswerOption;
import server.entity.ServerState;
import server.entity.Vote;
import server.entity.exceptions.NotLoggedIn;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeleteHandlerTest {
    @Test
    void testDeleteEmpty() {
        var handler = new DeleteHandler();
        Assertions.assertThrows(IncorrectCommand.class,
                () -> handler.handle("delete", "123"));
    }

    @Test
    void testDeleteIncomplete() {
        var handler = new DeleteHandler();
        Assertions.assertThrows(IncorrectCommand.class,
                () -> handler.handle("delete -t= -v=task", "123"));
    }

    @Test
    void testDelete() {
        var handler = new DeleteHandler();
        Assertions.assertEquals("", handler.handle("delete -t=topic -v=task", "123"));
    }

    @Test
    void testServerDelete() {
        var handler = new DeleteHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "123");
        serverState.createTopic("topic", "123");
        serverState.createVote("topic", "123",
                new Vote("task",
                        "username",
                        "desc",
                        new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList("sure", "nope")))));

       Assertions.assertEquals("Vote deleted", handler.handle("delete -t=topic -v=task", "123"));
    }

    @Test
    void testServerDeleteNonExist() {
        var handler = new DeleteHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "123");

        Assertions.assertThrows(NoSuchElementException.class,
                () -> handler.handle("delete -t=type -v=val", "123"));
    }

    @Test
    void testServerDeleteEmpty() {
        var handler = new DeleteHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "123");

        Assertions.assertThrows(IncorrectCommand.class,
                () -> handler.handle("delete", "123"));
    }

    @Test
    void testServerDeleteIncomplete() {
        var handler = new DeleteHandler();
        var serverState = ServerState.getInstance();
        serverState.login("username", "123");

        Assertions.assertThrows(IncorrectCommand.class,
                () -> handler.handle("delete -t= v=task", "123"));
    }

    @Test
    void testServerDeleteNotLoggedIn() {
        var handler = new DeleteHandler();
        var serverState = ServerState.getInstance();

        serverState.login("username", "123");
        serverState.createTopic("topic1", "123");
        serverState.createVote("topic1", "123",
                new Vote("task2",
                        "username",
                        "desc",
                        new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList("sure", "nope")))));

        Assertions.assertThrows(NotLoggedIn.class,
                () -> handler.handle("delete -t=topic1 -v=task2", "456"));
    }
}
