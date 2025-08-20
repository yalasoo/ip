import java.util.Scanner;

public class Dino {
    static final String line = "____________________________________________________________";
    static final int MAX_NUM = 100;
    static int curr = 0;
    static String[] tasks = new String[MAX_NUM];

    private static void printMessage(String message) {
        System.out.println(line);
        System.out.println("added: " + message);
        System.out.println(line);
    }

    private static void printList(String[] tasks) {
        System.out.println(line);
        for (int i = 0; i < curr; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
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
            } else {
                tasks[curr] = input;
                curr += 1;
                printMessage(input);
            }
        }
    }
}
