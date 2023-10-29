package presentation;

public class ParsedCommand {
    private final Command command;
    private final Object request;

    public ParsedCommand(Command command, Object request) {
        this.command = command;
        this.request = request;
    }

    public Command getOperation() {
        return command;
    }

    public Object getRequest() {
        return request;
    }
}
