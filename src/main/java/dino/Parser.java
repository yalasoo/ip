package dino;

/**
 * Parses the user input command into structured components to be processed by the application.
 */
public class Parser {

    /** Parses different user command string into its corresponding format of array of components.
     *
     * @return an array of strings containing the command type and details
     * @throws DukeException if the command is invalid or missing required information
     */
    public static Command parse(String command) throws DukeException {
        assert command != null : "Command cannot be empty";
        String[] parts = command.trim().split(" ", 3);
        assert parts.length >= 1 : "Command must have at least one word";
        String commandType = parts[0];
        String commandDetail = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
            case "bye":
                return new ByeCommand();
            case "list":
                return new ListCommand();
            case "mark": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Choose a valid task number to mark a task.");
                }
                return new MarkCommand(Integer.parseInt(commandDetail));
            }
            case "unmark": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Choose a valid task number to unmark a task.");
                }
                return new UnmarkCommand(Integer.parseInt(commandDetail));
            }
            case "todo": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("The description of a todo cannot be empty.");
                }
                return new ToDoCommand(new Todo(commandDetail));
            }
            case "deadline": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("The details of a deadline cannot be empty.");
                }
                if (!commandDetail.contains("/by")) {
                    throw new DukeException("Please use the correct deadline format.");
                }
                String[] taskDetail = commandDetail.split("/by");
                String description = taskDetail[0].trim();
                if (description.isEmpty()) {
                    throw new DukeException("The description of a deadline cannot be empty.");
                }
                String date = taskDetail[1].trim();
                if (date.isEmpty()) {
                    throw new DukeException("Deadline needs a date!");
                }
                return new ToDoCommand(new Deadline(description, date));
            }
            case "event": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("The details of a event cannot be empty.");
                }
                if (!commandDetail.contains("/from") || !commandDetail.contains("/to")) {
                    throw new DukeException("Please use the correct format to record down event.");
                }
                String[] taskDetail = commandDetail.split("/from");
                if (taskDetail.length != 2) {
                    throw new DukeException("Missing description or time!");
                }
                String description = taskDetail[0].trim();
                if (description.isEmpty()) {
                    throw new DukeException("The description of an event cannot be empty.");
                }
                String[] duration = taskDetail[1].split("/to");
                if (duration.length != 2) {
                    throw new DukeException("Missing start or end time!");
                }
                String start = duration[0].trim();
                if (start.isEmpty()) {
                    throw new DukeException("Event needs a start time!");
                }
                String end = duration[1].trim();
                if (end.isEmpty()) {
                    throw new DukeException("Event needs an end time!");
                }
                return new ToDoCommand(new Event(description, start, end));
            }
            case "delete": {
                return new DeleteCommand(Integer.parseInt(commandDetail));
            }
            case "find": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Please provide a valid keyword.");
                }
                return new FindCommand(commandDetail);
            }
            case "tag": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Please provide tag information.");
                }
                if (parts.length < 3) {
                    throw new DukeException("Task index or tag is missing.");
                }

                int index = Integer.parseInt(parts[1]);
                String tagName = parts[2];
                return new TagCommand(index, tagName);
            }
            default: throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
