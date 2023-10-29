package validator;

import persistance.model.Friendship;

public interface FriendshipValidator {
    void validateFriendship(Friendship friendship);
    void validateFriendshipDoesNotAlreadyExist(Friendship friendship);
}
