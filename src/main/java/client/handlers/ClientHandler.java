package client.handlers;

public interface ClientHandler {
    void setNext(ClientHandler clientHandler);
    boolean handle(String command);
}
