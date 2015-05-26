package com.example.brolin.hangdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ScoresActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        SharedPreferences mypref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String Scores = mypref.getString("SCORES","NO SCORES");

        TextView tv_scores = (TextView) findViewById(R.id.userNameGame);
        tv_scores.setText(Scores);
    }


}
