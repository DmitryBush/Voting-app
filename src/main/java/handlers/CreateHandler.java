package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.handlers.CreateServerHandler;

public class CreateHandler extends CommandHandler {
    private final Object object;

    public CreateHandler(Object o) {
        super("create", new VoteHandler(o));

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
        } else if (object.getClass() == CreateServerHandler.class) {

        }
        else {
            throw new RuntimeException();
        }
    }
}
