package server.handlers.service;

import io.netty.channel.ChannelFuture;

public class ServerServiceHandler extends ServerServiceAbstractHandler {
    public ServerServiceHandler(ChannelFuture future) {
        super("", new ExitHandler(future));
    }

    @Override
    protected boolean process(String command) {
        return true;
    }
}
