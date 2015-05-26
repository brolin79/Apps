package es.antoniolf.todolistlf;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import es.antoniolf.todolistlf.database.dbDAO;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class MainActivity extends ActionBarActivity {

    private List<Project> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** Reviewed if is passed a msg ***/
        String var = getIntent().getStringExtra("msg");
        if (var!=null){
            Toast toast;
            if(var.equals("ok")) {
                toast = Toast.makeText(MainActivity.this, R.string.projectsaved, Toast.LENGTH_SHORT);
            }else{
                toast = Toast.makeText(MainActivity.this, R.string.delete_p_ok, Toast.LENGTH_SHORT);
            }
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(getResources().getColor(R.color.blue));
            toast.show();
        }

        SharedPreferences preferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        String sproj = preferences.getString("Settings_p","Latest");

        /*** Fill the projects from the DB ***/
        projectList = dbDAO.getInstance().getProjectsDB(MainActivity.this,sproj);

        /*** Fill the view ***/
        ListView lv_projects = (ListView) findViewById(R.id.lv_projects);
        lv_projects.setAdapter(new myAdapter());

        lv_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p_id = projectList.get(position).getId();
                String p_name = projectList.get(position).getName();
                goTask(view, p_id, p_name);
            }
        });

        AdView adview = (AdView) findViewById(R.id.adView);
        adview.loadAd(new AdRequest.Builder().build());
    }

    /*** Adapter for the list view ***/
    private class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return projectList.size();
        }

        @Override
        public Object getItem(int position) {
            return projectList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = getLayoutInflater().inflate(R.layout.project, null);

            TextView tv_name = (TextView) rowView.findViewById(R.id.txt_project);
            tv_name.setText(projectList.get(position).getName());

            TextView tv_date = (TextView) rowView.findViewById(R.id.txt_date);
            tv_date.setText(projectList.get(position).getDeadline());

            ImageView img_priority = (ImageView) rowView.findViewById(R.id.iv_priority);
            int priority = projectList.get(position).getPriority();

            if (priority==1){
                img_priority.setImageResource(R.drawable.p_low);
            }else if (priority==2){
                img_priority.setImageResource(R.drawable.p_normal);
            }else{
                img_priority.setImageResource(R.drawable.p_high);
            }

            return rowView;
        }
    }

    public void goNewproject(View v){
        /*** Redirect to the new activity ***/
        Intent intent = new Intent(this,NewprojectActivity.class);
        startActivity(intent);
        finish();
    }

    public void goSettings(View v){
        /*** Redirect to the settings activity **/
        Intent myIntent = new Intent(this,SettingsActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void goTask(View v, int p_id, String p_name){
        /*** Redirect to the task activity ***/
        Intent myIntent = new Intent(this,TasksActivity.class);
        myIntent.putExtra("project_id",p_id);
        myIntent.putExtra("project_name",p_name);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //The font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
