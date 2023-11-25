package com.example.socialnetwork_gui.facade;



import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.FriendshipRequest;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.request.UserDeleteRequest;
import com.example.socialnetwork_gui.request.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserFacade {
    UserDto addUser(UserCreateRequest userRequest);

    UserDto updateUser(UserUpdateRequest userUpdateRequest);
    List<UserDto> getAllUsers();
    UserDto deleteUser(UserDeleteRequest userDeleteRequest);
    FriendshipDto addFriendship(FriendshipRequest friendshipRequest);
    List<FriendshipDto> getAllFriendships();
    void deleteFriendship(FriendshipRequest friendshipRequest);
    List<List<UserDto>> getFriendshipConnectedComponents();
    List<UserDto> getLongestComponent();
    List<FriendDto> getFriendsForUser(UUID userUid, Long luna);
}
