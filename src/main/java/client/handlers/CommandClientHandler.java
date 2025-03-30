package client.handlers;

import io.netty.channel.ChannelFuture;

public class CommandClientHandler extends CommandAbstractHandler{
    public CommandClientHandler(ChannelFuture future) {
        super("", new LoginClientHandler(future));
    }

    @Override
    protected boolean process(String command) {return true;}
}
