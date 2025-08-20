import java.util.Scanner;

public class Dino {
    public static void main(String[] args) {
        /*String logo = " ____   _           \n"

                + "|  _ \\ |_| _____   ___ \n"
                + "| | | || ||  _  \\ /   \\\n"
                + "| |_| || || | | ||     |\n"
                + "|____/ |_||_| |_| \\___/\n";

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);*/

        Scanner scanner = new Scanner(System.in);

        String line = "____________________________________________________________";
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
            }

            System.out.println(line);
            System.out.println(input);
            System.out.println(line);

        }
    }
}
