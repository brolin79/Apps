package es.antoniolf.todolistlf;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Brolin on 15/04/2015.
 */
public class TodolistlfApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Exo2.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
