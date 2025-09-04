import java.io.IOException;

public class Dino {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public Dino(String filePath) throws IOException {
        ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.loadData());
    }

    public void saveTasks() throws IOException {
        storage.saveData(tasks.getAllTasks());
    }

    public void run() throws DukeException {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            String[] parsedCommand = Parser.parse(input);
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
                }
                saveTasks();
            } catch (IOException e) {
                ui.showError("Failed to save data:" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Dino("data/tasks.txt").run();
        } catch (DukeException e) {
            Ui ui = new Ui();
            ui.showError("Check again!" + e.getMessage());
        } catch (IOException e) {
            Ui ui = new Ui();
            ui.showError("Failed to load tasks: " + e.getMessage());
        }
    }
}
