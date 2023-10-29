package validator;

import persistance.model.User;

public interface UserValidator {
    void validateUser(User user);
    void validateUserDoesNotAlreadyExist(User user);
}
