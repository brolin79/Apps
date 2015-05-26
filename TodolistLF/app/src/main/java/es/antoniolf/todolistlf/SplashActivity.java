package es.antoniolf.todolistlf;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;



public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
                finish();
            }
        },2000);
    }

    public void  nextActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
