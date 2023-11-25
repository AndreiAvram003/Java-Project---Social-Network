package com.example.socialnetwork_gui.presentation.ConsoleUI;

public enum Command {
    ADD_USER(3, "[username: str] [password: str] [email: str]"),
    PRINT_USERS(0, "NO ARGS"),
    ADD_FRIENDSHIP(2, "[first_user_uid: UUID] [second_user_uid: UUID]"),
    DELETE_USER(1, "[user_uid: UUID]"),
    DELETE_FRIENDSHIP(2, "[first_user_uid: UUID] [second_user_uid: UUID]"),
    PRINT_FRIENDSHIPS(0, "NO ARGS"),
    PRINT_COMPONENTS(0, "NO ARGS"),
    HELP(0, "NO ARGS"),

    PRINT_FRIENDS(2,"[user_uid: UUID],[luna: luna]"),

    PRINT_LONGEST_COMPONENT(0,"NO ARGS"),
    EXIT(0, "NO ARGS");

    final int numberOfArgs;
    final String argsDescription;

    Command(int numberOfArgs, String argsDescription) {
        this.numberOfArgs = numberOfArgs;
        this.argsDescription = argsDescription;
    }

    public int getNumberOfArgs() {
        return numberOfArgs;
    }

    public String getArgsDescription() {
        return argsDescription;
    }
}
