package com.example.socialnetwork_gui.exception;

public class EntityInvalidException extends ApplicationException {

    public EntityInvalidException(String message) {
        super(message, ResponseStatus.BAD_REQUEST);
    }
}
