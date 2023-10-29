package exception;

public interface ExceptionHandler {
    void handleParseException(ParseException parseException);

    void handleApplicationException(ApplicationException applicationException);
}
