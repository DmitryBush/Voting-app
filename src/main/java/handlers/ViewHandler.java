package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.handlers.ViewServerHandler;

public class ViewHandler extends CommandHandler {
    private final Object object;

    public ViewHandler(Object o) {
        super("view", new CreateHandler(o));

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
        } else if (object.getClass() == ViewServerHandler.class) {

        }
        else {
            throw new RuntimeException();
        }
    }
}
