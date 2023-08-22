package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// This page displays the receipt that the commuter wanted to check from the peyment history.
public class commuterPayReceiptPage extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_payreceiptpage);

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

        String paymentHistoryReference = intent.getString("getUserPaymentReference");
        String paymentHistoryDate = intent.getString("getUserPaymentDate");
        String paymentHistoryRoute = intent.getString("getUserPaymentRoute");
        String paymentHistoryFare = intent.getString("getUserPaymentAmount");

        TextView payerName = findViewById(R.id.textView79);
        TextView paymentDate = findViewById(R.id.textView80);
        TextView travelRoute = findViewById(R.id.textView81);
        TextView paymentAmount = findViewById(R.id.textView83);
        TextView paymentReference = findViewById(R.id.textView85);

        btnBack = findViewById(R.id.backButton);

        payerName.setText(accountFullName);
        paymentDate.setText(paymentHistoryDate);
        travelRoute.setText(paymentHistoryRoute);
        paymentAmount.setText(String.format("Php %s", paymentHistoryFare));
        paymentReference.setText(paymentHistoryReference);

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterPayReceiptPage.this, commuterPayHistoryPage.class);
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
    }
}

