package server.entity.exceptions;

public class NotUniqueName extends RuntimeException {
    public NotUniqueName(String message) {
        super(message);
    }
}
