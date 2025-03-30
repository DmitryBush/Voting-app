package server.handlers;

import handlers.exceptions.IncorrectCommand;

import java.util.Objects;

public abstract class ServerAbstractHandler implements ServerHandler{
    private final String baseCommand;
    private ServerHandler nextChain;

    public ServerAbstractHandler(String baseCommand, ServerHandler nextChain) {
        this.baseCommand = baseCommand;
        this.nextChain = nextChain;
    }

    @Override
    public void setNext(ServerHandler handler) {
        this.nextChain = handler;
    }

    @Override
    public String handle(String command, String id) {
        if (baseCommand.equalsIgnoreCase(command.split(" ")[0]))
            return process(command, id);

        if (Objects.nonNull(nextChain))
            return nextChain.handle(command, id);
        throw new IncorrectCommand("Non-existent command entered");
    }

    protected abstract String process(String command, String id);
}
