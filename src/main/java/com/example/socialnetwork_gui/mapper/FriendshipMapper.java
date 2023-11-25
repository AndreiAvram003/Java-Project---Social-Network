package com.example.socialnetwork_gui.mapper;



import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;

import java.util.List;
import java.util.Map;

public interface FriendshipMapper {
    Friendship toFriendship(User firstUser, User secondUser);
    FriendshipDto toDto(UserDto firstUser, UserDto secondUser);
    List<FriendshipDto> toDto(List<Friendship> friendships, Map<Long, UserDto> userIdToDtoMapping);


    List<FriendDto> toFriendDto(List<Friendship> friendships, Long id, Map<Long, User> userIdToEntityMapping);
}
