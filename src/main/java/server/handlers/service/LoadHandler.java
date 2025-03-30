package server.handlers.service;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.entity.ServerState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadHandler extends ServerServiceAbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoadHandler.class);

    public LoadHandler(ChannelFuture future) {
        super("load", null);
    }

    @Override
    protected boolean process(String command) {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("state.sav"))) {
            ServerState.getInstance().restoreState((ServerState) inputStream.readObject());
            logger.info("Server state loaded");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Unable to load state");
            throw new RuntimeException(e.getMessage());
        }
    }
}
