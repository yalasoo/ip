package dino;

import java.io.IOException;
import java.util.ArrayList;

/** The main class of the Dino application */
public class Dino {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    private static final String DEFAULT_FILE_PATH = "data/tasks.txt";

    /**
     * Constructs a Dino application instance with the storage file.
     *
     * @param filePath path to the storage file
     */
    public Dino(String filePath) throws IOException {
        ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadData());
        } catch (IOException e) {
            this.tasks = new TaskList(new ArrayList<>());
        }
    }

    public Dino() throws IOException {
        this(DEFAULT_FILE_PATH);
    }

    /** Saves the current list of tasks to the storage file. */
    public void saveTasks() {
        try {
            storage.saveData(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Failed to save data.");
        }
    }

    /** Starts the Dino application. */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            String[] parsedCommand;
            try {
                parsedCommand = Parser.parse(input);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
                continue;
            }
            String response = executeCommand(parsedCommand);
            if (parsedCommand[0].equals("bye")) {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
            new Dino("data/tasks.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String[] parsedCommand = Parser.parse(input);
            return executeCommand(parsedCommand);
        } catch (DukeException e ) {
            return ("Error executing command");
        }
    }

    private String executeCommand(String[] commands) {
        int len = commands.length;
        String commandType = commands[0];
        String commandDetail = len > 1 ? commands[1] : null;
        String extraDetail = len > 2 ? commands[2] : null;
        String finalExtraDetail = len > 3 ? commands[3] : null;
        String line = "______________________________________";

        try {
            switch (commandType) {
            case "bye":
                return line + "\nBye. Hope to see you soon!\n" + line;

            case "list": {
                String output = line + "\nHere are the tasks in your list:\n";
                for (int i = 0; i < tasks.getAllTasks().size(); i++) {
                    output += (i + 1) + ". " + tasks.getAllTasks().get(i) + "\n";
                }
                output += line;
                return output;
            }

            case "mark": {
                int index = Integer.parseInt(commandDetail) - 1;
                tasks.get(index).markAsDone();
                saveTasks();
                return line + "\nNice! I've marked this task as done:\n" + tasks.get(index) + "\n" + line;
            }

            case "unmark": {
                int index = Integer.parseInt(commandDetail) - 1;
                tasks.get(index).markAsUndone();
                saveTasks();
                return line + "\nOK, I've marked this task as not done yet:\n  " + tasks.get(index).toString() + "\n" + line;
            }

            case "todo": {
                Task todo = new Todo(commandDetail);
                tasks.addTask(todo);
                saveTasks();
                return line + "\nGot it. I've added this task:\n" + todo +
                        "\nNow you have " + tasks.getAllTasks().size() + " tasks in the list." + "\n" + line;
            }

            case "deadline": {
                Task deadline = new Deadline(commandDetail, extraDetail);
                tasks.addTask(deadline);
                saveTasks();
                return line + "\nGot it. I've added this task:\n" + deadline +
                        "\nNow you have " + tasks.getAllTasks().size() + " tasks in the list." + "\n" + line;
            }

            case "event": {
                Task event = new Event(commandDetail, extraDetail, finalExtraDetail);
                tasks.addTask(event);
                saveTasks();
                return line + "\nGot it. I've added this task:\n" + event +
                        "\nNow you have " + tasks.getAllTasks().size() + " tasks in the list." + "\n" + line;
            }

            case "delete": {
                int index = Integer.parseInt(commandDetail) - 1;
                Task deleted = tasks.removeTask(index);
                saveTasks();
                return line + "\nNoted. I've removed this task:\n" + deleted +
                        "\nNow you have " + tasks.getAllTasks().size() + " tasks in the list." + "\n" + line;
            }

            case "find": {
                ArrayList<Task> searchResults = tasks.findTasks(commandDetail);
                if (searchResults.isEmpty()) {
                    return line + "\nNo matching tasks found\n" + "\n" + line;
                }
                String output = line + "\nHere are the matching tasks in your list:\n";
                for (int i = 0; i < searchResults.size(); i++) {
                    output += (i + 1) + ". " + searchResults.get(i) + "\n";
                }
                output += line;
                return output;
            }
            }
        } catch (Exception e) {
            return line + "\nFailed to save data.\n" + line;
        }
        return line + "\nI'm sorry, but I don't know what that means :-(\n" + line;
    }
}
