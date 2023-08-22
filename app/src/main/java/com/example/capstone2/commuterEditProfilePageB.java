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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// This page will confirm the changes made on the user's (commuter) profile.
public class commuterEditProfilePageB extends AppCompatActivity {

    // Creating variables for custom setError;
    TextView txtError;
    ImageView showError;

    EditText txtPassword;
    ImageButton btnPasswordView, btnPasswordHide, btnBack;
    Button btnSave, btnCancel;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_editprofilepageb);

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

        txtError = findViewById(R.id.errorMessage);
        showError = findViewById(R.id.errorLogo);

        txtPassword = findViewById(R.id.userPassword);

        btnSave = findViewById(R.id.withdrawAmountButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);
        btnPasswordView = findViewById(R.id.btnPasswordView);
        btnPasswordHide = findViewById(R.id.btnPasswordHide);

        // Code based from user2336315 (StackOverflow).
        // Resets visibility of errors when the user started typing.
        txtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    showError.setVisibility(View.INVISIBLE);
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
        btnPasswordHide.setOnClickListener(v -> {
            btnPasswordView.setVisibility(View.VISIBLE);
            btnPasswordHide.setVisibility(View.INVISIBLE);
            txtPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        btnSave.setOnClickListener(v -> { updateUserInfo(); });

        // To cancel the changes in user (commuter) profile.
        btnCancel.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterEditProfilePageB.this, commuterEditProfilePageA.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });

        // To return the user (commuter) to their profile info page.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterEditProfilePageB.this, commuterProfilePage.class);
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

    // This will update the changes from the user's (commuter) profile to database.
    private void updateUserInfo() {

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String updatedPhoneNumber = intent.getString("getUpdatedPhoneNumber");
        String addedBirthday = intent.getString("getAddedBirthday");
        String addedAddress = intent.getString("getAddedAddress");
        String addedEmail = intent.getString("getAddedEmail");
        String updatedUsername = intent.getString("getUpdatedUsername");

        String userPassword = txtPassword.getText().toString();

        String text26 = getString(R.string.text26);
        String text234 = getString(R.string.text234);
        String text236 = getString(R.string.text236);

        // Creates database reference where user (commuter) details will be sent.
        DatabaseReference verifyUser = firebaseDatabase.child("accountInfo");

        // Code based from Peter Haddad (StackOverflow)
        verifyUser.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot currentUser : dataSnapshot.getChildren()) {

                        String currUserPassword = currentUser.child("accountPassword").getValue(String.class);

                        if (!(userPassword.equals(""))) {
                            if (currUserPassword.equals(userPassword)) {

                                // Send updated/non-updated user (commuter) details to the existing user data.
                                verifyUser.child(accountId).child("accountPhoneNumber").setValue(updatedPhoneNumber);
                                verifyUser.child(accountId).child("accountBirthday").setValue(addedBirthday);
                                verifyUser.child(accountId).child("accountAddress").setValue(addedAddress);
                                verifyUser.child(accountId).child("accountEmail").setValue(addedEmail);
                                verifyUser.child(accountId).child("accountUsername").setValue(updatedUsername);

                                // Redirect the user (commuter) to edit profile page with updated profile.
                                Intent intent = new Intent(commuterEditProfilePageB.this, commuterEditProfilePageA.class);
                                intent.putExtra("getUserId", accountId);
                                intent.putExtra("getUserUsername", String.valueOf(updatedUsername));
                                intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                intent.putExtra("getUserPhoneNumber", String.valueOf(updatedPhoneNumber));
                                intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                                intent.putExtra("getUserAccVerification", accountVerification);
                                intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                startActivity(intent);

                                // Temporary
                                Toast.makeText(commuterEditProfilePageB.this,
                                        text236, Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                txtPassword.requestFocus();

                                showError.setVisibility(View.VISIBLE);
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText(text234);
                            }
                        } else {
                            txtPassword.requestFocus();

                            showError.setVisibility(View.VISIBLE);
                            txtError.setVisibility(View.VISIBLE);
                            txtError.setText(text26);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}