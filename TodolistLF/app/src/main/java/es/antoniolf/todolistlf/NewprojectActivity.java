package es.antoniolf.todolistlf;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class NewprojectActivity extends ActionBarActivity {

    Calendar calendar = Calendar.getInstance();
    TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);

        /*** Fill the field of date ***/
        display = (TextView) findViewById(R.id.tv_date);

        /*** Dialog for the calendar ***/
        Button dateButton = (Button) findViewById(R.id.b_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewprojectActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /*** When the user pick a date ***/
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            display.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    };


    public void  sendNewProject (View view){

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

        String txt_name = name.getText().toString();
        String txt_date = display.getText().toString();

        Project miproyecto = new Project(0,txt_name,priority,txt_date);

        /*** Save the data into DB ***/
        dbDAO.getInstance().storeProjects(NewprojectActivity.this,miproyecto);

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
