package dino;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String line = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(line);
        System.out.println("Hello! I'm dino.Dino.");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    public void showBye() {
        System.out.println(line);
        System.out.println("Bye. Hope to see you soon!");
        System.out.println(line);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(List<Task> tasks) {
        System.out.println(line);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.toString());
        }
        System.out.println(line);
    }

    public void showTaskMarked(Task task) {
        System.out.println(line);
        System.out.println("Nice! I've marked this task as done:\n  " + task.toString());
        System.out.println(line);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(line);
        System.out.println("OK, I've marked this task as not done yet:\n  " + task.toString());
        System.out.println(line);
    }

    public void showTaskAdded(Task task, List<Task> tasks) {
        System.out.println(line);
        System.out.println("Got it. I've added this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    public void showTaskDeleted(Task task, List<Task> tasks) {
        System.out.println(line);
        System.out.println("Noted. I've removed this task:" +
                "\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    public void showError(String message) {
        System.out.println(line);
        System.out.println("Error! " + message);
        System.out.println(line);
    }

    public void showMessage(String message) {
        System.out.println(line);
        System.out.println(message);
        System.out.println(line);
    }
}
