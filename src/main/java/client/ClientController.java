package client;

import handlers.LoginHandler;
import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;

import java.util.Scanner;

public class ClientController {
    public void Execute(ChannelFuture future) {
        var running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            if (processString(scanner.nextLine(), future))
                running = false;
        }
    }

    private boolean processString(String s, ChannelFuture future) {
        try {
            if (s.split(" ").length < 1)
                return false;
            else if (s.equalsIgnoreCase("exit")) {
                future.channel().close();
                return true;
            }
            else {
                new LoginHandler(this).handle(s, null);
                future.channel().writeAndFlush(s);
            }
        } catch (IncorrectCommand e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}