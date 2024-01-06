package com.example.socialnetwork_gui.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDeleteRequest {

    private UUID from;
    private UUID to;

    private String message;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    private LocalDateTime data;
    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply = reply;
    }

    private Long reply;


    public MessageDeleteRequest(UUID from, UUID to, String message, LocalDateTime data, Long reply) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = null;

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
