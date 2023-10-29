package exception;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super(message, ResponseStatus.NOT_FOUND);
    }
}
