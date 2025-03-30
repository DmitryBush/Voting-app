package server;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.entity.ServerState;

import java.io.*;
import java.util.Scanner;

public class ServerController {
    private final Logger logger = LoggerFactory.getLogger(ServerController.class);
    public void Execute(ChannelFuture future) {
        var running = true;
        Scanner scanner = new Scanner(System.in);

        while (running){
            if (processString(scanner.nextLine(), future))
                running = false;
        }
    }

    private boolean processString(String s, ChannelFuture future){
        if (s.equalsIgnoreCase("exit")) {
            future.channel().close();
            logger.info("Exiting app. Do not turn off the device");
            return true;
        }
        else if (s.equalsIgnoreCase("save")) {
            save();
            return false;
        }
        else if (s.equalsIgnoreCase("load")) {
            load();
            return false;
        }
        return false;
    }

    private void save() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("state.sav"))) {
            outputStream.writeObject(ServerState.getInstance());
            logger.info("Server state saved");
        } catch (IOException e) {
            logger.error("Unable to save state");
        }
    }

    private void load() {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("state.sav"))) {
            ServerState.getInstance().restoreState((ServerState) inputStream.readObject());
            logger.info("Server state loaded");
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Unable to load state");
        }
    }
}
