package com.example.socialnetwork_gui.persistance.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message extends Entity<Long> {

    Long from;
    Long to;

    String message;

    LocalDateTime data;

    Long reply;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    UUID uid;


    public Message(Long id,UUID uid,Long from, Long to, String message, LocalDateTime data, Long reply) {
        super(id);
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }

    public Message(UUID uid,Long from, Long to, String message, LocalDateTime data, Long reply) {
        super(null);
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }


    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long  getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply = reply;
    }
}
