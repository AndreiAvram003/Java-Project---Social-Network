package com.example.socialnetwork_gui.persistance.model.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDto {

    private UserDto from;

    private UserDto to;

    private String message;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    private LocalDateTime data;

    private MessageDto reply;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    private UUID uid;

    public MessageDto getReply() {
        return reply;
    }

    public void setReply(MessageDto reply) {
        this.reply = reply;
    }

    public MessageDto(UUID uid,UserDto from, UserDto to, String message, LocalDateTime data, MessageDto reply) {
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }

    public UserDto getFrom() {
        return from;
    }

    public void setFrom(UserDto from) {
        this.from = from;
    }

    public UserDto getTo() {
        return to;
    }

    public void setTo(UserDto to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return from.getUsername() +
                " : " + message;
    }
}
