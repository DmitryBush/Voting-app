package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class LoginHandler extends CommandHandler {
    private final Object object;
    private final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    public LoginHandler(Object o) {
        super("login", new ViewHandler(o));
        object = o;
    }

    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
            if (map.isEmpty())
                throw new IncorrectCommand("Entered command with empty parameters");
            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
            return "";
        } else if (object.getClass() == InputServerHandler.class) {
            logger.debug("Command: {}\nMap: {}", command, map);
            if (ServerState.getInstance().login(map.get("u"), id))
                return "Successful login";
            else
                return "You're already logged in";
        } else
            throw new RuntimeException();
    }
}
