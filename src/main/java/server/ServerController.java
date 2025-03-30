package server;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.handlers.service.ServerServiceHandler;

import java.util.Scanner;

public class ServerController {
    private final Logger logger = LoggerFactory.getLogger(ServerController.class);
    public void Execute(ChannelFuture future) {
        var running = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                if (new ServerServiceHandler(future).handle(scanner.nextLine()))
                    running = false;
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }
}
