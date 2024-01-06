package com.example.socialnetwork_gui.facade;



import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendDto;
import com.example.socialnetwork_gui.persistance.model.dtos.FriendshipDto;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFacade  {
    UserDto addUser(UserCreateRequest userRequest);

    UserDto updateUser(UserUpdateRequest userUpdateRequest);
    List<UserDto> getAllUsers();


    MessageDto addMessage(MessageRequest messageRequest);


    Optional<Message> deleteMessage(MessageDeleteRequest messageRequest);

    UserDto deleteUser(UserDeleteRequest userDeleteRequest);
    FriendshipDto addFriendship(FriendshipRequest friendshipRequest);

    List<FriendshipDto> getAllFriendships();

    List<MessageDto> getMessagesBetweenUsers(UUID from_id,UUID to_id);
    void changeStatus(FriendshipRequest friendshipRequest);
    Optional<Friendship> deleteFriendship(FriendshipRequest friendshipRequest);
    List<List<UserDto>> getFriendshipConnectedComponents();
    List<UserDto> getLongestComponent();
    List<FriendDto> getFriendsForUser(UUID userUid, Long luna);

    List<UserDto> getFriendsForUser(UUID userUid);

    List<UserDto> getFriendsOnPage(int pageNumber,int pageSize,UUID userUid);

    List<UserDto> getNonFriendsOnPage(int pageNumber,int pageSize,UUID userUid);

    List<UserDto> getUsersThatAreNotFriends(UUID userUid);

    List<UserDto> getRequests(UUID uid);

    UserDto getUserbyUsernameAndPassword(String username,String password);
}
