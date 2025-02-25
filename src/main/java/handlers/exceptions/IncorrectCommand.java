package handlers.exceptions;

public class IncorrectCommand extends RuntimeException {
    public IncorrectCommand(String message) {
        super(message);
    }
}
