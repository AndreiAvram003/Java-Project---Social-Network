package com.example.socialnetwork_gui.mapper;



import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.UserCreateRequest;
import com.example.socialnetwork_gui.request.UserUpdateRequest;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User toUser(UserCreateRequest userRequest);

    User toUser(User userToUpdate,UserUpdateRequest userUpdateRequest);
    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);
    Map<Long, UserDto> toDtoMap(List<User> users);
    List<List<UserDto>> userComponentsToDtoComponents(List<List<User>> userComponents);
    List<UserDto> userComponentToDtoComponent(List<User> userComponent);
}
