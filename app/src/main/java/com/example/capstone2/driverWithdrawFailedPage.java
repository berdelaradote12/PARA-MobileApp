package com.example.capstone2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class driverWithdrawFailedPage extends AppCompatActivity {

    ImageView failed;
    AnimatedVectorDrawable failedAnimation;
    Button btnRetry, btnCancel;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_withdrawfailedpage);

        failed = (ImageView) findViewById(R.id.imageView15);

        failedAnimation = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_pay_failed_animation);

        btnRetry = (Button) (findViewById(R.id.retryButton));
        btnCancel = (Button) (findViewById(R.id.cancelButton));
        btnBack = (ImageButton) (findViewById(R.id.backButton));

        // Code based from Frederik Schweiger (StackOverflow)
        // Gets the resource file of the animation vector from drawable.
        failed.setImageDrawable(failedAnimation);
        failedAnimation.start();

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        // To retry withdrawal attempt by the user (driver).
        btnRetry.setOnClickListener(view -> {

            Intent intent1 = new Intent(driverWithdrawFailedPage.this, driverIntWithdrawPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });

        // To return the user (driver) to homepage.
        btnCancel.setOnClickListener(view -> {

            Intent intent2 = new Intent(driverWithdrawFailedPage.this, driverHomePage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);

        });

        // To return the user (driver) to homepage.
        btnBack.setOnClickListener(view -> {

            Intent intent3 = new Intent(driverWithdrawFailedPage.this, commuterHomePage.class);
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

