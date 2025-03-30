package server.handlers.service;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.entity.ServerState;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveHandler extends ServerServiceAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(SaveHandler.class);

    public SaveHandler(ChannelFuture future) {
        super("save", new LoadHandler(future));
    }

    @Override
    protected boolean process(String command) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("state.sav"))) {
            outputStream.writeObject(ServerState.getInstance());
            logger.info("Server state saved");
            return true;
        } catch (IOException e) {
            logger.error("Unable to save state");
            throw new RuntimeException(e.getMessage());
        }
    }
}
