package server.handlers;

public interface ServerHandler {
    void setNext(ServerHandler handler);
    String handle(String command, String id);
}
