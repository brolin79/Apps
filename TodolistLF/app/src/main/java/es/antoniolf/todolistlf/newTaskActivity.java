package es.antoniolf.todolistlf;

import android.content.Context;
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


public class newTaskActivity extends ActionBarActivity {

    int percentage;
    int p_id;
    String p_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        p_id = getIntent().getIntExtra("project_id",0);
        p_name = getIntent().getStringExtra("project_name");

        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(p_name);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView seekBarValue = (TextView)findViewById(R.id.tv_percentage);

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
                percentage = progress;
            }
        });

    }

    public void sendNewTask (View view){

        EditText name = (EditText) findViewById(R.id.et_taskname);
        String txt_name = name.getText().toString();
        EditText note = (EditText) findViewById(R.id.et_note);
        String txt_note = note.getText().toString();

        Task mitask = new Task(0,txt_name,percentage, txt_note ,p_id);

        dbDAO.getInstance().storeTasks(newTaskActivity.this, mitask);

        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("msg","ok");
        intent.putExtra("project_id",p_id);
        intent.putExtra("project_name",p_name);
        startActivity(intent);
        finish();
    }

    public void goHome(View v){
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("project_id",p_id);
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
