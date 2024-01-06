package com.example.socialnetwork_gui.persistance.model;

import java.time.LocalDateTime;

public class Friendship extends Entity<Long> {
    private Long idFirstUser;
    private Long idSecondUser;

    private LocalDateTime createdAt;


    private String status;

    public Friendship(Long id, Long idFirstUser, Long idSecondUser,LocalDateTime createdAt,String status) {
        super(id);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Friendship(Long idFirstUser, Long idSecondUser,LocalDateTime createdAt,String status) {
        super(null);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
