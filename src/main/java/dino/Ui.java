package dino;

import java.util.List;
import java.util.Scanner;

/**
 * Represents the user interface for the application.
 * Returns specific output based on the user command
 */
public class Ui {
    private static final String line = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Displays the welcome message to the user. */
    public void showWelcome() {
        System.out.println(line);
        System.out.println("Hello! I'm dino.Dino.");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    /** Displays the goodbye message to the user. */
    public void showBye() {
        System.out.println(line);
        System.out.println("Bye. Hope to see you soon!");
        System.out.println(line);
    }

    /**
     * Reads the command input from user.
     *
     * @return the line of input entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks the list of tasks
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println(line);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.toString());
        }
        System.out.println(line);
    }

    /**
     * Displays a message that a task has been marked as done.
     *
     * @param task the task to be marked as done
     */
    public void showTaskMarked(Task task) {
        System.out.println(line);
        System.out.println("Nice! I've marked this task as done:\n  " + task.toString());
        System.out.println(line);
    }

    /**
     * Displays a message that a task has been marked as undone.
     *
     * @param task the task to be marked as undone
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(line);
        System.out.println("OK, I've marked this task as not done yet:\n  " + task.toString());
        System.out.println(line);
    }

    /**
     * Displays a message that a new task has been added.
     *
     * @param task the task to be added
     * @param tasks the current list of tasks
     */
    public void showTaskAdded(Task task, List<Task> tasks) {
        System.out.println(line);
        System.out.println("Got it. I've added this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Displays a message that a new task has been deleted.
     *
     * @param task the task to be deleted
     * @param tasks the current list of tasks
     */
    public void showTaskDeleted(Task task, List<Task> tasks) {
        System.out.println(line);
        System.out.println("Noted. I've removed this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Displays a message showing the error.
     *
     * @param message the error details
     */
    public void showError(String message) {
        System.out.println(line);
        System.out.println("Error! " + message);
        System.out.println(line);
    }
}
