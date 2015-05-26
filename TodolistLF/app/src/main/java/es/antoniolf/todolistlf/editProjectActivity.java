package es.antoniolf.todolistlf;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import es.antoniolf.todolistlf.database.dbDAO;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class editProjectActivity extends ActionBarActivity {
    int p_id;
    String p_name;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        /*** Collect the id sended ***/
        p_id = getIntent().getIntExtra("project_id", 0);

        /*** Get the data from db ***/
        Project editproject = dbDAO.getInstance().getProject(editProjectActivity.this, p_id);

        /*** Fill the view ***/
        TextView title = (TextView) findViewById(R.id.txt_title);
        title.setText(editproject.getName());

        EditText et_tittle = (EditText) findViewById(R.id.et_projname);
        et_tittle.setText(editproject.getName());

        TextView date = (TextView) findViewById(R.id.tv_date);
        date.setText(editproject.getDeadline());

        int priority = editproject.getPriority();

        RadioButton r_low = (RadioButton) findViewById(R.id.rb_low);
        RadioButton r_normal = (RadioButton) findViewById(R.id.rb_normal);
        RadioButton r_high = (RadioButton) findViewById(R.id.rb_high);

        if (priority==1) {
            r_low.setChecked(true);
        } else if (priority==3) {
            r_high.setChecked(true);
        } else {
            r_normal.setChecked(true);
        }

        /*** Dialog for the calendar ***/
        Button dateButton = (Button) findViewById(R.id.b_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(editProjectActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /*** When the user pick a date ***/
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView display = (TextView) findViewById(R.id.tv_date);
            display.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    };

    public void  sendEditProject (View view){

        /*** Collect the data sended ***/
        EditText name = (EditText) findViewById(R.id.et_projname);

        RadioButton r_low = (RadioButton) findViewById(R.id.rb_low);
        RadioButton r_high = (RadioButton) findViewById(R.id.rb_high);
        int priority = 2;
        if (r_low.isChecked()){
            priority = 1;
        } else if (r_high.isChecked()) {
            priority = 3;
        } else {
            priority = 2;
        }

        TextView display = (TextView) findViewById(R.id.tv_date);

        String txt_name = name.getText().toString();
        String txt_date = display.getText().toString();

        Project miproyecto = new Project(p_id,txt_name,priority,txt_date);

        /*** Save the data into DB ***/
        dbDAO.getInstance().updateProject(editProjectActivity.this, miproyecto);

        /*** Back to the previous activity ***/
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("msg","ok");
        startActivity(intent);
        finish();
    }

    /*** Redirect to the main activity ***/
    public void goHome(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
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
