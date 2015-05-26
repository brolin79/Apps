package es.antoniolf.todolistlf.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.antoniolf.todolistlf.Project;
import es.antoniolf.todolistlf.Task;

/**
 * Created by Brolin on 21/04/2015.
 */
public class dbDAO {

    private static dbDAO gInstance = null;

    public static  dbDAO getInstance(){
        if (gInstance == null){
            gInstance = new dbDAO();
        }
        return gInstance;
    }

    public boolean storeProjects(Context context, Project project){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(dbContract.ProjectsTable.NAME, project.getName());
            cv.put(dbContract.ProjectsTable.PRIORITY, project.getPriority());
            cv.put(dbContract.ProjectsTable.DEADLINE, project.getDeadline());

            db.insert(dbContract.ProjectsTable.TABLE_NAME, null, cv);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public boolean storeTasks(Context context, Task task){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(dbContract.TasksTable.NAME, task.getName());
            cv.put(dbContract.TasksTable.PERCENTAGE, task.getPercentage());
            cv.put(dbContract.TasksTable.NOTE,task.getNote());
            cv.put(dbContract.TasksTable.PROJECT,task.getProject());

            db.insert(dbContract.TasksTable.TABLE_NAME, null, cv);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public boolean updateTask(Context context, Task task){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(dbContract.TasksTable.NAME, task.getName());
            cv.put(dbContract.TasksTable.PERCENTAGE, task.getPercentage());
            cv.put(dbContract.TasksTable.NOTE,task.getNote());
            cv.put(dbContract.TasksTable.PROJECT,task.getProject());

            db.update(dbContract.TasksTable.TABLE_NAME, cv, dbContract.TasksTable.ID + "=" + task.getId(), null);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public boolean updateProject(Context context, Project project){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(dbContract.ProjectsTable.NAME, project.getName());
            cv.put(dbContract.ProjectsTable.PRIORITY, project.getPriority());
            cv.put(dbContract.ProjectsTable.DEADLINE, project.getDeadline());

            db.update(dbContract.ProjectsTable.TABLE_NAME, cv, dbContract.ProjectsTable.ID + "=" + project.getId(), null);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public boolean deleteProject(Context context, int project){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            db.delete(dbContract.TasksTable.TABLE_NAME, dbContract.TasksTable.PROJECT + "=" + project, null);
            db.delete(dbContract.ProjectsTable.TABLE_NAME, dbContract.TasksTable.ID + "=" + project, null);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public boolean deleteTask(Context context, int task){

        try {
            SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

            db.beginTransaction();

            db.delete(dbContract.TasksTable.TABLE_NAME, dbContract.TasksTable.ID + "=" + task, null);

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (Exception e){
            return false;
        }

        return  true;
    }

    public List<Project> getProjectsDB(Context context, String orderby){

        String myorder;

        if (orderby.equals("Latest")){
            myorder = dbContract.ProjectsTable.ID + " DESC";
        }else if (orderby.equals("Name")){
            myorder = dbContract.ProjectsTable.NAME + " ASC";
        }else{
            myorder = dbContract.ProjectsTable.PRIORITY + " DESC";
        }

        SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

        String queryString =
                "SELECT * FROM "+ dbContract.ProjectsTable.TABLE_NAME +
                "  ORDER BY "+ myorder +" ";

        Cursor cursor = db.rawQuery(queryString, null);
        List<Project> projectList = new ArrayList<>();

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(dbContract.ProjectsTable.ID));
            String name = cursor.getString(cursor.getColumnIndex(dbContract.ProjectsTable.NAME));
            int priority = cursor.getInt(cursor.getColumnIndex(dbContract.ProjectsTable.PRIORITY));
            String deadline = cursor.getString(cursor.getColumnIndex(dbContract.ProjectsTable.DEADLINE));

            Project project = new Project(id,name,priority,deadline);
            projectList.add(project);
        }

        cursor.close();
        db.close();

        return projectList;
    }

    public Project getProject(Context context, int idproject){

        SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

        String[] whereArgs = new String[] { Integer.toString(idproject) };
        String queryString =
                "SELECT * FROM "+ dbContract.ProjectsTable.TABLE_NAME + " " +
                "WHERE "+ dbContract.ProjectsTable.ID +" = ? LIMIT 1";

        Cursor cursor = db.rawQuery(queryString, whereArgs);

        int id = idproject;
        int priority=2;
        String name = null,deadline=null;

        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(dbContract.ProjectsTable.ID));
            name = cursor.getString(cursor.getColumnIndex(dbContract.ProjectsTable.NAME));
            priority = cursor.getInt(cursor.getColumnIndex(dbContract.ProjectsTable.PRIORITY));
            deadline = cursor.getString(cursor.getColumnIndex(dbContract.ProjectsTable.DEADLINE));
        }

        Project project = new Project(id,name,priority,deadline);

        cursor.close();
        db.close();

        return project;
    }

    public List<Task> getTasksDB(Context context, int project_id, String orderby){

        String myorder;

        if (orderby.equals("Latest")){
            myorder = dbContract.TasksTable.ID + " DESC";
        }else if (orderby.equals("Name")){
            myorder = dbContract.TasksTable.NAME + " ASC";
        }else{
            myorder = dbContract.TasksTable.PERCENTAGE + " ASC";
        }

        SQLiteDatabase db = new dbOpenHelper(context).getWritableDatabase();

        String[] whereArgs = new String[] { Integer.toString(project_id) };
        String queryString =
                "SELECT * FROM "+ dbContract.TasksTable.TABLE_NAME + " " +
                "WHERE "+ dbContract.TasksTable.PROJECT +" = ? ORDER BY "+ myorder +" ";

        Cursor cursor = db.rawQuery(queryString, whereArgs);

        List<Task> taskList = new ArrayList<>();

        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbContract.TasksTable.ID));
            String name = cursor.getString(cursor.getColumnIndex(dbContract.TasksTable.NAME));
            int percentage = cursor.getInt(cursor.getColumnIndex(dbContract.TasksTable.PERCENTAGE));
            String note = cursor.getString(cursor.getColumnIndex(dbContract.TasksTable.NOTE));
            int project = cursor.getInt(cursor.getColumnIndex(dbContract.TasksTable.PROJECT));

            Task task = new Task(id,name,percentage, note,project);
            taskList.add(task);
        }

        cursor.close();
        db.close();

        return taskList;
    }

}
