package request;

import java.util.UUID;

public class UserDeleteRequest {
    private UUID userUid;

    public UserDeleteRequest(UUID userUid) {
        this.userUid = userUid;
    }

    public UUID getUserUid() {
        return userUid;
    }
}
