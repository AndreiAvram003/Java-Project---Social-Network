package com.example.socialnetwork_gui.request;

import java.util.UUID;

public class FriendshipRequest {
    private UUID firstUserUid;
    private UUID secondUserUid;


    private String status;



    public FriendshipRequest(UUID firstUserUid, UUID secondUserUid,String status) {
        this.firstUserUid = firstUserUid;
        this.secondUserUid = secondUserUid;
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public UUID getFirstUserUid() {
        return firstUserUid;
    }


    public void setFirstUserUid(UUID firstUserUid) {
        this.firstUserUid = firstUserUid;
    }

    public UUID getSecondUserUid() {
        return secondUserUid;
    }

    public void setSecondUserUid(UUID secondUserUid) {
        this.secondUserUid = secondUserUid;
    }
}
