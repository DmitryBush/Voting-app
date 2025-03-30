package server.handlers.service;

public interface ServerHandler {
    void setNext(ServerHandler handler);
    boolean handle(String command);
}
