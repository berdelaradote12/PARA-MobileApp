package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

// This page is where both the user and driver will log-in to get to their separate interfaces.
public class loginPage extends AppCompatActivity {

    // Creating variables for custom setError;
    TextView txtError;
    ImageView showErrorA, showErrorB;

    // Creating variables for EditText and buttons.
    EditText txtUsername, txtPassword;
    Button btnContinue, btnRegister;
    ImageButton btnPasswordView, btnPasswordHide;

    // Creating a variable for the reference of our Firebase Database.
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CapstoneTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        txtError = findViewById(R.id.errorMessage);
        showErrorA = findViewById(R.id.errorLogoA);
        showErrorB = findViewById(R.id.errorLogoB);

        txtUsername = findViewById(R.id.editUsername);
        txtPassword = findViewById(R.id.userPassword);

        btnPasswordView = findViewById(R.id.btnPasswordView);
        btnPasswordHide = findViewById(R.id.btnPasswordHide);
        btnContinue = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registerButton);

        // Getting the instance of our Firebase database.
        // Getting the already existing reference from our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        // Code based from user2336315 (StackOverflow).
        // Resets visibility of errors when the user started typing.
        txtUsername.addTextChangedListener(new TextWatcher() {

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

        txtPassword.addTextChangedListener(new TextWatcher() {

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

        // Button listener to view typed password.
        btnPasswordView.setOnClickListener(v -> {
            btnPasswordView.setVisibility(View.INVISIBLE);
            btnPasswordHide.setVisibility(View.VISIBLE);
            txtPassword.setTransformationMethod(null);
        });

        // Button listener to hide typed password.
        // "v ->" is a lambda.
        btnPasswordHide.setOnClickListener(v -> {
            btnPasswordView.setVisibility(View.VISIBLE);
            btnPasswordHide.setVisibility(View.INVISIBLE);
            txtPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        // Button listener to log-in the user.
        btnContinue.setOnClickListener(v -> verifyLogin());

        // Button listener to sign-up the user.
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(loginPage.this, signupPageA.class);
            startActivity(intent);
        });
    }

    private void verifyLogin() {

        String accUsername = txtUsername.getText().toString();
        String accPassword = txtPassword.getText().toString();
        String accDriver = "Driver";
        String accCommuter = "Commuter";
        String text30 = getString(R.string.text30);
        String text31 = getString(R.string.text31);

        // Code based from Shihab365 (StackOverflow).
        Query realtimeQuery = firebaseDatabase.child("accountInfo").orderByChild("accountUsername").equalTo(accUsername);

        // Code based from GeeksForGeeks.
        // Code based from Peter Haddad.
        // This method is called to validate the user logging in.
        realtimeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Code based from Shihab365 (StackOverflow).
                    for (DataSnapshot currentUser : dataSnapshot.getChildren()) {
                        accountInfo currentUserValue = currentUser.getValue(accountInfo.class);
                        if (Objects.requireNonNull(currentUserValue).accountPassword.equals(accPassword)) {
                            if (Objects.requireNonNull(currentUserValue).accountType.equals(accDriver)) {

                                // Get the rest of the current user's existing data.
                                String accountId = currentUser.child("accountId").getValue(String.class);
                                String accountUsername = currentUser.child("accountUsername").getValue(String.class);
                                String accountPassword = currentUser.child("accountPassword").getValue(String.class);
                                String accountFullName = currentUser.child("accountFullName").getValue(String.class);
                                String accountPhoneNumber = currentUser.child("accountPhoneNumber").getValue(String.class);
                                String accountVerification = currentUser.child("accountVerification").getValue(String.class);
                                String accountBalance = currentUser.child("driverBalance").getValue(String.class);

                                // Redirect intent to driver's homepage
                                Intent intent = new Intent(loginPage.this, driverHomePage.class);
                                intent.putExtra("getUserId", String.valueOf(accountId));
                                intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                startActivity(intent);

                            } else if (Objects.requireNonNull(currentUserValue).accountType.equals(accCommuter)) {

                                // Get the rest of the current user's existing data.
                                String accountId = currentUser.child("accountId").getValue(String.class);
                                String accountUsername = currentUser.child("accountUsername").getValue(String.class);
                                String accountPassword = currentUser.child("accountPassword").getValue(String.class);
                                String accountFullName = currentUser.child("accountFullName").getValue(String.class);
                                String accountPhoneNumber = currentUser.child("accountPhoneNumber").getValue(String.class);
                                String accountCommuterType = currentUser.child("commuterType").getValue(String.class);
                                String accountVerification = currentUser.child("accountVerification").getValue(String.class);
                                String accountBalance = currentUser.child("commuterBalance").getValue(String.class);

                                // Redirect intent to commuter's homepage
                                Intent intent = new Intent(loginPage.this, commuterHomePage.class);
                                intent.putExtra("getUserId", String.valueOf(accountId));
                                intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                                intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                startActivity(intent);

                            }
                        } else {
                            txtUsername.requestFocus();
                            txtPassword.requestFocus();

                            showErrorA.setVisibility(View.VISIBLE);
                            showErrorB.setVisibility(View.VISIBLE);
                            txtError.setVisibility(View.VISIBLE);
                            txtError.setText(text30);
                        }
                    }
                } else {
                    txtUsername.requestFocus();
                    txtPassword.requestFocus();

                    showErrorA.setVisibility(View.VISIBLE);
                    showErrorB.setVisibility(View.VISIBLE);
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText(text31);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Method for an error is received.
                //Toast.makeText(loginPage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}