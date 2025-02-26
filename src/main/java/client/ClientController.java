package client;

import handlers.LoginHandler;

import java.util.Scanner;

public class ClientController {
    public static void Execute(){
        var running = true;
        Scanner scanner = new Scanner(System.in);

        while (running){
            if (processString(scanner.nextLine()))
                running = false;
        }
    }

    private static boolean processString(String s){
        if (s.split(" ").length < 1)
            return false;
        else if (s.equalsIgnoreCase("exit"))
            return true;
        else
            new LoginHandler(ClientController.class).handle(s, null);
        return false;
    }
}
