package server.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;

public class VoteHandler extends ServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(VoteHandler.class);

    public VoteHandler() {
        super("vote", new DeleteHandler());
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        logger.debug("Command: {}\nMap: {}", command, map);
        if (ServerState.getInstance().vote(id, map.get("t"), map.get("v"), map.get("vc")))
            return "You have successfully voted";
        else
            return "You're already voted";
    }
}
