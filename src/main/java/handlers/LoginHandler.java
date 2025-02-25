package handlers;

import client.ClientController;
import server.handlers.LoginServerHandler;

public class LoginHandler extends CommandHandler {
    private final Object object;

    public LoginHandler(Object o) {
        super("login", new ViewHandler(o));
        object = o;
    }

    @Override
    protected void process(String command) {
        var parameters = command.split(" ");

        if (object.getClass() == ClientController.class) {
            for (var part: parameters) {
                if (part.startsWith("-u=")) {
                    if (part.split("=").length < 2)
                        throw new IndexOutOfBoundsException();
                }
                else
                    throw new RuntimeException();
            }
        } else if (object.getClass() == LoginServerHandler.class) {

        }
        else {
            throw new RuntimeException();
        }
    }
}
