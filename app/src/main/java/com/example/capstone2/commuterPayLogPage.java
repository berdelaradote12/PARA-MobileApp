package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// This is where the user (commuter) can check the receipt of their most recent payment.
public class commuterPayLogPage extends AppCompatActivity {

    ImageButton btnBack;
    Button btnReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_paylogpage);

        // Get data from previous intent, also new data for when redirecting to commuter homepage.
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

        TextView postTransactionRoute = (TextView) findViewById(R.id.textView63);
        TextView postTransactionAmount = (TextView) findViewById(R.id.textView64);

        btnReceipt = findViewById(R.id.receiptButton);
        btnBack = findViewById(R.id.backButton);

        postTransactionRoute.setText(transactionRoute);
        postTransactionAmount.setText(String.format("Paid Php %s", fareAmount));

        // To proceed the user (commuter) to the receipt of their most recent transaction.
        btnReceipt.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterPayLogPage.this, commuterCurrPayReceiptPage.class);
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

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterPayLogPage.this, commuterHomePage.class);
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

