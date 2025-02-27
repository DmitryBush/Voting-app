package handlers;

import handlers.exceptions.IncorrectCommand;

import java.util.Objects;

public abstract class CommandHandler implements handler{
    private final String baseCommand;
    private handler nextChain;

    public CommandHandler(String baseCommand, handler nextChain) {
        this.baseCommand = baseCommand;
        this.nextChain = nextChain;
    }

    @Override
    public void setNext(handler handler) {
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
