package mapper;

import persistance.model.Friendship;
import persistance.model.User;
import persistance.model.dtos.FriendshipDto;
import persistance.model.dtos.UserDto;

import java.util.List;
import java.util.Map;

public interface FriendshipMapper {
    Friendship toFriendship(User firstUser, User secondUser);
    FriendshipDto toDto(UserDto firstUser, UserDto secondUser);
    List<FriendshipDto> toDto(List<Friendship> friendships, Map<Long, UserDto> userIdToDtoMapping);
}
