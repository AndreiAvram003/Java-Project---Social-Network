package com.example.socialnetwork_gui.validator;

import com.example.socialnetwork_gui.exception.EntityInvalidException;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidatorImpl implements UserValidator {
    private static final Pattern emailRegex = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private static final Pattern passwordUppercaseRegex = Pattern.compile(".*[A-Z].*");
    private static final Pattern passwordSpecialCharRegex = Pattern.compile(".*[!@#$%^&*?].*");
    private static final int USERNAME_MIN_SIZE = 3;
    private static final int USERNAME_MAX_SIZE = 20;
    private static final int PASSWORD_MIN_SIZE = 7;
    private static final int PASSWORD_MAX_SIZE = 20;
    private final UserRepository userRepository;

    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateUser(User user) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        errorMessageBuilder
                .append(getUsernameMessage(user.getUsername()))
                .append(getPasswordMessage(user.getPassword()))
                .append(getEmailMessage(user.getEmail()));
        if (!errorMessageBuilder.isEmpty()) {
            throw new EntityInvalidException(errorMessageBuilder.toString());
        }
    }

    public void validateUserDoesNotAlreadyExist(User user) {
        boolean userWithUsernameExists = userRepository.getByUsername(user.getUsername()).isPresent();
        if (userWithUsernameExists) {
            throw new EntityInvalidException(String.format("User with username %s already exists", user.getUsername()));
        }
        boolean userWithEmailExists = userRepository.getByEmail(user.getEmail()).isPresent();
        if (userWithEmailExists) {
            throw new EntityInvalidException(String.format("User with email %s already exists", user.getEmail()));
        }
    }

    private String getUsernameMessage(String username) {
        if (username.isEmpty()) {
            return "Username is mandatory";
        }
        return username.length() < USERNAME_MIN_SIZE || username.length() > USERNAME_MAX_SIZE
                ? "Username length must be between 4 and 20\n"
                : "";
    }

    private String getPasswordMessage(String password) {
        if (password.isEmpty()) {
            return "Password is mandatory";
        }

        StringBuilder errorBuilder = new StringBuilder();
        if (password.length() < PASSWORD_MIN_SIZE || password.length() > PASSWORD_MAX_SIZE) {
            errorBuilder.append("Password length must be between 7 and 20\n");
        }
        Matcher uppercaseMatcher = passwordUppercaseRegex.matcher(password);
        if (!uppercaseMatcher.find()) {
            errorBuilder.append("Password must contain at least one uppercase letter\n");
        }
        Matcher specialCharMatcher = passwordSpecialCharRegex.matcher(password);
        if (!specialCharMatcher.find()) {
            errorBuilder.append("Password must contain at least one special character\n");
        }
        return errorBuilder.toString();
    }

    private String getEmailMessage(String email) {
        if (email.isEmpty()) {
            return "Email is mandatory\n";
        }
        Matcher matcher = emailRegex.matcher(email);
        return !matcher.find()
                ? String.format("%s is not a valid email\n", email)
                : "";
    }
}
