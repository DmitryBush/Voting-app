package client.handlers;

import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

class CreateClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(CreateClientHandler.class);

    public CreateClientHandler() {
        super("create", new VoteClientHandler());
    }

    @Override
    protected String process(String command) {
        var map = StringParser.parseCommand(command);

        if (map.isEmpty())
            throw new IncorrectCommand("Entered empty command");

        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        if (map.containsKey("t")) {
            if (map.containsKey("va") && map.containsKey("vc")) {
                if (map.get("va").split("/n").length != Integer.parseInt(map.get("vc")))
                    throw new IncorrectCommand("Not enough answer options entered");
            }
            else
                throw new IncorrectCommand("Incomplete command entered to create a vote");
        }
        return "";
    }
}
