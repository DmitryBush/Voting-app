package server.handlers;

public class ServerCommandHandler extends ServerAbstractHandler{
    public ServerCommandHandler() {
        super("", new LoginHandler());
    }

    @Override
    protected String process(String command, String id) {
        return "";
    }
}
