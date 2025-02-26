package handlers;

public interface handler {
    void setNext(handler handler);
    void handle(String command, String id);
}
