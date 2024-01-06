package com.example.socialnetwork_gui.persistance.model.dtos;

import java.time.LocalDateTime;

public class FriendDto {
    private String friendUsername;
    private String friendEmail;
    private LocalDateTime friendsFrom;

    public FriendDto(String friendUsername, String friendEmail, LocalDateTime friendsFrom) {
        this.friendUsername = friendUsername;
        this.friendEmail = friendEmail;
        this.friendsFrom = friendsFrom;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    @Override
    public String toString() {
        return String.format(
                "friend_username = %s | friend_email = %s | friends_from = %s",
                friendUsername,
                friendEmail,
                friendsFrom
        );
    }
}