package server.handlers.service;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitHandler extends ServerServiceAbstractHandler {
    private final ChannelFuture future;
    private final Logger logger = LoggerFactory.getLogger(ExitHandler.class);

    public ExitHandler(ChannelFuture future) {
        super("exit", new SaveHandler(future));
        this.future = future;
    }

    @Override
    protected boolean process(String command) {
        try {
            future.channel().close().sync();
            logger.info("Exiting app. Do not turn off the device");
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
