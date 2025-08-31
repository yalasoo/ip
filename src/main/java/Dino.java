import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dino {
    static final String line = "____________________________________________________________";
    static ArrayList<Task> tasks = new ArrayList<>();
    private Storage storage;

    public Dino(String filePath) throws IOException {
        this.storage = new Storage(filePath);
        this.tasks = storage.loadData();
    }

    private static void printList() {
        System.out.println(line);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.toString());
        }
        System.out.println(line);
    }

    private static void markTask(int i) {
        tasks.get(i).markAsDone();
        System.out.println(line);
        System.out.println("Nice! I've marked this task as done:\n  " + tasks.get(i).toString());
        System.out.println(line);
    }

    private static void unmarkTask(int i) {
        tasks.get(i).markAsUndone();
        System.out.println(line);
        System.out.println("OK, I've marked this task as not done yet:\n  " + tasks.get(i).toString());
        System.out.println(line);
    }

    private static void addTask(Task task) {
        tasks.add(task);
        System.out.println(line);
        System.out.println("Got it. I've added this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    private static void deleteTask(Task task) {
        tasks.remove(task);
        System.out.println(line);
        System.out.println("Noted. I've removed this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    public void saveTasks() throws IOException {
        storage.saveData(tasks);
    }

    public static void main(String[] args) throws IOException {

        Dino dino = new Dino("data/dino.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println("Hello! I'm Dino.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println("Bye. Hope to see you soon!");
                    System.out.println(line);
                    break;
                } else if (input.equals("list")) {
                    printList();
                } else if (input.startsWith("mark")) {
                    String index = input.substring(5).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("The task does not exist.");
                    }
                    markTask(i);
                } else if (input.startsWith("unmark")) {
                    String index = input.substring(7).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("The task does not exist.");
                    }
                    unmarkTask(i);
                } else if (input.startsWith("todo")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    Task todo = new Todo(description);
                    addTask(todo);
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
                    addTask(deadline);
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
                    addTask(event);
                } else if (input.startsWith("delete")) {
                    String index = input.substring(7).trim();
                    int i = Integer.parseInt(index) - 1;
                    if (i < 0 || i >= tasks.size()) {
                        throw new DukeException("Choose a valid task.");
                    }
                    deleteTask(tasks.get(i));
                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
                dino.saveTasks();
            } catch (DukeException e) {
                System.out.println(line);
                System.out.println("Check again! " + e.getMessage());
                System.out.println(line);
            } catch (Exception e) {
                System.out.println(line);
                System.out.println("Error! " + e.getMessage());
                System.out.println(line);
            }
        }
    }
}
