package client.handlers;

import handlers.exceptions.IncorrectCommand;

import java.util.Objects;

public abstract class CommandAbstractHandler implements ClientHandler {
    private final String baseCommand;
    private ClientHandler nextChain;

    public CommandAbstractHandler(String baseCommand, ClientHandler nextChain) {
        this.baseCommand = baseCommand;
        this.nextChain = nextChain;
    }

    @Override
    public void setNext(ClientHandler clientHandler) {
        this.nextChain = clientHandler;
    }

    @Override
    public String handle(String command) {
        if (baseCommand.equalsIgnoreCase(command.split(" ")[0]))
            return process(command);

        if (Objects.nonNull(nextChain))
            return nextChain.handle(command);
        throw new IncorrectCommand("Non-existent command entered");
    }

    protected abstract String process(String command);
}
