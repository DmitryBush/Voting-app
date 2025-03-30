package server.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;

public class LoginHandler extends ServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    public LoginHandler() {
        super("login", new ViewHandler());
    }

    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        logger.debug("Command: {}\nMap: {}", command, map);

        if (ServerState.getInstance().login(map.get("u"), id))
            return "Successful login";
        else
            return "You're already logged in";
    }
}
