package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

// This page is where the user (commuter) will initiate cash-in for their wallet.
public class commuterIntCashInPage extends AppCompatActivity {

    ImageButton btnVisa, btnGCash, btnPayMaya, btnSevElev, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_intcashinpage);

        btnVisa = (ImageButton) (findViewById(R.id.visaButton));
        btnGCash = (ImageButton) (findViewById(R.id.gcashButton));
        btnPayMaya = (ImageButton) (findViewById(R.id.paymayaButton));
        btnSevElev = (ImageButton) (findViewById(R.id.sevelevButton));
        btnBack = (ImageButton) (findViewById(R.id.backButton));

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

        // If the cash-in method was Mastercard-Visa:
        btnVisa.setOnClickListener(v -> {

            String cashInMethod = "Mastercard - Visa";

            Intent intent1 = new Intent(commuterIntCashInPage.this, commuterCashInPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent1.putExtra("getCashInMethod", cashInMethod);
            startActivity(intent1);
        });

        // If the cash-in method was G-Cash:
        btnGCash.setOnClickListener(v -> {

            String cashInMethod = "G-Cash";

            Intent intent2 = new Intent(commuterIntCashInPage.this, commuterCashInPage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent2.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent2.putExtra("getCashInMethod", cashInMethod);
            startActivity(intent2);
        });

        // If the cash-in method was PayMaya:
        btnPayMaya.setOnClickListener(v -> {

            String cashInMethod = "PayMaya";

            Intent intent3 = new Intent(commuterIntCashInPage.this, commuterCashInPage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent3.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent3.putExtra("getCashInMethod", cashInMethod);
            startActivity(intent3);
        });

        // If the cash-in method was Seven-Eleven:
        btnSevElev.setOnClickListener(v -> {

            String cashInMethod = "SevenEleven";

            Intent intent4 = new Intent(commuterIntCashInPage.this, commuterCashInPage.class);
            intent4.putExtra("getUserId", String.valueOf(accountId));
            intent4.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent4.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent4.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent4.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent4.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent4.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent4.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent4.putExtra("getCashInMethod", cashInMethod);
            startActivity(intent4);
        });

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(v -> {
            Intent intent5 = new Intent(commuterIntCashInPage.this, commuterHomePage.class);
            intent5.putExtra("getUserId", String.valueOf(accountId));
            intent5.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent5.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent5.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent5.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent5.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent5.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent5.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent5);
        });

    }
}
