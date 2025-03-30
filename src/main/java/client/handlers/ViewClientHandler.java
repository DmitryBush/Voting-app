package client.handlers;

import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

public class ViewClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(ViewClientHandler.class);
    private final ChannelFuture future;
    public ViewClientHandler(ChannelFuture future) {
        super("view", new CreateClientHandler(future));
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        var map = StringParser.parseCommand(command);
        if (map.size() > 2)
            throw new IncorrectCommand("Entered incorrect command");

        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        future.channel().writeAndFlush(command);
        return false;
    }
}
