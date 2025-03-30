package server.handlers.service;

import handlers.exceptions.IncorrectCommand;

import java.util.Objects;

public abstract class ServerServiceAbstractHandler implements ServerHandler {
    private final String baseCommand;
    private ServerHandler nextChain;

    public ServerServiceAbstractHandler(String baseCommand, ServerHandler nextChain) {
        this.baseCommand = baseCommand;
        this.nextChain = nextChain;
    }

    @Override
    public void setNext(ServerHandler handler) {
        this.nextChain = handler;
    }

    @Override
    public boolean handle(String command) {
        if (baseCommand.equalsIgnoreCase(command.split(" ")[0]))
            return process(command);

        if (Objects.nonNull(nextChain))
            return nextChain.handle(command);
        throw new IncorrectCommand("Non-existent command entered");
    }

    protected abstract boolean process(String command);
}
