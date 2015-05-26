package es.antoniolf.todolistlf;

/**
 * Created by Brolin on 20/04/2015.
 */
public class Task {
    private int id, percentage, project;
    private String name, note;

    public Task(int id, String name, int percentage, String note , int project) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.note = note;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getNote(){ return  note; }

    public int getProject() {
        return project;
    }
}

