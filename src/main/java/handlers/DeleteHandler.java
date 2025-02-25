package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.handlers.DeleteServerHandler;

public class DeleteHandler extends CommandHandler{
    private final Object object;

    public DeleteHandler(Object o) {
        super("delete", null);
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
        } else if (object.getClass() == DeleteServerHandler.class) {

        }
        else {
            throw new RuntimeException();
        }
    }
}
