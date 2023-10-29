package exception;

public class ExceptionHandlerImpl implements ExceptionHandler {
    public void handleApplicationException(ApplicationException exception) {
        System.out.printf("%s ERROR: %s%n", exception.getResponseStatus().getStatusCode(), exception.getMessage());
    }

    public void handleParseException(ParseException exception) {
        System.out.printf("PARSE ERROR: %s%n", exception.getMessage());
    }
}
