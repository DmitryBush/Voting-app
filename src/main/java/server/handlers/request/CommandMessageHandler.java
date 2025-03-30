package server.handlers.request;

public class CommandMessageHandler extends RequestServerAbstractHandler {
    public CommandMessageHandler() {
        super("", new LoginRequestHandler());
    }

    @Override
    protected String process(String command, String id) {
        return "";
    }
}
