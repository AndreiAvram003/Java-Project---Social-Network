package com.example.socialnetwork_gui.request;

import java.util.UUID;

public class MessageRequest {

    private UUID from;
    private UUID to;

    private String message;

    public UUID getReply() {
        return reply;
    }

    public void setReply(UUID reply) {
        this.reply = reply;
    }

    private UUID reply;

    public MessageRequest(UUID from, UUID to, String message, UUID reply) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.reply = reply;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
