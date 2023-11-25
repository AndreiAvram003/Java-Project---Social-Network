package com.example.socialnetwork_gui.exception;

public interface ExceptionHandler {
    void handleParseException(ParseException parseException);

    void handleApplicationException(ApplicationException applicationException);
}
