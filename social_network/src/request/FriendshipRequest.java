package request;

import java.util.UUID;

public class FriendshipRequest {
    private UUID firstUserUid;
    private UUID secondUserUid;

    public FriendshipRequest(UUID firstUserUid, UUID secondUserUid) {
        this.firstUserUid = firstUserUid;
        this.secondUserUid = secondUserUid;
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
