package persistance.model.dtos;

public class FriendshipDto {
    private UserDto firstUser;
    private UserDto secondUser;

    public FriendshipDto(UserDto firstUser, UserDto secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
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
                '}';
    }
}
