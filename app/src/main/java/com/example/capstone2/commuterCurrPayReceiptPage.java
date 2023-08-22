package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// This will display the receipt of the user's (commuter) most recent fare payment.
public class commuterCurrPayReceiptPage extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_currpayreceiptpage);

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

        TextView payerName = (TextView) findViewById(R.id.textView66);
        TextView paymentDate = (TextView) findViewById(R.id.textView67);
        TextView travelRoute = (TextView) findViewById(R.id.textView68);
        TextView paymentAmount = (TextView) findViewById(R.id.textView70);
        TextView paymentReference = (TextView) findViewById(R.id.textView72);

        btnBack = (ImageButton) (findViewById(R.id.backButton));

        payerName.setText(accountFullName);
        paymentDate.setText(transactionDate);
        travelRoute.setText(transactionRoute);
        paymentAmount.setText(String.format("Php %s", fareAmount));
        paymentReference.setText(referenceId);

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterCurrPayReceiptPage.this, commuterHomePage.class);
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
