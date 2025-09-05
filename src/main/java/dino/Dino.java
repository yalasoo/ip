package dino;

import java.io.IOException;
import java.util.ArrayList;

/** The main class of the Dino application */
public class Dino {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

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

            int len = parsedCommand.length;
            String commandType = parsedCommand[0];
            String commandDetail = len > 1 ? parsedCommand[1] : null;
            String extraDetail = len > 2 ? parsedCommand[2] : null;
            String finalExtraDetail = len > 3 ? parsedCommand[3] : null;

            try {
                switch (commandType) {
                    case "bye":
                        ui.showBye();
                        return;

                    case "list":
                        ui.showTaskList(tasks.getAllTasks());
                        break;

                    case "mark": {
                        int index = Integer.parseInt(commandDetail) - 1;
                        tasks.get(index).markAsDone();
                        ui.showTaskMarked(tasks.get(index));
                        break;
                    }

                    case "unmark": {
                        int index = Integer.parseInt(commandDetail) - 1;
                        tasks.get(index).markAsUndone();
                        ui.showTaskUnmarked(tasks.get(index));
                        break;
                    }

                    case "todo": {
                        Task todo = new Todo(commandDetail);
                        tasks.addTask(todo);
                        ui.showTaskAdded(todo, tasks.getAllTasks());
                        break;
                    }

                    case "deadline": {
                        Task deadline = new Deadline(commandDetail, extraDetail);
                        tasks.addTask(deadline);
                        ui.showTaskAdded(deadline, tasks.getAllTasks());
                        break;
                    }

                    case "event": {
                        Task event = new Event(commandDetail, extraDetail, finalExtraDetail);
                        tasks.addTask(event);
                        ui.showTaskAdded(event, tasks.getAllTasks());
                        break;
                    }

                    case "delete": {
                        int index = Integer.parseInt(commandDetail) - 1;
                        Task deleted = tasks.removeTask(index);
                        ui.showTaskDeleted(deleted, tasks.getAllTasks());
                        break;
                    }

                    case "find": {
                        ArrayList<Task> searchResults = tasks.findTasks(commandDetail);
                        ui.showFoundResults(searchResults);
                        break;
                    }
                }
                saveTasks();
            } catch (Exception e) {
                ui.showError("Failed to save data.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
            new Dino("data/tasks.txt").run();
    }
}
