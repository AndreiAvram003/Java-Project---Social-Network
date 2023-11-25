package com.example.socialnetwork_gui.request;

import java.util.UUID;

public class UserDeleteRequest {
    private final UUID userUid;

    public UserDeleteRequest(UUID userUid) {
        this.userUid = userUid;
    }

    public UUID getUserUid() {
        return userUid;
    }
}
