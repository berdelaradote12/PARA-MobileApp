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

import java.util.regex.Pattern;

public class commuterEditProfilePageC extends AppCompatActivity {

    // Code based from GeeksForGeeks
    // Pattern for strong and weak passwords.
    private static final Pattern passwordPattern =
            Pattern.compile("^" +
                    "(?=.*[A-Za-z])" +        // at least one letter
                    "(?=.*[!@#$%^&+=-_])" +    // at least 1 special character
                    "(?=\\S+$)" +             // no white spaces
                    ".{4,}" +                 // at least 4 characters
                    "$");

    TextView txtError;
    ImageView showErrorA, showErrorB, showErrorC;

    EditText txtCurrPassword, txtNewPassword, txtAuthNewPassword;
    ImageButton btnPasswordViewA, btnPasswordHideA, btnPasswordViewB, btnPasswordHideB, btnPasswordViewC, btnPasswordHideC, btnBack;
    Button btnSaveNewPass, btnCancel;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_editprofilepagec);

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
        showErrorA = findViewById(R.id.errorLogoA);
        showErrorB = findViewById(R.id.errorLogoB);
        showErrorC = findViewById(R.id.errorLogoC);

        txtCurrPassword = (EditText) findViewById(R.id.currPassword);
        txtNewPassword = (EditText) findViewById(R.id.newPassword);
        txtAuthNewPassword = (EditText) findViewById(R.id.confNewPassword);

        btnSaveNewPass = (Button) findViewById(R.id.saveNewPassButton);
        btnCancel = (Button) findViewById(R.id.cancelButton);
        btnBack = (ImageButton) findViewById(R.id.backButton);
        btnPasswordViewA = (ImageButton) findViewById(R.id.btnPasswordView);
        btnPasswordHideA = (ImageButton) findViewById(R.id.btnPasswordHide);
        btnPasswordViewB = (ImageButton) findViewById(R.id.btnPasswordViewB);
        btnPasswordHideB = (ImageButton) findViewById(R.id.btnPasswordHideB);
        btnPasswordViewC = (ImageButton) findViewById(R.id.btnPasswordViewC);
        btnPasswordHideC = (ImageButton) findViewById(R.id.btnPasswordHideC);

        // Code based from user2336315 (StackOverflow).
        // Resets visibility of errors when the user started typing.
        txtCurrPassword.addTextChangedListener(new TextWatcher() {

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

        txtNewPassword.addTextChangedListener(new TextWatcher() {

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

        txtAuthNewPassword.addTextChangedListener(new TextWatcher() {

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

        // Button listener to view typed current password.
        btnPasswordViewA.setOnClickListener(v -> {
            btnPasswordViewA.setVisibility(View.INVISIBLE);
            btnPasswordHideA.setVisibility(View.VISIBLE);
            txtCurrPassword.setTransformationMethod(null);
        });

        // Button listener to hide typed current password.
        btnPasswordHideA.setOnClickListener(v -> {
            btnPasswordViewA.setVisibility(View.VISIBLE);
            btnPasswordHideA.setVisibility(View.INVISIBLE);
            txtCurrPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        // Button listener to view typed new password.
        btnPasswordViewB.setOnClickListener(v -> {
            btnPasswordViewB.setVisibility(View.INVISIBLE);
            btnPasswordHideB.setVisibility(View.VISIBLE);
            txtNewPassword.setTransformationMethod(null);
        });

        // Button listener to hide typed new password.
        btnPasswordHideB.setOnClickListener(v -> {
            btnPasswordViewB.setVisibility(View.VISIBLE);
            btnPasswordHideB.setVisibility(View.INVISIBLE);
            txtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        // Button listener to view re-typed new password.
        btnPasswordViewC.setOnClickListener(v -> {
            btnPasswordViewC.setVisibility(View.INVISIBLE);
            btnPasswordHideC.setVisibility(View.VISIBLE);
            txtAuthNewPassword.setTransformationMethod(null);
        });

        // Button listener to hide re-typed new password.
        btnPasswordHideC.setOnClickListener(v -> {
            btnPasswordViewC.setVisibility(View.VISIBLE);
            btnPasswordHideC.setVisibility(View.INVISIBLE);
            txtAuthNewPassword.setTransformationMethod(new PasswordTransformationMethod());
        });

        btnSaveNewPass.setOnClickListener(v -> { updateUserPassword(); });

        // To cancel the changes in user profile.
        btnCancel.setOnClickListener(v -> {

            Intent intent1 = new Intent(commuterEditProfilePageC.this, commuterEditProfilePageA.class);
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

        // To return the user (commuter) to their profile info page.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterEditProfilePageC.this, commuterProfilePage.class);
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

    private void updateUserPassword() {

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String userPassword = txtCurrPassword.getText().toString();
        String newUserPassword = txtNewPassword.getText().toString();
        String authNewUserPassword = txtAuthNewPassword.getText().toString();

        String text34 = getString(R.string.text34);
        String text36 = getString(R.string.text36);
        String text234 = getString(R.string.text234);
        String text235 = getString(R.string.text235);
        String text237 = getString(R.string.text237);

        // Creates database reference where cash in details will be sent.
        DatabaseReference verifyUser = firebaseDatabase.child("accountInfo");

        // Code based from Peter Haddad (StackOverflow)
        verifyUser.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot currentUser : dataSnapshot.getChildren()) {

                        String currUserPassword = currentUser.child("accountPassword").getValue(String.class);

                        if (currUserPassword.equals(userPassword)) {

                            if (!passwordPattern.matcher(newUserPassword).matches()) {
                                txtNewPassword.requestFocus();

                                showErrorB.setVisibility(View.VISIBLE);
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText(String.format("%s \n%s",text34,text36));
                            } else if (newUserPassword.equals(authNewUserPassword)) {
                                // Send updated/non-updated user details to the existing user data.
                                verifyUser.child(accountId).child("accountPassword").setValue(newUserPassword);

                                // Redirect the user to edit profile page with updated password.
                                Intent intent = new Intent(commuterEditProfilePageC.this, commuterEditProfilePageA.class);
                                intent.putExtra("getUserId", accountId);
                                intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                intent.putExtra("getUserPassword", newUserPassword);
                                intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                                intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                startActivity(intent);

                                // Temporary
                                Toast.makeText(commuterEditProfilePageC.this,
                                        text237, Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                txtNewPassword.requestFocus();
                                txtAuthNewPassword.requestFocus();

                                showErrorB.setVisibility(View.VISIBLE);
                                showErrorC.setVisibility(View.VISIBLE);
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText(text235);
                            }
                        } else {
                            txtCurrPassword.requestFocus();

                            showErrorA.setVisibility(View.VISIBLE);
                            txtError.setVisibility(View.VISIBLE);
                            txtError.setText(text234);
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