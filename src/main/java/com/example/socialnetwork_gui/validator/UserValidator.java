package com.example.socialnetwork_gui.validator;

import com.example.socialnetwork_gui.persistance.model.User;

public interface UserValidator {
    void validateUser(User user);
    void validateUserDoesNotAlreadyExist(User user);
}
