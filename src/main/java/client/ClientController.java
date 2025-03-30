package client;

import client.handlers.CommandClientHandler;
import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;

import java.util.Scanner;

public class ClientController {
    private Logger logger = LoggerFactory.getLogger(ClientController.class);

    public void Execute(ChannelFuture future) {
        var running = true;

        try (var scanner = new Scanner(System.in)) {
            while (running) {
                if (processString(scanner.nextLine(), future))
                    running = false;
            }
        }
    }

    private boolean processString(String s, ChannelFuture future) {
        try {
            return new CommandClientHandler(future).handle(s);
        } catch (IncorrectCommand e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}