package validator;

import exception.EntityInvalidException;
import persistance.model.Friendship;
import persistance.repository.FriendshipRepository;

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
