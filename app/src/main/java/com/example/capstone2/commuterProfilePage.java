package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class commuterProfilePage extends AppCompatActivity {

    ImageButton btnProfile, btnLanguage, btnHome;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_profilepage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        btnProfile = (ImageButton) (findViewById(R.id.editProfButton));
        btnLanguage = (ImageButton) (findViewById(R.id.languageButton));
        btnLogOut = (Button) (findViewById(R.id.logoutButton));
        btnHome = (ImageButton) (findViewById(R.id.homeButton));

        TextView currUserFullName = (TextView) findViewById(R.id.textView51);
        TextView currUserUsername = (TextView) findViewById(R.id.textView52);

        currUserFullName.setText(accountFullName);
        currUserUsername.setText(accountUsername);

        // To initiate editing user (commuter) profile function.
        btnProfile.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterProfilePage.this, commuterEditProfilePageA.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);
        });

        // To initiate language change function.
        btnLanguage.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterProfilePage.this, commuterLanguagePage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent2.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);
        });

        // To log-out the user (commuter).
        btnLogOut.setOnClickListener(v -> {
            Intent intent3 = new Intent(commuterProfilePage.this, loginPage.class);
            startActivity(intent3);
        });

        // To redirect the user (commuter) to homepage.
        btnHome.setOnClickListener(v -> {

            Intent intent4 = new Intent(commuterProfilePage.this, commuterHomePage.class);
            intent4.putExtra("getUserId", String.valueOf(accountId));
            intent4.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent4.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent4.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent4.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent4.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent4.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent4.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent4);
        });

    }
}
