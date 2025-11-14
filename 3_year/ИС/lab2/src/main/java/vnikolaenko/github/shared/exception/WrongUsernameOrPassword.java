package vnikolaenko.github.shared.exception;

public class WrongUsernameOrPassword extends RuntimeException {
    public WrongUsernameOrPassword(String message) {
        super(message);
    }
}
