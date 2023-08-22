package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// This page displays the user's (commuter) homepage interface.
public class commuterHomePage extends AppCompatActivity {

    ImageButton btnPay, btnPayHistory, btnUser;
    Button btnCashIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_homepage);

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

        TextView commuterBalance = (TextView)findViewById(R.id.textView47);

        btnCashIn = (Button)(findViewById(R.id.cashInButton));
        btnPay = (ImageButton) (findViewById(R.id.paymentButton));
        btnPayHistory = (ImageButton) (findViewById(R.id.payHistoryButton));
        btnUser = (ImageButton) (findViewById(R.id.userButton));

        // Change string format of account balance to have "Php."
        commuterBalance.setText(String.format("Php %s", accountBalance));

        // To initiate cash-in function.
        btnCashIn.setOnClickListener(v -> {
            Intent intent1 = new Intent(commuterHomePage.this, commuterIntCashInPage.class);
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

        // To initiate fare payment function.
        btnPay.setOnClickListener(v -> {
            Intent intent2 = new Intent(commuterHomePage.this, commuterIntPayPage.class);
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

        // To let user (commuter) check their payment history.
        btnPayHistory.setOnClickListener(v -> {
            Intent intent3 = new Intent(commuterHomePage.this, commuterPayHistoryPage.class);
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

        // To redirect the user (commuter) to their profile page.
        btnUser.setOnClickListener(v -> {
            Intent intent4 = new Intent(commuterHomePage.this, commuterProfilePage.class);
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
