package com.example.socialnetwork_gui.validator;

import com.example.socialnetwork_gui.exception.EntityInvalidException;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.repository.FriendshipRepository;

public class FriendshipValidatorImpl implements FriendshipValidator {
    private final FriendshipRepository friendshipRepository;

    public FriendshipValidatorImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public void validateFriendship(Friendship friendship) {
        if (friendship.getIdFirstUser().equals(friendship.getIdSecondUser())) {
            throw new EntityInvalidException("An user cannot befriend himself");
        }
    }

    @Override
    public void validateFriendshipDoesNotAlreadyExist(Friendship friendship) {
        boolean friendshipExists =
                friendshipRepository.getByUserIds(friendship.getIdFirstUser(), friendship.getIdSecondUser())
                        .isPresent()
                        ||
                friendshipRepository.getByUserIds(friendship.getIdSecondUser(), friendship.getIdFirstUser())
                        .isPresent();
        if (friendshipExists) {
            throw new EntityInvalidException("Friendship already exists");
        }
    }
}
