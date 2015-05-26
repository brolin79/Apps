package es.antoniolf.todolistlf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brolin on 21/04/2015.
 */
public class dbOpenHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private  static final String TEXT_TYPE = " TEXT";
    private  static final String INTEGER_TYPE = " INTEGER";
    private  static final String COMMA = ", ";
    private  static final String PKA = " PRIMARY KEY AUTOINCREMENT";

    private static final String CREATE_PROJECTS_TABLE = "CREATE TABLE "
            + dbContract.ProjectsTable.TABLE_NAME + " ("
            + dbContract.ProjectsTable.ID + INTEGER_TYPE + PKA + COMMA
            + dbContract.ProjectsTable.NAME + TEXT_TYPE + COMMA
            + dbContract.ProjectsTable.PRIORITY + INTEGER_TYPE + COMMA
            + dbContract.ProjectsTable.DEADLINE + TEXT_TYPE + " )";

    private static  final String CREATE_TASKS_TABLE = "CREATE TABLE "
            + dbContract.TasksTable.TABLE_NAME + " ("
            + dbContract.TasksTable.ID + INTEGER_TYPE + PKA + COMMA
            + dbContract.TasksTable.NAME + TEXT_TYPE + COMMA
            + dbContract.TasksTable.PERCENTAGE + INTEGER_TYPE + COMMA
            + dbContract.TasksTable.NOTE + TEXT_TYPE + COMMA
            + dbContract.TasksTable.PROJECT + INTEGER_TYPE + " )";

    private static final String DROP_PROJECTS_TABLE = "DROP TABLE IF EXISTS "+ dbContract.ProjectsTable.TABLE_NAME;
    private static final String DROP_TASKS_TABLE = "DROP TABLE IF EXISTS "+ dbContract.TasksTable.TABLE_NAME;

    public dbOpenHelper(Context context) {
        super(context, dbContract.DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROJECTS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PROJECTS_TABLE);
        db.execSQL(DROP_TASKS_TABLE);
        onCreate(db);
    }
}
