package handlers;

import client.ClientController;
import handlers.exceptions.IncorrectCommand;
import parser.StringParser;
import server.entity.AnswerOption;
import server.entity.ServerState;
import server.entity.Vote;
import server.handlers.InputServerHandler;

import java.util.Arrays;

public class CreateHandler extends CommandHandler {
    private final Object object;

    public CreateHandler(Object o) {
        super("create", new VoteHandler(o));

        object = o;
    }
    @Override
    protected String process(String command, String id) {
        if (object.getClass() == ClientController.class) {
            var map = StringParser.parseCommand(command);
            map.forEach((key, value) -> {
                if (value == null)
                    throw new IncorrectCommand("Occurred error near parameter value");
            });
        } else if (object.getClass() == InputServerHandler.class) {
            var map = StringParser.parseCommand(command);

            if (map.containsKey("n") && map.containsKey("t")) {
                ServerState.getInstance().createTopic(map.get("n"), id);
                ServerState.getInstance()
                        .createVote(map.get("t"), id,
                                new Vote(map.get("vn"), null, map.get("vd"),
                                        new AnswerOption(Arrays.stream(map.get("va").split("/n",
                                                Integer.parseInt(map.get("vc")))).toList())));
            } else if (map.containsKey("n")) {
                if (ServerState.getInstance().createTopic(map.get("n"), id))
                    return "Topic created";
                else
                    return "Something go wrong";
            } else if (map.containsKey("t")) {
                ServerState.getInstance()
                        .createVote(map.get("t"), id,
                                new Vote(map.get("vn"), null, map.get("vd"),
                                        new AnswerOption(Arrays.stream(map.get("va").split("/n",
                                                Integer.parseInt(map.get("vc")))).toList())));
            } else
                return "Occurred error near parameter value";
        }
        throw new RuntimeException();
    }
}
