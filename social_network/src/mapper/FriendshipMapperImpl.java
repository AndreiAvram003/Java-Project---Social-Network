package mapper;

import persistance.model.Friendship;
import persistance.model.User;
import persistance.model.dtos.FriendshipDto;
import persistance.model.dtos.UserDto;

import java.util.List;
import java.util.Map;

public class FriendshipMapperImpl implements FriendshipMapper {
    @Override
    public Friendship toFriendship(User firstUser, User secondUser) {
        return new Friendship(firstUser.getId(), secondUser.getId());
    }

    @Override
    public FriendshipDto toDto(UserDto firstUser, UserDto secondUser) {
        return new FriendshipDto(firstUser, secondUser);
    }

    @Override
    public List<FriendshipDto> toDto(List<Friendship> friendships, Map<Long, UserDto> userIdToDtoMapping) {
        return friendships
                .stream()
                .map(friendship -> this.toDto(friendship, userIdToDtoMapping))
                .toList();
    }

    private FriendshipDto toDto(Friendship friendship, Map<Long, UserDto> userIdToDtoMapping) {
        UserDto firstUser = userIdToDtoMapping.get(friendship.getIdFirstUser());
        UserDto secondUser = userIdToDtoMapping.get(friendship.getIdSecondUser());
        return new FriendshipDto(firstUser, secondUser);
    }
}
