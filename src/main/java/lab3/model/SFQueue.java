package lab3.model;


import java.util.Comparator;
import java.util.LinkedList;

public class SFQueue implements Queue<Task> {

    private LinkedList<Task> tasks;

    public SFQueue() {
        this.tasks = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
        tasks.sort(Comparator.comparingDouble(Task::getSolveTime));
    }

    @Override
    public Task get() {
        return tasks.pop();
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public LinkedList<Task> getTasks() {
        return tasks;
    }
}
