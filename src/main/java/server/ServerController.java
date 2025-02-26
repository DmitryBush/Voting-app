package server;

import server.entity.ServerState;

import java.io.*;
import java.util.Scanner;

public class ServerController {
    public static void Execute() {
        var running = true;
        Scanner scanner = new Scanner(System.in);

        while (running){
            if (processString(scanner.nextLine()))
                running = false;
        }
    }

    private static boolean processString(String s){
        if (s.equalsIgnoreCase("exit"))
            return true;
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

    private static void save() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("state.sav"))) {
            outputStream.writeObject(ServerState.getInstance());
        } catch (IOException e) {
            System.out.println("Unable to save state");
        }
    }

    private static void load() {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("state.sav"))) {
            ServerState.getInstance().restoreState((ServerState) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to load state");
        }
    }
}
