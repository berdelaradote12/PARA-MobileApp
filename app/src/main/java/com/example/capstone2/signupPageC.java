package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupPageC extends AppCompatActivity {

    Button btnSeniorCitizen, btnPwd, btnStudent, btnRegular, btnBack;

    // Creating instance for Firebase Database and its reference.
    // For sending the data of Regular commuter to Firebase Realtime Database.
    private final DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("accountInfo");
    accountInfo accountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_pagec);

        // Get the user info from last intent.
        Bundle intent = getIntent().getExtras();
        String getUserFullName = intent.getString("getUserFullName");
        String getUserPhoneNumber = intent.getString("getUserPhoneNumber");
        String getUserPrefUsername = intent.getString("getUserPrefUsername");
        String getUserPrefPassword = intent.getString("getUserPrefPassword");
        String getInitialBal = intent.getString("getInitialBal");
        String getUserAccType = intent.getString("getUserAccType");
        String getUserAccVerification = intent.getString("getUserAccVerification");

        btnSeniorCitizen = findViewById(R.id.scButton);
        btnPwd = findViewById(R.id.pwdButton);
        btnStudent = findViewById(R.id.stButton);
        btnRegular = findViewById(R.id.rgButton);
        btnBack = findViewById(R.id.backButton);

        // If user is a senior citizen commuter.
        btnSeniorCitizen.setOnClickListener(v -> {

            String comType = "Senior Citizen";

            // Redirect senior citizen commuter with their previous and new data to next page.
            Intent intent1 = new Intent(signupPageC.this, signupPageD.class);
            intent1.putExtra("getUserFullName", String.valueOf(getUserFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(getUserPhoneNumber));
            intent1.putExtra("getUserPrefUsername", String.valueOf(getUserPrefUsername));
            intent1.putExtra("getUserPrefPassword", String.valueOf(getUserPrefPassword));
            intent1.putExtra("getInitialBal", String.valueOf(getInitialBal));
            intent1.putExtra("getUserAccType", String.valueOf(getUserAccType));
            intent1.putExtra("getUserComAccType", comType);
            intent1.putExtra("getUserAccVerification", getUserAccVerification);
            startActivity(intent1);
        });

        // If user is a PWD commuter.
        btnPwd.setOnClickListener(v -> {

            String comType = "PWD";

            // Redirect PWD commuter with their previous and new data to next page.
            Intent intent2 = new Intent(signupPageC.this, signupPageD.class);
            intent2.putExtra("getUserFullName", String.valueOf(getUserFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(getUserPhoneNumber));
            intent2.putExtra("getUserPrefUsername", String.valueOf(getUserPrefUsername));
            intent2.putExtra("getUserPrefPassword", String.valueOf(getUserPrefPassword));
            intent2.putExtra("getInitialBal", String.valueOf(getInitialBal));
            intent2.putExtra("getUserAccType", String.valueOf(getUserAccType));
            intent2.putExtra("getUserComAccType", comType);
            intent2.putExtra("getUserAccVerification", getUserAccVerification);
            startActivity(intent2);
        });

        // If user is a student commuter.
        btnStudent.setOnClickListener(v -> {

            String comType = "Student";

            // Redirect student commuter with their previous and new data to next page.
            Intent intent3 = new Intent(signupPageC.this, signupPageD.class);
            intent3.putExtra("getUserFullName", String.valueOf(getUserFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(getUserPhoneNumber));
            intent3.putExtra("getUserPrefUsername", String.valueOf(getUserPrefUsername));
            intent3.putExtra("getUserPrefPassword", String.valueOf(getUserPrefPassword));
            intent3.putExtra("getInitialBal", String.valueOf(getInitialBal));
            intent3.putExtra("getUserAccType", String.valueOf(getUserAccType));
            intent3.putExtra("getUserComAccType", comType);
            intent3.putExtra("getUserAccVerification", getUserAccVerification);
            startActivity(intent3);
        });

        // If user is a regular commuter.
        btnRegular.setOnClickListener(v -> {

            String comType = "Regular";
            String text28 = getString(R.string.text28);

            // Code based from Samuel Robert (StackOverflow)
            // This sets the data to the database reference which will be sent to Firebase.
            String accountId = firebaseDatabase.push().getKey();

            accountInfo = new accountInfo();
            accountInfo.setAccountFullName(getUserFullName);
            accountInfo.setAccountPhoneNumber(getUserPhoneNumber);
            accountInfo.setAccountUsername(getUserPrefUsername);
            accountInfo.setAccountPassword(getUserPrefPassword);
            accountInfo.setCommuterBalance(getInitialBal);
            accountInfo.setAccountType(getUserAccType);
            accountInfo.setCommuterType(comType);
            accountInfo.setAccountVerification(getUserAccVerification);
            accountInfo.setAccountId(accountId);

            accountInfo.setAccountBirthday("");
            accountInfo.setAccountAddress("");
            accountInfo.setAccountEmail("");

            firebaseDatabase.child(accountId).setValue(accountInfo);

            // Regular commuters no longer need to upload their valid id to validate their type.
            Intent intent14 = new Intent(signupPageC.this, loginPage.class);
            startActivity(intent14);

            // Temporary
            Toast.makeText(signupPageC.this,
                    text28, Toast.LENGTH_SHORT)
                    .show();
        });

        // To return to the main sign-up page.
        btnBack.setOnClickListener(v -> {
            Intent intent15 = new Intent(signupPageC.this, signupPageA.class);
            startActivity(intent15);
        });
    }
}
