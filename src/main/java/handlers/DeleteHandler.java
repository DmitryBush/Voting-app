package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class DeleteHandler extends CommandHandler{
    private final Object object;

    public DeleteHandler(Object o) {
        super("delete", null);
        object = o;
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
            if (map.isEmpty())
                throw new IncorrectCommand("Entered empty command");
            else if (map.size() != 2)
                throw new IncorrectCommand("Incomplete command entered");

            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
            return "";
        } else if (object.getClass() == InputServerHandler.class) {
            if (ServerState.getInstance().delete(id, map.get("t"), map.get("v")))
                return "Vote deleted";
            else {
                return "Something went wrong";
            }
        } else
            throw new RuntimeException();
    }
}
