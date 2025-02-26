package handlers;

public interface handler {
    void setNext(handler handler);
    String handle(String command, String id);
}
