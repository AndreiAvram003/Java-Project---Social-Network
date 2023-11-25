package com.example.socialnetwork_gui.persistance.model.dtos;

import java.time.LocalDateTime;

public class FriendshipDto {
    private UserDto firstUser;
    private UserDto secondUser;
    private LocalDateTime createdAt;

    public FriendshipDto(UserDto firstUser, UserDto secondUser,LocalDateTime createdAt) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.createdAt = createdAt;
    }

    public UserDto getFirstUser() {
        return firstUser;
    }

    public UserDto getSecondUser() {
        return secondUser;
    }


    @Override
    public String toString() {
        return "Friendship{" +
                "firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", createdAt=" +createdAt+
                '}';
    }
}
