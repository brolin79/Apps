package es.antoniolf.todolistlf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.antoniolf.todolistlf.database.dbDAO;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class TasksActivity extends ActionBarActivity {

    private List<Task> taskList;
    int p_id;
    String p_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        /*** Reviewed if is passed a msg ***/
        String var = getIntent().getStringExtra("msg");
        if (var!=null){
            Toast toast;
            if(var.equals("ok")) {
                toast = Toast.makeText(TasksActivity.this, R.string.tasksaved, Toast.LENGTH_SHORT);
            }else{
                toast = Toast.makeText(TasksActivity.this, R.string.delete_t_ok, Toast.LENGTH_SHORT);
            }
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(getResources().getColor(R.color.blue));
            toast.show();
        }

        /*** Collect the data sended ***/
        p_id = getIntent().getIntExtra("project_id",0);
        p_name = getIntent().getStringExtra("project_name");

        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(p_name);

        /*** Collect the settings from sharedpref***/
        SharedPreferences preferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        String stask = preferences.getString("Settings_t","Latest");

        /*** Get Data from DB ***/
        taskList = dbDAO.getInstance().getTasksDB(TasksActivity.this,p_id,stask);


        /*** Fill the view ***/
        ListView lv_tasks = (ListView) findViewById(R.id.lv_tasks);
        lv_tasks.setAdapter(new myAdapter());

        lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int t_id = taskList.get(position).getId();
                String t_name = taskList.get(position).getName();
                int t_percentage = taskList.get(position).getPercentage();
                String t_note = taskList.get(position).getNote();
                int t_project = taskList.get(position).getProject();
                goEditTask(view, t_id, t_name, t_percentage, t_note , t_project);
            }
        });

    }


    /*** Adapter for the listview ***/
    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = getLayoutInflater().inflate(R.layout.task, null);

            TextView tv_name = (TextView) rowView.findViewById(R.id.txt_task);
            tv_name.setText(taskList.get(position).getName());

            ImageView img_progress = (ImageView) rowView.findViewById(R.id.iv_progress);
            int num = taskList.get(position).getPercentage();

            if (num>=0 && num<24){
                img_progress.setImageResource(R.drawable.progressbar_00);
            }else if (num>24 && num<49 ){
                img_progress.setImageResource(R.drawable.progressbar_25);
            }else if (num>49 && num<74){
                img_progress.setImageResource(R.drawable.progressbar_50);
            }else if (num>74 && num<100){
                img_progress.setImageResource(R.drawable.progressbar_75);
            }else{
                img_progress.setImageResource(R.drawable.progressbar_99);
            }

            return rowView;
        }
    }

    public void goHome(View v){
        /*** Redirect to the main activity ***/
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void goPrevious(){
        /*** Redirect to the main activity ***/
        Intent myIntent = new Intent(this,MainActivity.class);
        myIntent.putExtra("msg","delete");
        startActivity(myIntent);
        finish();
    }

    public void goNewTask(View v){
        /*** Redirect to the new activity ***/
        Intent intent = new Intent(this,newTaskActivity.class);
        intent.putExtra("project_id",p_id);
        intent.putExtra("project_name",p_name);
        startActivity(intent);
        finish();
    }

    public void goEditTask(View v, int t_id, String t_name, int t_percentage, String t_note, int t_project){
        /*** Redirect to the edit task activity ***/
        Intent myIntent = new Intent(this,editTaskActivity.class);
        myIntent.putExtra("task_id",t_id);
        myIntent.putExtra("task_name",t_name);
        myIntent.putExtra("task_percentage",t_percentage);
        myIntent.putExtra("task_note",t_note);
        myIntent.putExtra("task_project",t_project);
        myIntent.putExtra("p_name",p_name);
        startActivity(myIntent);
        finish();
    }

    public void goEditProject(View v){
        /*** Redirect to the edit project activity ***/
        Intent myIntent = new Intent(this,editProjectActivity.class);
        myIntent.putExtra("project_id",p_id);
        startActivity(myIntent);
        finish();
    }

    public void deleteProject(View v){

        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_p_title)
                .setMessage(R.string.delete_p_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*** Delete in the DB ***/
                        dbDAO.getInstance().deleteProject(TasksActivity.this, p_id);
                        /*** Redirect to the previous activity ***/
                        goPrevious();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    //The font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
