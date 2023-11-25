package com.example.socialnetwork_gui.persistance.model;

import java.time.LocalDateTime;

public class Friendship extends Entity<Long> {
    private Long idFirstUser;
    private Long idSecondUser;

    private LocalDateTime createdAt;

    public Friendship(Long id, Long idFirstUser, Long idSecondUser,LocalDateTime createdAt) {
        super(id);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.createdAt = createdAt;
    }

    public Friendship(Long idFirstUser, Long idSecondUser,LocalDateTime createdAt) {
        super(null);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.createdAt = createdAt;
    }

    public Long getIdFirstUser() {
        return idFirstUser;
    }

    public void setIdFirstUser(Long idFirstUser) {
        this.idFirstUser = idFirstUser;
    }

    public Long getIdSecondUser() {
        return idSecondUser;
    }

    public void setIdSecondUser(Long idSecondUser) {
        this.idSecondUser = idSecondUser;
    }

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(){this.createdAt = createdAt;}

    public Long getFriendId(Long userId){
        return userId.equals(idFirstUser) ? idSecondUser : idFirstUser;
    }
}
