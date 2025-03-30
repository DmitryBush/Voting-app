package server.handlers.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.ServerState;

public class ViewRequestHandler extends RequestServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(ViewRequestHandler.class);

    public ViewRequestHandler() {
        super("view", new CreateRequestHandler());
    }

    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        logger.debug("Command: {}\nMap: {}", command, map);

        if (map.containsKey("v") && map.containsKey("t")) {
            return ServerState.getInstance().view(id, map.get("t"), map.get("v"));
        } else if (map.containsKey("t")) {
            return ServerState.getInstance().view(id, map.get("t"));
        } else {
            return ServerState.getInstance().view(id);
        }
    }
}
