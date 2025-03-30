package server.handlers.request;

public interface RequestServerHandler {
    void setNext(RequestServerHandler handler);
    String handle(String command, String id);
}
