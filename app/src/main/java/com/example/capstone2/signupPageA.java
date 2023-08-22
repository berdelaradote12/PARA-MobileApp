package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class signupPageA extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // Code based from GeeksForGeeks
    // Pattern for strong and weak passwords.
    private static final Pattern passwordPattern =
            Pattern.compile("^" +
                    "(?=.*[A-Za-z])" +         // at least one letter
                    "(?=.*[!@#$%^&+=-_])" +    // at least 1 special character
                    "(?=\\S+$)" +              // no white spaces
                    ".{4,}" +                  // at least 4 characters
                    "$");

    private static final Pattern phoneNumberPattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // numbers only
                    "(?=\\S+$)" +           // no white spaces
                    ".{11}" +               // at least 11 characters
                    "$");

    // Creating variables for custom setError;
    TextView txtError;
    ImageView showErrorA, showErrorB, showErrorC, showErrorD, showGo;

    // Code based from GeeksforGeeks.
    // Creating variables for EditText and buttons.
    EditText txtFullName, txtPhoneNumber, txtPrefUsername, txtPrefPassword;
    ImageButton btnPasswordView, btnPasswordHide;
    Button btnCommuter, btnDriver, btnCancel;

    // Creating a variable for Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // Creating a variable for the database;
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_pagea);

        txtError = findViewById(R.id.errorMessage);
        showErrorA = findViewById(R.id.errorLogoA);
        showErrorB = findViewById(R.id.errorLogoB);
        showErrorC = findViewById(R.id.errorLogoC);
        showErrorD = findViewById(R.id.errorLogoD);
        showGo = findViewById(R.id.goLogo);

        txtFullName = findViewById(R.id.fullName);
        txtPhoneNumber = findViewById(R.id.phoneNumber);
        txtPrefUsername = findViewById(R.id.prefUsername);
        txtPrefPassword = findViewById(R.id.prefPassword);

        btnCommuter = findViewById(R.id.commuterButton);
        btnDriver = findViewById(R.id.driverButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnPasswordView = findViewById(R.id.btnPasswordView);
        btnPasswordHide = findViewById(R.id.btnPasswordHide);

        // Getting the instance of Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Getting the reference of Firebase database.
        databaseReference = firebaseDatabase.getReference("accountInfo");

        // Code based from user2336315 (StackOverflow).
        // Resets visibility of errors when the user started typing.
        txtFullName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    showErrorA.setVisibility(View.INVISIBLE);
                    txtError.setVisibility(View.GONE);
            }
        });

        txtPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    showErrorB.setVisibility(View.INVISIBLE);
                    txtError.setVisibility(View.GONE);
            }
        });

        txtPrefUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    showErrorC.setVisibility(View.INVISIBLE);
                    txtError.setVisibility(View.GONE);
            }
        });

        txtPrefPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    showErrorD.setVisibility(View.INVISIBLE);
                    txtError.setVisibility(View.GONE);
            }
        });

        // Button listener to view typed password.
        btnPasswordView.setOnClickListener(v -> {
            btnPasswordView.setVisibility(View.INVISIBLE);
            btnPasswordHide.setVisibility(View.VISIBLE);
            txtPrefPassword.setTransformationMethod(null);
        });

        // Button listener to hide typed password.
        btnPasswordHide.setOnClickListener(v -> {
            btnPasswordView.setVisibility(View.VISIBLE);
            btnPasswordHide.setVisibility(View.INVISIBLE);
            txtPrefPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        // Button listener for account type: Driver
        btnDriver.setOnClickListener(v -> addDatatoFirebaseA());

        // Button listener for account type: Commuter
        btnCommuter.setOnClickListener(v -> addDatatoFirebaseB());

        //Button listener to cancel account registration
        btnCancel.setOnClickListener(v -> {
            // To return to the previous page.
            Intent intent = new Intent(signupPageA.this, loginPage.class);
            startActivity(intent);
        });

    }

    // Initial sign-up data of the driver.
    private void addDatatoFirebaseA() {

        String text26 = getString(R.string.text26);
        String text29 = getString(R.string.text29);
        String text34 = getString(R.string.text34);
        String text36 = getString(R.string.text36);
        String text37 = getString(R.string.text37);
        String accFullName = txtFullName.getText().toString();
        String accPhoneNumber = txtPhoneNumber.getText().toString();
        String prefUsername = txtPrefUsername.getText().toString();
        String prefPassword = txtPrefPassword.getText().toString();
        double initialBal = 0.00;
        String accType = "Driver";
        String accVerification = "Semi Verified";

        if (TextUtils.isEmpty(accFullName)) {
            txtFullName.requestFocus();

            showErrorA.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (TextUtils.isEmpty(accPhoneNumber)) {
            txtPhoneNumber.requestFocus();

            showErrorB.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (TextUtils.isEmpty(prefUsername)) {
            txtPrefUsername.requestFocus();

            showErrorC.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (!phoneNumberPattern.matcher(accPhoneNumber).matches()){
            txtPhoneNumber.requestFocus();

            showErrorB.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text37);

        } else if (TextUtils.isEmpty(prefPassword)) {
            txtPrefPassword.requestFocus();

            showErrorD.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);

        } else if (!passwordPattern.matcher(prefPassword).matches()) {
            txtPrefPassword.requestFocus();

            showErrorD.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(String.format("%s \n%s",text34,text36));
        } else {

            // Codes based from Peter Haddad.
            databaseReference.orderByChild("accountUsername").equalTo(prefUsername).addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if(dataSnapshot.exists()){
                        txtPrefUsername.requestFocus();

                        showErrorC.setVisibility(View.VISIBLE);
                        txtError.setVisibility(View.VISIBLE);
                        txtError.setText(text29);
                    }
                    else {
                        // Code based from user914425 (StackOverflow) and Taimoor Sikander.
                        // To go to the next page and pass the data added by the user.
                        Intent intent = new Intent(signupPageA.this, signupPageB.class);
                        intent.putExtra("getUserFullName", (accFullName));
                        intent.putExtra("getUserPhoneNumber", (accPhoneNumber));
                        intent.putExtra("getUserPrefUsername", (prefUsername));
                        intent.putExtra("getUserPrefPassword", (prefPassword));
                        intent.putExtra("getInitialBal", decimalFormat.format(initialBal));
                        intent.putExtra("getUserAccType", (accType));
                        intent.putExtra("getUserAccVerification", (accVerification));
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    // Initial sign-up data of the commuter.
    private void addDatatoFirebaseB() {

        String text26 = getString(R.string.text26);
        String text29 = getString(R.string.text29);
        String text34 = getString(R.string.text34);
        String text36 = getString(R.string.text36);
        String text37 = getString(R.string.text37);
        String accFullName = txtFullName.getText().toString();
        String accPhoneNumber = txtPhoneNumber.getText().toString();
        String prefUsername = txtPrefUsername.getText().toString();
        String prefPassword = txtPrefPassword.getText().toString();
        double initialBal = 0.00;
        String accType = "Commuter";
        String accVerification = "Semi Verified";

        if (TextUtils.isEmpty(accFullName)) {
            txtFullName.requestFocus();

            showErrorA.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (TextUtils.isEmpty(accPhoneNumber)) {
            txtPhoneNumber.requestFocus();

            showErrorB.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (!phoneNumberPattern.matcher(accPhoneNumber).matches()){
            txtPhoneNumber.requestFocus();

            showErrorB.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text37);
        } else if (TextUtils.isEmpty(prefUsername)) {
            txtPrefUsername.requestFocus();

            showErrorC.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);
        } else if (TextUtils.isEmpty(prefPassword)) {
            txtPrefPassword.requestFocus();

            showErrorD.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(text26);

        } else if (!passwordPattern.matcher(prefPassword).matches()) {
            txtPrefPassword.requestFocus();

            showErrorD.setVisibility(View.VISIBLE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText(String.format("%s \n%s",text34,text36));
        } else {

            // Codes based from Peter Haddad.
            databaseReference.orderByChild("accountUsername").equalTo(prefUsername).addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if(dataSnapshot.exists()){
                        txtPrefUsername.requestFocus();

                        showErrorC.setVisibility(View.VISIBLE);
                        txtError.setVisibility(View.VISIBLE);
                        txtError.setText(text29);
                    }
                    else {
                        // Code based from user914425 (StackOverflow) and Taimoor Sikander.
                        // To go to the next page and pass the data added by the user.
                        Intent intent = new Intent(signupPageA.this, signupPageC.class);
                        intent.putExtra("getUserFullName", (accFullName));
                        intent.putExtra("getUserPhoneNumber", (accPhoneNumber));
                        intent.putExtra("getUserPrefUsername", (prefUsername));
                        intent.putExtra("getUserPrefPassword", (prefPassword));
                        intent.putExtra("getInitialBal",  decimalFormat.format(initialBal));
                        intent.putExtra("getUserAccType", (accType));
                        intent.putExtra("getUserAccVerification", (accVerification));
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}

