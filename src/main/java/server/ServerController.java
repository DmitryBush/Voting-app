package server;

import io.netty.channel.ChannelFuture;
import server.handlers.service.ServerServiceHandler;

import java.util.Scanner;

public class ServerController {
    public void Execute(ChannelFuture future) {
        var running = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                if (new ServerServiceHandler(future).handle(scanner.nextLine()))
                    running = false;
            }
        }
    }
}
