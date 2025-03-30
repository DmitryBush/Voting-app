import server.handlers.LoginHandler;
import handlers.exceptions.IncorrectCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.entity.ServerState;
import server.entity.exceptions.NotLoggedIn;

public class CreateHandlerTest {
    @Test
    public void testCreateVote() {
        var handler = new LoginHandler();
        Assertions.assertEquals("",
                handler.handle("create vote -t=topic -vn=vote -vd=test -vc=2 -va=pass/nnot pass", ""));
    }
    @Test
    public void testCreateTopic() {
        var handler = new LoginHandler();
        Assertions.assertEquals("",
                handler.handle("create topic -n=topic", ""));
    }
    @Test
    public void testCreateEmpty() {
        var handler = new LoginHandler();
        Assertions.assertThrows(IncorrectCommand.class, () ->
                handler.handle("create", ""));
    }
    @Test
    public void testCreateIncomplete() {
        var handler = new LoginHandler();
        Assertions.assertThrows(IncorrectCommand.class, () ->
                handler.handle("create topic -n=", ""));
    }
    @Test
    public void testCreateServerVoteNotLoggedIn() {
        var handler = new LoginHandler();
        Assertions.assertThrows(NotLoggedIn.class, () ->
                handler.handle("create vote -t=topic -vn=vote -vd=test -vc=2 -va=pass/nnot pass", ""));
    }
    @Test
    public void testCreateServerTopicNotLoggedIn() {
        var handler = new LoginHandler();
        Assertions.assertThrows(NotLoggedIn.class, () ->
                handler.handle("create topic -n=topic", ""));
    }
    @Test
    public void testCreateServerEmpty() {
        var handler = new LoginHandler();
        Assertions.assertThrows(IncorrectCommand.class, () ->
                handler.handle("create", ""));
    }
    @Test
    public void testCreateServerIncomplete() {
        var handler = new LoginHandler();
        ServerState.getInstance().login("username", "1");

        Assertions.assertThrows(IncorrectCommand.class, () ->
                handler.handle("create topic -n=", "1"));
    }
}
