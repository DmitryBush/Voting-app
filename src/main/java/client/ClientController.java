package client;

import handlers.LoginHandler;
import handlers.exceptions.IncorrectCommand;
import io.netty.channel.ChannelFuture;
import parser.StringParser;

import java.util.Scanner;

public class ClientController {
    Scanner scanner = new Scanner(System.in);

    public void Execute(ChannelFuture future) {
        var running = true;

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

            if (s.split(" ")[0].equalsIgnoreCase("create")) {
                if (s.split(" ")[1].equalsIgnoreCase("vote"))
                    s = addCreateVoteInfo(s);
            }
            if (s.split(" ")[0].equalsIgnoreCase("vote"))
                s = addVoteInfo(s, future);

            new LoginHandler(this).handle(s, null);
            future.channel().writeAndFlush(s);
        } catch (IncorrectCommand e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    private String addCreateVoteInfo(String s) {
        var sb = new StringBuilder(s);
        System.out.println("Enter a unique vote name");
        sb.append(" -vn=").append(scanner.nextLine());

        System.out.println("Enter a vote description");
        sb.append(" -vd=").append(scanner.nextLine());

        System.out.println("Enter number of answer options");
        var answerOptions = scanner.nextLine();
        sb.append(" -vc=").append(answerOptions).append(" -va=");
        System.out.println("Enter answer options");
        for (int i = 0; i < Integer.parseInt(answerOptions); i++) {
            sb.append(scanner.nextLine()).append("/n");
        }
        return sb.toString();
    }

    private String addVoteInfo(String s, ChannelFuture future) {
        var sb = new StringBuilder(s);
        var map = StringParser.parseCommand(s);

        System.out.println("Select an answer option");
        future.channel().writeAndFlush(String.format("view -t=%s -v=%s", map.get("t"), map.get("v")));
        return sb.append(" -vc=").append(scanner.nextLine()).toString();
    }
}