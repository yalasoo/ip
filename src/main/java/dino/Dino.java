package dino;

import java.io.IOException;
import java.util.ArrayList;

/** The main class of the Dino application */
public class Dino {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    private static final String DEFAULT_FILE_PATH = "data/tasks.txt";

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

    public Dino() throws IOException {
        this(DEFAULT_FILE_PATH);
    }

    /** Starts the Dino application. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                command.executeCommand(tasks, ui, storage);
                isExit = command.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showBye();
    }

    public static void main(String[] args) throws IOException {
            new Dino(DEFAULT_FILE_PATH).run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            return Parser.parse(input).executeCommand(tasks, ui, storage);
        } catch (DukeException e ) {
            return ("Error executing command");
        }
    }

    public String showWelcome() {
        String line = "______________________________________";
        return line + "\nHello! I'm Dino.\n"
                + "What can I do for you?\n" + line;
    }
}
