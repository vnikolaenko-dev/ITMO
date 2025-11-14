package vnikolaenko.github.shared.exception;

public class ObjectAlreadyExists extends RuntimeException {
    public ObjectAlreadyExists(String message) {
        super(message);
    }
}
