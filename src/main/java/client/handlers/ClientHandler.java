package client.handlers;

public interface ClientHandler {
    void setNext(ClientHandler clientHandler);
    String handle(String command);
}
