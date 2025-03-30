package client.handlers;

import io.netty.channel.ChannelFuture;

public class ExitClientHandler extends CommandAbstractHandler{
    private final ChannelFuture future;
    public ExitClientHandler(ChannelFuture future) {
        super("exit", null);
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        future.channel().close();
        return true;
    }
}
