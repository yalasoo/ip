import java.io.IOException;
import java.util.ArrayList;

public class Dino {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Storage storage;
    private Ui ui;

    public Dino(String filePath) throws IOException {
        ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.loadData();
    }

    public void saveTasks() throws IOException {
        storage.saveData(tasks);
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
                        ui.showTaskList(tasks);
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
                        tasks.add(todo);
                        ui.showTaskAdded(todo, tasks);
                        break;
                    }

                    case "deadline": {
                        Task deadline = new Deadline(commandDetail, extraDetail);
                        tasks.add(deadline);
                        ui.showTaskAdded(deadline, tasks);
                        break;
                    }

                    case "event": {
                        Task event = new Event(commandDetail, extraDetail, finalExtraDetail);
                        tasks.add(event);
                        ui.showTaskAdded(event, tasks);
                        break;
                    }

                    case "delete": {
                        int index = Integer.parseInt(commandDetail) - 1;
                        Task deleted = tasks.remove(index);
                        ui.showTaskDeleted(deleted, tasks);
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
