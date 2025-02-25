package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.handlers.VoteServerHandler;

public class VoteHandler extends CommandHandler {
    private final Object object;

    public VoteHandler(Object o) {
        super("vote", new DeleteHandler(o));

        object = o;
    }
    @Override
    protected void process(String command) {
        if (object.getClass() == ClientController.class) {
            var map = StringParser.parseCommand(command);
            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
        } else if (object.getClass() == VoteServerHandler.class) {

        }
        else {
            throw new RuntimeException();
        }
    }
}
