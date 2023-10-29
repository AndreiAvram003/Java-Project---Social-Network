package facade;

import persistance.model.dtos.FriendshipDto;
import request.FriendshipRequest;
import request.UserDeleteRequest;
import persistance.model.dtos.UserDto;
import request.UserCreateRequest;

import java.util.List;

public interface UserFacade {
    UserDto addUser(UserCreateRequest userRequest);
    List<UserDto> getAllUsers();
    void deleteUser(UserDeleteRequest userDeleteRequest);
    FriendshipDto addFriendship(FriendshipRequest friendshipRequest);
    List<FriendshipDto> getAllFriendships();
    void deleteFriendship(FriendshipRequest friendshipRequest);
    List<List<UserDto>> getFriendshipConnectedComponents();
    List<UserDto> getLongestComponent();
}
