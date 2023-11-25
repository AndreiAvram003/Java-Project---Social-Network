package com.example.socialnetwork_gui.mapper;



import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FriendshipMapperImpl implements FriendshipMapper {
    @Override
    public Friendship toFriendship(User firstUser, User secondUser) {
        return new Friendship(firstUser.getId(), secondUser.getId(), LocalDateTime.now());
    }

    @Override
    public FriendshipDto toDto(UserDto firstUser, UserDto secondUser) {
        return new FriendshipDto(firstUser, secondUser,LocalDateTime.now());
    }

    @Override
    public List<FriendshipDto> toDto(List<Friendship> friendships, Map<Long, UserDto> userIdToDtoMapping) {
        return friendships
                .stream()
                .map(friendship -> this.toDto(friendship, userIdToDtoMapping))
                .toList();
    }


    @Override
    public List<FriendDto> toFriendDto(List<Friendship> friendships, Long id, Map<Long, User> userIdToEntityMapping) {
        return friendships
                .stream()
                .map(friendship -> this.toFriendDto(friendship, id, userIdToEntityMapping))
                .toList();
    }

    private FriendDto toFriendDto(Friendship friendship, Long id, Map<Long, User> userIdToEntityMapping) {
        User friend = userIdToEntityMapping.get(friendship.getFriendId(id));
        return new FriendDto(
                friend.getUsername(),
                friend.getEmail(),
                friendship.getCreatedAt()
        );
    }

    private FriendshipDto toDto(Friendship friendship, Map<Long, UserDto> userIdToDtoMapping) {
        UserDto firstUser = userIdToDtoMapping.get(friendship.getIdFirstUser());
        UserDto secondUser = userIdToDtoMapping.get(friendship.getIdSecondUser());
        return new FriendshipDto(firstUser, secondUser, friendship.getCreatedAt());
    }
}
