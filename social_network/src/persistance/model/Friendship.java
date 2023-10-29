package persistance.model;

public class Friendship extends Entity<Long> {
    private Long idFirstUser;
    private Long idSecondUser;

    public Friendship(Long id, Long idFirstUser, Long idSecondUser) {
        super(id);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
    }

    public Friendship(Long idFirstUser, Long idSecondUser) {
        super(null);
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
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
}
