package com.example.capstone2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class languageSetter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageManager languageManager = new languageManager(this);
        languageManager.updateResources(languageManager.getLang());

    }
}
