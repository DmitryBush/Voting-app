package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class ViewHandler extends CommandHandler {
    private final Object object;

    public ViewHandler(Object o) {
        super("view", new CreateHandler(o));

        object = o;
    }

    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
            if (map.size() > 2)
                throw new IncorrectCommand("Entered incorrect command");

            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
            return "";
        } else if (object.getClass() == InputServerHandler.class) {
            if (map.containsKey("v") && map.containsKey("t")) {
                return ServerState.getInstance().view(id, map.get("t"), map.get("v"));
            } else if (map.containsKey("t")) {
                return ServerState.getInstance().view(id, map.get("t"));
            } else {
                return ServerState.getInstance().view(id);
            }
        } else
            throw new RuntimeException();
    }
}
