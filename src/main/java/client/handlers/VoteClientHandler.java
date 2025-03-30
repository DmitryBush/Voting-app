package client.handlers;

import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

import java.util.Scanner;

public class VoteClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(VoteClientHandler.class);
    private final ChannelFuture future;

    public VoteClientHandler(ChannelFuture future) {
        super("vote", new DeleteClientHandler(future));
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        command = vote(command);
        var map = StringParser.parseCommand(command);

        if (map.isEmpty())
            throw new IncorrectCommand("Entered empty command");
        else if (map.size() != 3)
            throw new IncorrectCommand("Incomplete command entered");

        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        future.channel().writeAndFlush(command);
        return false;
    }

    private String vote(String command) {
        try (var scanner= new Scanner(System.in)) {
            var sb = new StringBuilder(command);
            var map = StringParser.parseCommand(command);

            System.out.println("Select an answer option");
            future.channel().writeAndFlush(String.format("view -t=%s -v=%s", map.get("t"), map.get("v")));
            return sb.append(" -vc=").append(Integer.parseInt(scanner.nextLine()) - 1).toString();
        }
    }
}
