package com.example.capstone2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

// If fare payment was successful, the user (commuter) will be redirected to this page.
public class commuterPaySuccessPage extends AppCompatActivity {

    ImageView success;
    AnimatedVectorDrawable successAnimation;
    Button btnContinue;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_paysuccesspage);

        success = findViewById(R.id.imageView56);

        // Code based from Frederik Schweiger (StackOverflow)
        // Gets the resource file of the animation vector from drawable.
        successAnimation = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_pay_success_animation);

        btnContinue = findViewById(R.id.payLogButton);
        btnBack = findViewById(R.id.backButton);

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

        String referenceId = intent.getString("getReferenceId");
        String transactionDate = intent.getString("getTransactionDate");
        String transactionRoute = intent.getString("getTransactionRoute");
        String fareAmount = intent.getString("getFareAmount");

        success.setImageDrawable(successAnimation);
        successAnimation.start();

        // To proceed the user to the log of their payment.
        btnContinue.setOnClickListener(v -> {

                Intent intent1 = new Intent(commuterPaySuccessPage.this, commuterPayLogPage.class);
                intent1.putExtra("getUserId", String.valueOf(accountId));
                intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
                intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
                intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
                intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

                intent1.putExtra("getReferenceId", String.valueOf(referenceId));
                intent1.putExtra("getTransactionDate", String.valueOf(transactionDate));
                intent1.putExtra("getTransactionRoute", String.valueOf(transactionRoute));
                intent1.putExtra("getFareAmount", String.valueOf(fareAmount));

                startActivity(intent1);

        });

        // To return the user to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterPaySuccessPage.this, commuterHomePage.class);
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
    }
}
