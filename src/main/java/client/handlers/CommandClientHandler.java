package client.handlers;

public class CommandClientHandler extends CommandAbstractHandler{
    public CommandClientHandler() {
        super("", new LoginClientHandler());
    }

    @Override
    protected String process(String command) {
        return "";
    }
}
