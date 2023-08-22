package com.example.capstone2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// This page displays the user (commuter) profile as well as the information they can update.
public class commuterEditProfilePageA extends AppCompatActivity {

    // Code based from Android_coder (StackOverflow)
    // Initialize calendar.
    final Calendar appCalendar = Calendar.getInstance();

    // Creating variables for custom setError;
    TextView txtError;
    ImageView showErrorA, showErrorB;

    String updatedPhoneNumber, addedBirthday, addedAddress, addedEmail, updatedUsername;
    TextView userFullName;
    EditText txtPhoneNumber, txtBirthday, txtAddress, txtEmail, txtUsername;
    Button btnProfStat, btnNewPassword, btnSaveChanges;
    ImageButton btnBack;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference retPhoneNumber, retUsername, retBirthday, retAddress, retEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_editprofilepagea);

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

        userFullName = findViewById(R.id.textView56);

        txtUsername = findViewById(R.id.editUsername);
        txtPhoneNumber = findViewById(R.id.editPhoneNumber);
        txtBirthday = findViewById(R.id.addBirthday);
        txtAddress = findViewById(R.id.addAddress);
        txtEmail = findViewById(R.id.addEmail);

        btnProfStat = findViewById(R.id.fullVerificationButton);
        btnNewPassword = findViewById(R.id.newPassButton);
        btnSaveChanges = findViewById(R.id.saveProfButton);
        btnBack = findViewById(R.id.backButton);

        // Initialize date picker when EditText : txtBirthday was tapped.
        // Code based from Android_coder (StackOverflow)
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            appCalendar.set(year,monthOfYear,dayOfMonth);
            updateLabel();
        };

        userFullName.setText(accountFullName);
        btnProfStat.setText(accountVerification);

        // Code based from Bruno Ferreira (StackOverflow)
        // To retrieve the editable data of the user (commuter) from the database and display it  on edit texts.
        retPhoneNumber = FirebaseDatabase.getInstance().getReference().child("accountInfo").child(accountId).child("accountPhoneNumber");
        retUsername = FirebaseDatabase.getInstance().getReference().child("accountInfo").child(accountId).child("accountUsername");
        retBirthday = FirebaseDatabase.getInstance().getReference().child("accountInfo").child(accountId).child("accountBirthday");
        retAddress = FirebaseDatabase.getInstance().getReference().child("accountInfo").child(accountId).child("accountAddress");
        retEmail = FirebaseDatabase.getInstance().getReference().child("accountInfo").child(accountId).child("accountEmail");

        retPhoneNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtPhoneNumber.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        retUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUsername.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        retBirthday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (retBirthday != null) {
                    txtBirthday.setText(dataSnapshot.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        retAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (retAddress != null) {
                    txtAddress.setText(dataSnapshot.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        retEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (retEmail != null) {
                    txtEmail.setText(dataSnapshot.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Code based from user2336315 (StackOverflow).
        // Resets visibility of errors when the user started typing.
        txtPhoneNumber.addTextChangedListener(new TextWatcher() {

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

        txtUsername.addTextChangedListener(new TextWatcher() {

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

        // Displays a calendar pop-up for user's (commuter) birthday to be reflected on EditText : txtBirthday.
        txtBirthday.setOnClickListener(v -> {
            new DatePickerDialog(commuterEditProfilePageA.this,date,appCalendar.get(Calendar.YEAR),appCalendar.get(Calendar.MONTH),appCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // To redirect the user (commuter) to another page where they can upload their vaccination card.
        btnProfStat.setOnClickListener(v -> {

            String text137 = getString(R.string.text137);

            // Creates database reference where user (commuter) details will be sent.
            DatabaseReference verifyUser = firebaseDatabase.child("accountInfo");

            // Code based from Peter Haddad (StackOverflow)
            verifyUser.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot currentUser : dataSnapshot.getChildren()) {

                            String currUserVerification = currentUser.child("accountVerification").getValue(String.class);

                            if (currUserVerification.equals("Fully Verified")) {

                                Toast.makeText(commuterEditProfilePageA.this,
                                        text137, Toast.LENGTH_SHORT)
                                        .show();
                            } else {

                                Intent intent1 = new Intent(commuterEditProfilePageA.this, commuterFullVerificationPage.class);
                                intent1.putExtra("getUserId", accountId);
                                intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
                                intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
                                intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
                                intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

                                startActivity(intent1);

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        });

        // To redirect the user (commuter) to another page where they can change their password.
        btnNewPassword.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterEditProfilePageA.this, commuterEditProfilePageC.class);
            intent2.putExtra("getUserId", accountId);
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent2.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);
        });

        // To save changes in user's (commuter) profile.
        btnSaveChanges.setOnClickListener(v -> {

            String text26 = getString(R.string.text26);

            updatedPhoneNumber = txtPhoneNumber.getText().toString();
            addedBirthday = txtBirthday.getText().toString();
            addedAddress = txtAddress.getText().toString();
            addedEmail = txtEmail.getText().toString();
            updatedUsername = txtUsername.getText().toString();

            if (TextUtils.isEmpty(updatedPhoneNumber)) {
                txtPhoneNumber.requestFocus();

                showErrorA.setVisibility(View.VISIBLE);
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(text26);
            } else if (TextUtils.isEmpty(updatedUsername)){
                txtUsername.requestFocus();

                showErrorB.setVisibility(View.VISIBLE);
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(text26);
            } else {
                Intent intent3 = new Intent(commuterEditProfilePageA.this, commuterEditProfilePageB.class);
                intent3.putExtra("getUserId", accountId);
                intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
                intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
                intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
                intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                intent3.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                intent3.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

                intent3.putExtra("getUpdatedPhoneNumber", String.valueOf(updatedPhoneNumber));
                intent3.putExtra("getAddedBirthday", String.valueOf(addedBirthday));
                intent3.putExtra("getAddedAddress", String.valueOf(addedAddress));
                intent3.putExtra("getAddedEmail", String.valueOf(addedEmail));
                intent3.putExtra("getUpdatedUsername", String.valueOf(updatedUsername));

                startActivity(intent3);
            }

        });

        // To return the user (commuter) to their profile page.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterEditProfilePageA.this, commuterProfilePage.class);
            intent2.putExtra("getUserId", accountId);
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

    // To format the date once it was displayed on EditText : txtBirthday.
    private void updateLabel(){
        String dateFormat="MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        txtBirthday.setText(simpleDateFormat.format(appCalendar.getTime()));
    }

}
