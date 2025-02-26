package handlers;

import client.ClientController;
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
        var parameters = command.split(" ");

        if (object.getClass() == ClientController.class) {
            for (var part: parameters) {
                if (part.startsWith("-u=")) {
                    if (part.split("=").length < 2)
                        return "Something wrong with parameters";
                }
                else
                    return "Absent parameters";
            }
        } else if (object.getClass() == InputServerHandler.class) {
            var map = StringParser.parseCommand(command);

            if (ServerState.getInstance().login(map.get("u"), id))
                return "Successful login";
            else
                return "You're already logged in";
        }
        throw new RuntimeException();
    }
}
