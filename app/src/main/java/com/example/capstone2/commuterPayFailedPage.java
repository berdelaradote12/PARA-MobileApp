package com.example.capstone2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

// If fare payment was unsuccessful, the user (commuter) will be redirected to this page.
public class commuterPayFailedPage extends AppCompatActivity {

    ImageView failed;
    AnimatedVectorDrawable failedAnimation;
    Button btnCashIn, btnCancel;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_payfailedpage);

        failed = (ImageView) findViewById(R.id.imageView57);

        failedAnimation = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_pay_failed_animation);

        btnCashIn = (Button) (findViewById(R.id.cashInButton));
        btnCancel = (Button) (findViewById(R.id.cancelButton));
        btnBack = (ImageButton) (findViewById(R.id.backButton));

        // Code based from Frederik Schweiger (StackOverflow)
        // Gets the resource file of the animation vector from drawable.
        failed.setImageDrawable(failedAnimation);
        failedAnimation.start();

        // Get data from previous intent, also new data for when redirecting to homepage.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        // To initiate cash in function.
        btnCashIn.setOnClickListener(view -> {

            Intent intent1 = new Intent(commuterPayFailedPage.this, commuterIntCashInPage.class);
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

        // To return the user (commuter) to pay page.
        btnCancel.setOnClickListener(view -> {

            Intent intent2 = new Intent(commuterPayFailedPage.this, commuterIntPayPage.class);
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

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(view -> {

            Intent intent3 = new Intent(commuterPayFailedPage.this, commuterHomePage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent3.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent3);

        });
    }
}