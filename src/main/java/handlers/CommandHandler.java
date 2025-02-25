package handlers;

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
    public void handle(String command) {
        if (baseCommand.equalsIgnoreCase(command.split(" ")[0]))
            process(command);

        if (Objects.nonNull(nextChain))
            nextChain.handle(command);
    }

    protected abstract void process(String command);
}
