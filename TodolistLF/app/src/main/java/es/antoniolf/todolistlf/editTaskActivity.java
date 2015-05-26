package es.antoniolf.todolistlf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import es.antoniolf.todolistlf.database.dbDAO;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class editTaskActivity extends ActionBarActivity {

    int t_id, t_percentage,t_project;
    String t_name,t_note,p_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        /*** Collect the data sended ***/
        t_id = getIntent().getIntExtra("task_id",0);
        t_name = getIntent().getStringExtra("task_name");
        t_percentage = getIntent().getIntExtra("task_percentage", 0);
        t_note = getIntent().getStringExtra("task_note");
        t_project = getIntent().getIntExtra("task_project",0);
        p_name = getIntent().getStringExtra("p_name");

        /*** We write on the view ***/
        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(t_name);

        EditText txt_name = (EditText) findViewById(R.id.et_taskname);
        txt_name.setText(t_name);

        EditText txt_note = (EditText) findViewById(R.id.et_note);
        txt_note.setText(t_note);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(t_percentage);
        final TextView seekBarValue = (TextView)findViewById(R.id.tv_percentage);

        seekBarValue.setText((t_percentage+" %"));


        /*** Method of the seekbar ***/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                seekBarValue.setText(progress+" %");
                t_percentage = progress;
            }
        });
    }

    public void sendEditTask (View view){

        /*** Collect what we will save in the db ***/
        EditText name = (EditText) findViewById(R.id.et_taskname);
        String txt_title = name.getText().toString();

        EditText note = (EditText) findViewById(R.id.et_note);
        String txt_note = note.getText().toString();

        Task mitask = new Task(t_id,txt_title,t_percentage,txt_note,t_project);

        /*** Save in the DB ***/
        dbDAO.getInstance().updateTask(editTaskActivity.this, mitask);

        /*** Redirect to the previous activity ***/
        goPrevious();
    }

    public void deleteTask(View v){

        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_t_title)
                .setMessage(R.string.delete_t_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*** Delete in the DB ***/
                        dbDAO.getInstance().deleteTask(editTaskActivity.this, t_id);
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

    public void goPrevious (){
        /*** Redirect to the previous activity ***/
        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("msg","delete");
        intent.putExtra("project_id",t_project);
        intent.putExtra("project_name",p_name);
        startActivity(intent);
        finish();
    }

    public void goHome(View v){
        /*** Redirect to the main activity ***/
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("project_id",t_project);
        intent.putExtra("project_name",p_name);
        startActivity(intent);
        finish();
    }

    //The font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
