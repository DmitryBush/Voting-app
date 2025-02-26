package client;

import handlers.LoginHandler;

import java.util.Scanner;

public class ClientController {
    public void Execute(){
        var running = true;
        Scanner scanner = new Scanner(System.in);

        while (running){
            if (processString(scanner.nextLine()))
                running = false;
        }
    }

    private boolean processString(String s){
        if (s.split(" ").length < 1)
            return false;
        else if (s.equalsIgnoreCase("exit"))
            return true;
        else
            new LoginHandler(this).handle(s, );
        return false;
    }
}
