package com.example.brolin.hangdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class GameOverActivity extends ActionBarActivity {

    int gPoints=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int Points = getIntent().getIntExtra("Points_var",0);
        TextView tvw_Points = (TextView) findViewById(R.id.points_number);
        tvw_Points.setText(String.valueOf(Points));

        gPoints = Points;
    }

    public void saveScore(View v){
        SharedPreferences mypref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        EditText et_username = (EditText) findViewById(R.id.userNameGame);

        String v_username = et_username.getText().toString();

        SharedPreferences.Editor editor = mypref.edit();

        String prevScores = mypref.getString("SCORES","");
        editor.putString("SCORES",v_username.toUpperCase()+" - "+gPoints+ " POINTS \n"+ prevScores);
        editor.commit();

        finish();
    }
}
