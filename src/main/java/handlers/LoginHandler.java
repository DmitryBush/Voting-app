package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class LoginHandler extends CommandHandler {
    private final Object object;

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
            if (ServerState.getInstance().login(map.get("u"), id))
                return "Successful login";
            else
                return "You're already logged in";
        } else
            throw new RuntimeException();
    }
}
