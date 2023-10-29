package exception;

public abstract class ApplicationException extends RuntimeException {
    private final ResponseStatus responseStatus;
    public ApplicationException(String message, ResponseStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
