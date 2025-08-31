public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toStoreFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}

