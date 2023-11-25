package com.example.socialnetwork_gui.presentation.ConsoleUI;

import com.example.socialnetwork_gui.exception.ParseException;

import java.util.Arrays;
import java.util.List;

public class CommandParserImpl implements CommandParser {
    private final ParsedCommandFactory parsedCommandFactory = ParsedCommandFactory.getInstance();

    @Override
    public ParsedCommand parse(String request) {
        String trimmedRequest = request.trim().replaceAll("\\s+", " ");
        String[] requestItems = trimmedRequest.split(" ");
        Command command = this.getCommand(requestItems);
        List<String> args = this.getListOfArgs(requestItems);
        return parsedCommandFactory.getParsedCommand(command, args);
    }

    private Command getCommand(String[] requestItems) {
        if (requestItems.length == 0) {
            throw new ParseException("Invalid number of args in the request");
        }
        String command = requestItems[0];
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format("%s is not a valid command (type 'help')", command));
        }
    }

    private List<String> getListOfArgs(String[] requestItems) {
        if (requestItems.length == 0) {
            throw new ParseException("Invalid number of args in the request");
        }
        return Arrays.stream(requestItems).skip(1).toList();
    }
}
