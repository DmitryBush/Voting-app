package client.handlers;

import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

public class LoginClientHandler extends CommandAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginClientHandler.class);
    private final ChannelFuture future;
    public LoginClientHandler(ChannelFuture future) {
        super("login", new ViewClientHandler(future));
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        var map = StringParser.parseCommand(command);
        if (map.isEmpty())
            throw new IncorrectCommand("Entered command with empty parameters");
        map.forEach((key, value) -> {
            if (value == null)
                throw new IncorrectCommand("Occurred error near parameter value");
        });
        future.channel().writeAndFlush(command);
        return false;
    }
}
