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
        try {
            future.channel().close().sync();
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
