package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class driverWithdrawMethodPage extends AppCompatActivity {

    MaterialButton btnEditEWDetail, btnEditCPDetail;
    ImageButton btnBack;
    ImageView ewalletMethodView, cpickupMethodView;
    TextView txtPhoneNumber;

    int[] imageArrayA = { R.drawable.g_cash_method, R.drawable.paymaya_method };
    int[] imageArrayB = { R.drawable.palawan_method, R.drawable.cebuana_method };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_withdrawmethodpage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        btnEditEWDetail = findViewById(R.id.editEWDetailButton);
        btnEditCPDetail = findViewById(R.id.editCPDetailButton);
        btnBack = findViewById(R.id.backButton);

        ewalletMethodView = findViewById(R.id.imageView31);
        cpickupMethodView = findViewById(R.id.imageView33);

        txtPhoneNumber = findViewById(R.id.textView41);

        txtPhoneNumber.setText(accountPhoneNumber);

        // Code based from InnocentKiller (StackOverflow)
        // Switches e-wallet methods' logos every 0.7 seconds.
        Handler handlerA = new Handler();
        Runnable runnableA = new Runnable() {
            int i = 0;

            public void run() {
                ewalletMethodView.setImageResource(imageArrayA[i]);
                i++;
                if (i > imageArrayA.length - 1) {
                    i = 0;
                }
                handlerA.postDelayed(this, 1000);
            }
        };
        handlerA.postDelayed(runnableA, 200);

        // Switches cash pick up methods' logos every 0.7 seconds.
        Handler handlerB = new Handler();
        Runnable runnableB = new Runnable() {
            int i = 0;

            public void run() {
                cpickupMethodView.setImageResource(imageArrayB[i]);
                i++;
                if (i > imageArrayB.length - 1) {
                    i = 0;
                }
                handlerB.postDelayed(this, 1000);
            }
        };
        handlerB.postDelayed(runnableB, 200);

        // To return the user (driver) to homepage.
        btnBack.setOnClickListener(view -> {

            Intent intent1 = new Intent(driverWithdrawMethodPage.this, driverProfilePage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);
        });
    }
}
