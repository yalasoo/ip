import java.util.Scanner;

public class Dino {
    static final String line = "____________________________________________________________";
    static final int MAX_NUM = 100;
    static int curr = 0;
    static Task[] tasks = new Task[MAX_NUM];

    private static void printMessage(String message) {
        System.out.println(line);
        System.out.println("added: " + message);
        System.out.println(line);
    }

    private static void printList(Task[] tasks) {
        System.out.println(line);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < curr; i++) {
            Task task = tasks[i];
            System.out.println((i + 1) + ". " + task.toString());
        }
        System.out.println(line);
    }

    private static void markTask(int i) {
        tasks[i].markAsDone();
        System.out.println(line);
        System.out.println("Nice! I've marked this task as done:\n  " + tasks[i].toString());
        System.out.println(line);
    }

    private static void unmarkTask(int i) {
        tasks[i].markAsUndone();
        System.out.println(line);
        System.out.println("OK, I've marked this task as not done yet:\n  " + tasks[i].toString());
        System.out.println(line);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println("Hello! I'm Dino. \nWhat can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                printList(tasks);
            } else if (input.startsWith("mark")) {
                String index = input.substring(5);
                int i = Integer.parseInt(index) - 1;
                markTask(i);
            } else if (input.startsWith("unmark")) {
                String index = input.substring(7);
                int i = Integer.parseInt(index) - 1;
                unmarkTask(i);
            } else {
                tasks[curr] = new Task(input);
                curr += 1;
                printMessage(input);
            }
        }
    }
}
