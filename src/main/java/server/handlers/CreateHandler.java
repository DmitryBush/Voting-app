package server.handlers;

import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.AnswerOption;
import server.entity.ServerState;
import server.entity.Vote;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CreateHandler extends ServerAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(CreateHandler.class);

    public CreateHandler() {
        super("create", new VoteHandler());
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        logger.debug("Command: {}\nMap: {}", command, map);
        if (map.containsKey("n") && map.containsKey("t")) {
            ServerState.getInstance().createTopic(map.get("n"), id);
            return ServerState.getInstance()
                    .createVote(map.get("t"), id,
                            new Vote(map.get("vn"),
                                    null,
                                    map.get("vd"),
                                    new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList(map.get("va")
                                            .split("/n", Integer.parseInt(map.get("vc"))))))));
        } else if (map.containsKey("n")) {
            if (ServerState.getInstance().createTopic(map.get("n"), id))
                return "Topic created";
        } else if (map.containsKey("t")) {
            return ServerState.getInstance()
                    .createVote(map.get("t"), id,
                            new Vote(map.get("vn"),
                                    null,
                                    map.get("vd"),
                                    new AnswerOption(new CopyOnWriteArrayList<>(Arrays.asList(map.get("va")
                                            .split("/n", Integer.parseInt(map.get("vc"))))))));
        }
        throw new IncorrectCommand("Occurred error near parameter value");
    }
}
