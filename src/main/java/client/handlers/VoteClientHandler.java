package client.handlers;

import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

public class VoteClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(VoteClientHandler.class);

    public VoteClientHandler() {
        super("vote", new DeleteClientHandler());
    }

    @Override
    protected String process(String command) {
        var map = StringParser.parseCommand(command);
        if (map.isEmpty())
            throw new IncorrectCommand("Entered empty command");
        else if (map.size() != 3)
            throw new IncorrectCommand("Incomplete command entered");

        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        return "";
    }
}
