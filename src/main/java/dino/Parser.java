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
    public static String[] parse(String command) throws DukeException {
        String[] parts = command.trim().split(" ", 2);
        String commandType = parts[0];
        String commandDetail = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
            case "bye":
            case "list":
            case "mark": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Choose a valid task number to mark a task.");
                }
                return new String[] {commandType, commandDetail};
            }
            case "unmark": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Choose a valid task number to unmark a task.");
                }
                return new String[] {commandType, commandDetail};
            }
            case "todo": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("The description of a todo cannot be empty.");
                }
                return new String[]{commandType, commandDetail};
            }
            case "deadline": {
                String[] taskDetail = commandDetail.split("/by");
                String description = taskDetail[0].trim();
                if (description.isEmpty()) {
                    throw new DukeException("The description of a deadline cannot be empty.");
                }
                String date = taskDetail[1].trim();
                if (date.isEmpty()) {
                    throw new DukeException("Deadline needs a date!");
                }
                return new String[]{commandType, description, date};
            }
            case "event": {
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
                return new String[]{commandType, description, start, end};
            }
            case "delete": {
                return new String[]{commandType, commandDetail};
            }
            case "find": {
                if (commandDetail.isEmpty()) {
                    throw new DukeException("Please provide a valid keyword.");
                }
                return new String[]{commandType, commandDetail};
            }
            default: throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
