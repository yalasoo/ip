package dino;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    //new tasklist
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    //existing tasklist
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new ArrayIndexOutOfBoundsException("Invalid task index.");
        }
        return tasks.remove(index);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new ArrayIndexOutOfBoundsException("Invalid task index.");
        }
        return tasks.get(index);
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /** Returns a list of tasks containing the required keyword.
     *
     * @param keyword the word to be searched
     * @return task list with all tasks containing the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> searchResults = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                searchResults.add(task);
            }
        }
        return searchResults;
    }
}
