package com.example.socialnetwork_gui.request;

import java.util.UUID;

public class FilterByUidAndMonthRequest {
    private UUID userUUID;
    private Long luna;

    public FilterByUidAndMonthRequest(UUID userUUID, Long luna) {
        this.userUUID = userUUID;
        this.luna = luna;
    }


    public UUID getUserUUID() {
        return userUUID;
    }

    public Long getLuna() {
        return luna;
    }
}
