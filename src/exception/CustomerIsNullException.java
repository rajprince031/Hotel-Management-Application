package exception;

public class CustomerIsNullException extends RuntimeException {
    public CustomerIsNullException(String message) {
        super(message);
    }
}
