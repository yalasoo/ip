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

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.equals("bye")) {
                    ui.showBye();
                    break;
                } else if (input.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (input.startsWith("mark")) {
                    String index = input.substring(5).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("The task does not exist.");
                    }
                    tasks.get(i).markAsDone();
                    ui.showTaskMarked(tasks.get(i));
                } else if (input.startsWith("unmark")) {
                    String index = input.substring(7).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("The task does not exist.");
                    }
                    tasks.get(i).markAsUndone();
                    ui.showTaskUnmarked(tasks.get(i));
                } else if (input.startsWith("todo")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    Task todo = new Todo(description);
                    tasks.add(todo);
                    ui.showTaskAdded(todo, tasks);
                } else if (input.startsWith("deadline")) {
                    String[] detail = input.substring(9).split("/by");
                    if (detail.length != 2) {
                        throw new DukeException("Use the correct format!");
                    }
                    String description = detail[0].trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a deadline cannot be empty.");
                    }
                    String date = detail[1].trim();
                    if (date.isEmpty()) {
                        throw new DukeException("Deadline needs a date!");
                    }
                    Task deadline = new Deadline(description, date);
                    tasks.add(deadline);
                    ui.showTaskAdded(deadline, tasks);
                } else if (input.startsWith("event")) {
                    String[] detail = input.substring(6).split("/from");
                    if (detail.length != 2) {
                        throw new DukeException("Missing description or time!");
                    }
                    String description = detail[0].trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of an event cannot be empty.");
                    }
                    String[] duration = detail[1].split("/to");
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
                    Task event = new Event(description, start, end);
                    tasks.add(event);
                    ui.showTaskAdded(event, tasks);
                } else if (input.startsWith("delete")) {
                    String index = input.substring(7).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("Choose a valid task.");
                    }
                    Task deleted = tasks.remove(i);
                    ui.showTaskDeleted(deleted, tasks);
                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
                saveTasks();
            } catch (DukeException e) {
                ui.showError("Check again! " + e.getMessage());
            } catch (IOException e) {
                ui.showError("Failed to save data:" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Dino("data/tasks.txt").run();
        } catch (IOException e) {
            Ui ui = new Ui();
            ui.showError("Failed to load tasks: " + e.getMessage());
        }
    }
}
