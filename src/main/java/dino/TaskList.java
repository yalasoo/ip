package dino;

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
}
