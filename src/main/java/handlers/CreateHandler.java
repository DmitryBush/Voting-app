package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import server.entity.AnswerOption;
import server.entity.ServerState;
import server.entity.Vote;
import server.handlers.InputServerHandler;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CreateHandler extends CommandHandler {
    private final Object object;
    private final Logger logger = LoggerFactory.getLogger(CreateHandler.class);

    public CreateHandler(Object o) {
        super("create", new VoteHandler(o));
        object = o;
    }
    @Override
    protected String process(String command, String id) {
        var map = StringParser.parseCommand(command);
        if (object.getClass() == ClientController.class) {
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
        } else if (object.getClass() == InputServerHandler.class) {
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
        throw new IncorrectCommand("Occurred error near parameter value");
    }
}
