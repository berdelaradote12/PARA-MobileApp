package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class driverProfilePage extends AppCompatActivity {

    ImageButton btnProfile, btnWdMethod, btnLanguage, btnHome;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profilepage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        btnProfile = (ImageButton) (findViewById(R.id.editProfButton));
        btnWdMethod = (ImageButton) (findViewById(R.id.wdMethodButton));
        btnLanguage = (ImageButton) (findViewById(R.id.languageButton));
        btnLogOut = (Button) (findViewById(R.id.logoutButton));
        btnHome = (ImageButton) (findViewById(R.id.homeButton));

        TextView currUserFullName = findViewById(R.id.textView33);
        TextView currUserUsername = findViewById(R.id.textView34);

        currUserFullName.setText(accountFullName);
        currUserUsername.setText(accountUsername);

        // To initiate editing user (driver) profile function.
        btnProfile.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverProfilePage.this, driverEditProfilePageA.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);
        });

        // To initiate updating user (driver) withdraw method function.
        btnWdMethod.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverProfilePage.this, driverWithdrawMethodPage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);
        });

        // To initiate language change function.
        btnLanguage.setOnClickListener(v -> {

            Intent inten3 = new Intent(driverProfilePage.this, driverLanguagePage.class);
            inten3.putExtra("getUserId", String.valueOf(accountId));
            inten3.putExtra("getUserUsername", String.valueOf(accountUsername));
            inten3.putExtra("getUserPassword", String.valueOf(accountPassword));
            inten3.putExtra("getUserFullName", String.valueOf(accountFullName));
            inten3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            inten3.putExtra("getUserAccVerification", accountVerification);
            inten3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(inten3);
        });

        // To log-out the user (driver).
        btnLogOut.setOnClickListener(v -> {
            Intent intent4 = new Intent(driverProfilePage.this, loginPage.class);
            startActivity(intent4);
        });

        // To redirect the user (driver) to homepage.
        btnHome.setOnClickListener(v -> {

            Intent intent5 = new Intent(driverProfilePage.this, driverHomePage.class);
            intent5.putExtra("getUserId", String.valueOf(accountId));
            intent5.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent5.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent5.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent5.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent5.putExtra("getUserAccVerification", accountVerification);
            intent5.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent5);
        });

    }
}

