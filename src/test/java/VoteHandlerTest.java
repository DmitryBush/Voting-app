import server.handlers.VoteHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.AnswerOption;
import server.entity.ServerState;
import server.entity.Vote;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class VoteHandlerTest {
    @Test
    public void testServerVote() {
        var handler = new VoteHandler();
        var server = ServerState.getInstance();
        server.login("username", "1");
        server.createTopic("topic1", "1");
        server.createVote("topic1", "1",
                new Vote("task",
                        "username",
                        "",
                        new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList("sure", "nope")))));

        Assertions.assertEquals("You have successfully voted",
                handler.handle("vote -t=topic -v=task -vc=0", "1"));
    }
    @Test
    public void testVote() {
        var handler = new VoteHandler();

        Assertions.assertEquals("",
                handler.handle("vote -t=topic -v=task -vc=0", "1"));
    }

    @Test
    public void testIncompleteVote() {
        var handler = new VoteHandler();

        Assertions.assertThrows(IncorrectCommand.class,
                ()-> handler.handle("vote -t=topic -v= -vc=0", "1"));
    }
    @Test
    public void testServerIncompleteVote() {
        var handler = new VoteHandler();
        var server = ServerState.getInstance();
        server.login("username", "1");
        server.createTopic("topic", "1");
        server.createVote("topic", "1",
                new Vote("task",
                        "username",
                        "",
                        new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList("sure", "nope")))));

        Assertions.assertThrows(IncorrectCommand.class,
                () -> handler.handle("vote -t=topic -v= -vc=0", "1"));
    }
    @Test
    public void testReVote() {
        var handler = new VoteHandler();
        var server = ServerState.getInstance();
        server.login("username", "1");
        server.createTopic("topic2", "1");
        server.createVote("topic2", "1",
                new Vote("task",
                        "username",
                        "",
                        new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList("sure", "nope")))));
        server.vote("1", "topic2", "task", "0");

        Assertions.assertEquals("You're already voted",
                handler.handle("vote -t=topic2 -v=task -vc=0", "1"));
    }
}
