package es.antoniolf.todolistlf;

/**
 * Created by Brolin on 20/04/2015.
 */
public class Project {

    private int id, priority;
    private String name, deadline;

    public Project(int id, String name, int priority, String deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }
}
