package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;
import server.handlers.InputServerHandler;

public class VoteHandler extends CommandHandler {
    private final Object object;
    private final Logger logger = LoggerFactory.getLogger(VoteHandler.class);

    public VoteHandler(Object o) {
        super("vote", new DeleteHandler(o));

        object = o;
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
            if (map.isEmpty())
                throw new IncorrectCommand("Entered empty command");
            else if (map.size() != 3)
                throw new IncorrectCommand("Incomplete command entered");

            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
            return "";
        } else if (object.getClass() == InputServerHandler.class) {
            logger.debug("Command: {}\nMap: {}", command, map);
            if (ServerState.getInstance().vote(id, map.get("t"), map.get("v"), map.get("vc")))
                return "You have successfully voted";
            else {
                return "You're already voted";
            }
        } else
            throw new RuntimeException();
    }
}
