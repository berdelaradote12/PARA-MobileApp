package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class driverLanguagePage extends AppCompatActivity {

    ImageButton btnFilipino, btnEnglish, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_languagepage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String text128 = getString(R.string.text131);

        btnFilipino = findViewById(R.id.filipinoButton);
        btnEnglish = findViewById(R.id.englishButton);
        btnBack = findViewById(R.id.backButton);

        languageManager languageManager= new languageManager(this);

        // To change the language back to Filipino.
        btnFilipino.setOnClickListener(view -> {
            languageManager.updateResources("fil");
            //recreate();

            Intent intent1 = new Intent(driverLanguagePage.this, driverProfilePage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

            Toast.makeText(driverLanguagePage.this,
                    text128, Toast.LENGTH_SHORT)
                    .show();
        });

        // To change the language to English.
        btnEnglish.setOnClickListener(view -> {
            languageManager.updateResources("en");
            //recreate();

            Intent intent2 = new Intent(driverLanguagePage.this, driverProfilePage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);

            Toast.makeText(driverLanguagePage.this,
                    text128, Toast.LENGTH_SHORT)
                    .show();
        });

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(view -> {

            Intent intent3 = new Intent(driverLanguagePage.this, driverProfilePage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserAccVerification", accountVerification);
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent3);
        });
    }
}
