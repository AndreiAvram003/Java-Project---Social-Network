package com.example.socialnetwork_gui.exception;

public enum ResponseStatus {
    BAD_REQUEST(400),
    NOT_FOUND(404);

    final int status;

    ResponseStatus(int status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status;
    }
}
