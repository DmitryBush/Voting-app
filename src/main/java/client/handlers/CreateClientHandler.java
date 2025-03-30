package client.handlers;

import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

import java.util.Scanner;

class CreateClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(CreateClientHandler.class);
    private final ChannelFuture future;

    public CreateClientHandler(ChannelFuture future) {
        super("create", new VoteClientHandler(future));
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        var map = StringParser.parseCommand(command);

        if (map.isEmpty())
            throw new IncorrectCommand("Entered empty command");

        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        if (map.containsKey("t")) {
            command = createVote(command);
            StringParser.parseCommand(command);
            if (map.containsKey("va") && map.containsKey("vc")) {
                if (map.get("va").split("/n").length != Integer.parseInt(map.get("vc")))
                    throw new IncorrectCommand("Not enough answer options entered");
            }
            else
                throw new IncorrectCommand("Incomplete command entered to create a vote");
        }
        future.channel().writeAndFlush(command);
        return false;
    }

    private String createVote(String s) {
        try (var scanner = new Scanner(System.in)) {
            var sb = new StringBuilder(s);
            System.out.println("Enter a unique vote name");
            sb.append(" -vn=").append(scanner.nextLine());

            System.out.println("Enter a vote description");
            sb.append(" -vd=").append(scanner.nextLine());

            System.out.println("Enter number of answer options");
            var answerOptions = scanner.nextLine();
            sb.append(" -vc=").append(answerOptions).append(" -va=");
            System.out.println("Enter answer options");
            for (int i = 0; i < Integer.parseInt(answerOptions); i++) {
                sb.append(scanner.nextLine()).append("/n");
            }
            return sb.toString();
        }
    }
}
