package server.handlers.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;

public class LoginRequestHandler extends RequestServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginRequestHandler.class);

    public LoginRequestHandler() {
        super("login", new ViewRequestHandler());
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
