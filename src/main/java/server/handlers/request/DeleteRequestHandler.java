package server.handlers.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;

public class DeleteRequestHandler extends RequestServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(DeleteRequestHandler.class);

    public DeleteRequestHandler() {
        super("delete", null);
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        logger.debug("Command: {}\nMap: {}", command, map);

        if (ServerState.getInstance().delete(id, map.get("t"), map.get("v")))
            return "Vote deleted";
        else {
            return "Something went wrong";
        }
    }
}
