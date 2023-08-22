package com.example.capstone2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

// Code based from Cambo Tutotrial
// (YT Link: https://www.youtube.com/watch?v=cJeiGSzPyq0)
// This will help set the language of the app after button click.
public class languageManager  {

    private Context context;

    private SharedPreferences sharedReferences;

    public languageManager (Context langContext) {

        context = langContext;
        sharedReferences = context.getSharedPreferences("LANG", Context.MODE_PRIVATE);

    }

    // To initiate language change.
    void updateResources(String code) {

        Locale locale = new Locale(code, "PH");
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLang(code);

    }

    public String getLang(){
        return sharedReferences.getString("lang","en");
    }

    // To reflect the language change in all activities.
    public void setLang (String code){

        SharedPreferences.Editor editor = sharedReferences.edit();
        editor.putString("lang", code);
        editor.commit();
    }

}