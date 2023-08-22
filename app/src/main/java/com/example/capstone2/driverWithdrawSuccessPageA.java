package com.example.capstone2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class driverWithdrawSuccessPageA extends AppCompatActivity {

    ImageView success;
    AnimatedVectorDrawable successAnimation;
    Button btnContinue;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_withdrawsuccesspagea);

        success = findViewById(R.id.imageView13);

        // Code based from Frederik Schweiger (StackOverflow)
        // Gets the resource file of the animation vector from drawable.
        successAnimation = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_pay_success_animation);

        btnContinue = findViewById(R.id.continueButton);
        btnBack = findViewById(R.id.backButton);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        success.setImageDrawable(successAnimation);
        successAnimation.start();

        // To proceed the user to the log of their payment.
        btnContinue.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverWithdrawSuccessPageA.this, driverIntWithdrawPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });

        // To return the user to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverWithdrawSuccessPageA.this, driverHomePage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);

        });

    }
}
