package com.example.socialnetwork_gui.validator;

import com.example.socialnetwork_gui.persistance.model.Friendship;

public interface FriendshipValidator {
    void validateFriendship(Friendship friendship);
    void validateFriendshipDoesNotAlreadyExist(Friendship friendship);
}
