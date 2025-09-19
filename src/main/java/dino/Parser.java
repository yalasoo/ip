package dino;

import java.util.Map;

/**
 * Parses the user input command into structured components to be processed by the application.
 */
public class Parser {

    private static int parseIndex(String str, String command) throws DinoException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new DinoException("Task number must be an integer. Correct format: " + usage(command));
        }
    }

    /** Parses different user command string into its corresponding format of array of components.
     *
     * @return an array of strings containing the command type and details
     * @throws DinoException if the command is invalid or missing required information
     */
    public static Command parse(String command) throws DinoException {
        assert command != null : "Command cannot be empty";
        String[] parts = command.trim().split("\\s+", 2);
        assert parts.length >= 1 : "Command must have at least one word";
        String commandType = parts[0];
        String commandDetail = parts.length > 1 ? parts[1] : "";

        switch (commandType) {
            case "bye":
                if (parts.length > 1) {
                    throw new DinoException("Bye command does not take any arguments.");
                }
                return new ByeCommand();
            case "list":
                if (parts.length > 1) {
                    throw new DinoException("List command does not take any arguments.");
                }
                return new ListCommand();
            case "mark": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("Check again! Correct format: " + usage("mark"));
                }
                int index = parseIndex(commandDetail, "mark") - 1;
                return new MarkCommand(index);
            }
            case "unmark": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("Check again! Correct format: " + usage("unmark"));
                }
                int index = parseIndex(commandDetail, "unmark") - 1;
                return new UnmarkCommand(index);
            }
            case "todo": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("The description of a todo cannot be empty. " +
                            "Correct format: " + usage("todo"));
                }
                return new ToDoCommand(new Todo(commandDetail));
            }
            case "deadline": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("The details of a deadline cannot be empty. " +
                            "Correct format: " + usage("deadline"));
                }
                if (!commandDetail.contains("/by")) {
                    throw new DinoException("Please use the correct deadline format: " + usage("deadline"));
                }
                String[] taskDetail = commandDetail.split("/by");
                String description = taskDetail[0].trim();
                if (description.isEmpty()) {
                    throw new DinoException("The description of a deadline cannot be empty. " +
                            "Correct format: " + usage("deadline"));
                }
                String date = taskDetail[1].trim();
                if (date.isEmpty()) {
                    throw new DinoException("Deadline needs a date! " +
                            "Correct format: " + usage("deadline"));
                }
                return new ToDoCommand(new Deadline(description, date));
            }
            case "event": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("The details of a event cannot be empty. " +
                            "Correct format: " + usage("event"));
                }
                if (!commandDetail.contains("/from") || !commandDetail.contains("/to")) {
                    throw new DinoException("Please use the correct format to record down event: " + usage("event"));
                }
                String[] taskDetail = commandDetail.split("/from");
                if (taskDetail.length != 2) {
                    throw new DinoException("Missing description or time! " +
                            "Correct format: " + usage("event"));
                }
                String description = taskDetail[0].trim();
                if (description.isEmpty()) {
                    throw new DinoException("The description of an event cannot be empty. " +
                            "Correct format: " + usage("event"));
                }
                String[] duration = taskDetail[1].split("/to");
                if (duration.length != 2) {
                    throw new DinoException("Missing start or end time! " +
                            "Correct format: " + usage("event"));
                }
                String start = duration[0].trim();
                if (start.isEmpty()) {
                    throw new DinoException("Event needs a start time! " +
                            "Correct format: " + usage("event"));
                }
                String end = duration[1].trim();
                if (end.isEmpty()) {
                    throw new DinoException("Event needs an end time! " +
                            "Correct format: " + usage("event"));
                }
                return new ToDoCommand(new Event(description, start, end));
            }
            case "delete": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("Please provide a valid task number. Correct format: " + usage("delete"));
                }
                int index = parseIndex(commandDetail, "delete") - 1;
                return new DeleteCommand(index);
            }
            case "find": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("Please provide a valid keyword. Correct format: " + usage("find"));
                }
                return new FindCommand(commandDetail);
            }
            case "tag": {
                if (commandDetail.isEmpty()) {
                    throw new DinoException("Please provide tag information. Correct format: " + usage("tag"));
                }
                String[] tagParts = commandDetail.trim().split("\\s+", 2);
                if (tagParts.length < 2) {
                    throw new DinoException("Task index or tag is missing. Correct format: " + usage("tag"));
                }

                int index = parseIndex(tagParts[0], "tag") - 1;
                String tagName = tagParts[1];
                return new TagCommand(index, tagName);
            }
            default: throw new DinoException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static final Map<String, String> commandUsage = Map.of(
            "todo", "todo <description>",
            "deadline", "deadline <description> /by <YYYY-MM-DD>",
            "event", "event <description> /from <start> /to <end>",
            "mark", "mark <task_number>",
            "unmark", "unmark <task_number>",
            "delete", "delete <task_number>",
            "tag", "tag <task_index> <tag_name>",
            "find", "find <keyword>"
    );

    private static String usage(String command) {
        return commandUsage.getOrDefault(command, "");
    }
}
