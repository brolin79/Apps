package es.antoniolf.todolistlf.database;

/**
 * Created by Brolin on 21/04/2015.
 */
public class dbContract {

    public static  final String DB_NAME = "todolistlf.db";

    public abstract class ProjectsTable{
        public static  final String TABLE_NAME = "projects_table";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PRIORITY = "priority";
        public static final String DEADLINE = "deadline";
    }

    public abstract class TasksTable{
        public static  final String TABLE_NAME = "tasks_table";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PERCENTAGE = "percentage";
        public static final String NOTE = "note";
        public static final String PROJECT = "project";
    }
}
