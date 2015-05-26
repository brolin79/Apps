package es.antoniolf.todolistlf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        String sproj = preferences.getString("Settings_p","Latest");
        String stask = preferences.getString("Settings_t","Latest");

        RadioButton p_latest = (RadioButton) findViewById(R.id.rb_pLatest);
        RadioButton p_name = (RadioButton) findViewById(R.id.rb_pName);
        RadioButton p_priority = (RadioButton) findViewById(R.id.rb_pPriority);
        if (sproj.equals("Latest")){
            p_latest.setChecked(true);
        }else if (sproj.equals("Name")){
            p_name.setChecked(true);
        }else{
            p_priority.setChecked(true);
        }

        RadioButton t_latest = (RadioButton) findViewById(R.id.rb_tLatest);
        RadioButton t_name = (RadioButton) findViewById(R.id.rb_tName);
        RadioButton t_percentage = (RadioButton) findViewById(R.id.rb_tPercentage);
        if (stask.equals("Latest")){
            t_latest.setChecked(true);
        }else if (stask.equals("Name")){
            t_name.setChecked(true);
        }else{
            t_percentage.setChecked(true);
        }
    }

    public void sendSettings (View v){
        RadioButton p_latest = (RadioButton) findViewById(R.id.rb_pLatest);
        RadioButton p_name = (RadioButton) findViewById(R.id.rb_pName);
        RadioButton p_priority = (RadioButton) findViewById(R.id.rb_pPriority);
        String p_settings = "Latest";
        if (p_latest.isChecked()){
            p_settings = "Latest";
        } else if (p_name.isChecked()) {
            p_settings = "Name";
        } else {
            p_settings = "Priority";
        }

        RadioButton t_latest = (RadioButton) findViewById(R.id.rb_tLatest);
        RadioButton t_name = (RadioButton) findViewById(R.id.rb_tName);
        RadioButton t_percentage = (RadioButton) findViewById(R.id.rb_tPercentage);
        String t_settings = "Latest";
        if (t_latest.isChecked()){
            t_settings = "Latest";
        } else if (t_name.isChecked()) {
            t_settings = "Name";
        } else {
            t_settings = "Percentage";
        }

        SharedPreferences preferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Settings_p",p_settings);
        editor.putString("Settings_t",t_settings);
        editor.commit();

        Toast toast = Toast.makeText(SettingsActivity.this, R.string.s_saved, Toast.LENGTH_SHORT);
        TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
        tv.setTextColor(getResources().getColor(R.color.blue));
        toast.show();
    }


    public void goHome(View v){
        /*** Redirect to the main activity ***/
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
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
