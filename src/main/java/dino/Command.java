package dino;

import java.io.IOException;
import java.util.ArrayList;

public interface Command {
    String executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    boolean isExit();

}

abstract class CommandBase implements Command {
    @Override
    public boolean isExit() {
        return false;
    }
}

class ToDoCommand extends CommandBase {
    private Task task;

    public ToDoCommand(Task task) {
        this.task = task;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        taskList.addTask(task);
        try {
            storage.saveData(taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException("Error encountered when saving tasks.");
        }
        ui.showTaskAdded(task, taskList.getTaskListAsArray());
        return ui.getTaskAdded(task, taskList.getTaskListAsArray());
    }
}

class MarkCommand extends CommandBase {
    private int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = taskList.get(taskIndex);
        task.markAsDone();
        try {
            storage.saveData(taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException("Error encountered when saving tasks.");
        }
        ui.showTaskMarked(task);
        return ui.getTaskMarked(task);
    }
}

class UnmarkCommand extends CommandBase {
    private int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = taskList.get(taskIndex);
        task.markAsUndone();
        try {
            storage.saveData(taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException("Error encountered when saving tasks.");
        }
        ui.showTaskUnmarked(task);
        return ui.getTaskUnmarked(task);
    }
}

class DeleteCommand extends CommandBase {
    private int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = taskList.get(taskIndex);
        taskList.removeTask(taskIndex);
        try {
            storage.saveData(taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException("Error encountered when saving tasks.");
        }
        ui.showTaskDeleted(task, taskList.getTaskListAsArray());
        return ui.getTaskDeleted(task, taskList.getTaskListAsArray());
    }
}

class ListCommand extends CommandBase {
    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) {
        ui.showTaskList(taskList.getTaskListAsArray());
        return ui.getTaskList(taskList.getTaskListAsArray());
    }
}

class ByeCommand extends CommandBase {
    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) {
        ui.showBye();
        return ui.getByeMessage();
    }
}

class FindCommand extends CommandBase {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ArrayList<Task> searchResults = taskList.findTasks(keyword);
        ui.showFoundResults(searchResults);
        return ui.getFoundResults(searchResults);
    }
}

class TagCommand extends CommandBase {
    private int taskIndex;
    private String tag;

    public TagCommand(int taskIndex, String tag) {
        this.taskIndex = taskIndex;
        this.tag = tag;
    }

    @Override
    public String executeCommand(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = taskList.get(taskIndex);
        task.setTag(tag);

        try {
            storage.saveData(taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException("Error saving task after tagging.");
        }

        ui.showTaskTagged(task);
        return ui.taskTaggedMsg(task);
    }
}
