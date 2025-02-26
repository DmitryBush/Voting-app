package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class VoteHandler extends CommandHandler {
    private final Object object;

    public VoteHandler(Object o) {
        super("vote", new DeleteHandler(o));

        object = o;
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
        } else if (object.getClass() == InputServerHandler.class) {
            if (ServerState.getInstance().vote(id, map.get("t"), map.get("v"), map.get("vc")))
                return "You have successfully voted";
            else {
                return "Something wrong";
            }
        }
        throw new RuntimeException();
    }
}
