package com.example.capstone2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class driverConfirmWithdrawPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

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
        setContentView(R.layout.driver_confirmwithdrawpage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notifChannel = new NotificationChannel("Para! Notification", "Para! Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }

        txtError = findViewById(R.id.errorMessage);
        showError = findViewById(R.id.errorLogo);

        txtPassword = findViewById(R.id.userPassword);

        btnSave = findViewById(R.id.withdrawButton);
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

        btnSave.setOnClickListener(v -> { withdrawIncome(); });

        // To cancel the changes in user (commuter) profile.
        btnCancel.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverConfirmWithdrawPage.this, driverIntWithdrawPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });

        // To return the user (commuter) to their profile info page.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverConfirmWithdrawPage.this, driverIntWithdrawPage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);

        });
    }

    // This will send the cash-out request of the user (driver) to database.
    private void withdrawIncome() {

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String withdrawAmount = intent.getString("getUserWithdrawAmount");
        String withdrawMethod = intent.getString("getUserWithdrawMethod");

        String userPassword = txtPassword.getText().toString();

        String text26 = getString(R.string.text26);
        String text129 = getString(R.string.text132);
        String text130 = getString(R.string.text133);
        String text234 = getString(R.string.text234);
        String text239 = getString(R.string.text239);

        // Creates database reference where user (driver) income withdrawal details be sent.
        DatabaseReference verifyUser = firebaseDatabase.child("accountInfo");

        // Code based from Peter Haddad (StackOverflow)
        verifyUser.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot currentUser : dataSnapshot.getChildren()) {

                            String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                            String currentMonth = (String) android.text.format.DateFormat.format("MMMM", new Date());

                            String currUserPassword = currentUser.child("accountPassword").getValue(String.class);
                            String getDriverCurrBalance = currentUser.child("driverBalance").getValue(String.class);
                            double currDriverBalance = Double.parseDouble(Objects.requireNonNull(getDriverCurrBalance));
                            double setWithdrawAmount = Double.parseDouble(Objects.requireNonNull(withdrawAmount));

                            double newBalance = currDriverBalance - setWithdrawAmount;

                            // Encrypt withdrawal transaction.
                            String newWithdrawal = verifyUser.push().getKey();

                            // Get the current date to string.
                            String withdrawalRequestDateTime = DateFormat.getDateTimeInstance().format(new Date());
                            String withdrawalRequestDate = DateFormat.getDateInstance().format(new Date());

                            if (!(userPassword.equals(""))) {
                                if (Objects.equals(currUserPassword, userPassword)) {
                                    if (currDriverBalance < setWithdrawAmount) {

                                        // Redirect the user (driver) to withdraw failed page if the withdraw request failed.
                                        Intent intent = new Intent(driverConfirmWithdrawPage.this, driverWithdrawFailedPage.class);
                                        intent.putExtra("getUserId", accountId);
                                        intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                        intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                        intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                        intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                        intent.putExtra("getUserAccVerification", accountVerification);
                                        intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                        startActivity(intent);

                                    } else {

                                        if (withdrawMethod.equals("G-Cash")) {

                                            // Send user (driver) withdrawal request details to Firebase and deduct the withdrawal amount from their balance.
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestDate").setValue(withdrawalRequestDateTime);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestMethod").setValue(withdrawMethod);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestAmount").setValue(decimalFormat.format(setWithdrawAmount));
                                            verifyUser.child(accountId).child("driverBalance").setValue(decimalFormat.format(newBalance));

                                            if (!currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").exists()) {
                                                String getDriverDailyBalance = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").getValue(String.class);
                                                double currDriverDailyBalance = Double.parseDouble(Objects.requireNonNull(getDriverDailyBalance));
                                                double newDriverDailyBalance = currDriverDailyBalance - setWithdrawAmount;

                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").setValue(decimalFormat.format(newDriverDailyBalance));
                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").setValue(decimalFormat.format(setWithdrawAmount));

                                            } else {

                                                // Add data of withdrawn income to daily record.
                                                String getCurrWithdrawIncome = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").getValue(String.class);
                                                double currWithdrawIncome = Double.parseDouble(Objects.requireNonNull(getCurrWithdrawIncome));
                                                double newWithdrawIncome = currWithdrawIncome + setWithdrawAmount;

                                                // Update daily balance.
                                                String getDriverDailyBalance = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").getValue(String.class);
                                                double currDriverDailyBalance = Double.parseDouble(Objects.requireNonNull(getDriverDailyBalance));
                                                double newDriverDailyBalance = currDriverDailyBalance - setWithdrawAmount;

                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").setValue(decimalFormat.format(newDriverDailyBalance));
                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").setValue(decimalFormat.format(newWithdrawIncome));

                                            }

                                            // Code based from Easy Tuto.
                                            // (YT Link: https://www.youtube.com/watch?v=4BuRMScaaI4)
                                            // Push notification to alert the user (driver) about the status of withdrawal.
                                            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(driverConfirmWithdrawPage.this, "Para! Notification");
                                            notifBuilder.setContentTitle(text129);
                                            notifBuilder.setContentText(String.format("%s (Php %s) %s %s", text239, decimalFormat.format(setWithdrawAmount), text130, withdrawMethod));
                                            notifBuilder.setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(String.format("%s (Php %s) %s %s", text239, decimalFormat.format(setWithdrawAmount), text130, withdrawMethod)));
                                            notifBuilder.setSmallIcon(R.drawable.ic_notification_icon);
                                            notifBuilder.setAutoCancel(true);

                                            NotificationManagerCompat notifManagerCompat = NotificationManagerCompat.from(driverConfirmWithdrawPage.this);
                                            notifManagerCompat.notify(1, notifBuilder.build());

                                            // Redirect the user (driver) to withdraw success page if the withdraw request was a success.
                                            Intent intent = new Intent(driverConfirmWithdrawPage.this, driverWithdrawSuccessPageA.class);
                                            intent.putExtra("getUserId", accountId);
                                            intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                            intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                            intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                            intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                            intent.putExtra("getUserAccVerification", accountVerification);
                                            intent.putExtra("getUserBalance", decimalFormat.format(newBalance));

                                            startActivity(intent);

                                        } else if (withdrawMethod.equals("PayMaya")) {

                                            // Sends user (driver) withdrawal request details to Firebase and deducts the withdrawal amount from their balance.
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestDate").setValue(withdrawalRequestDateTime);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestMethod").setValue(withdrawMethod);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestAmount").setValue(decimalFormat.format(setWithdrawAmount));
                                            verifyUser.child(accountId).child("driverBalance").setValue(decimalFormat.format(newBalance));

                                            if (!currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").exists()) {
                                                String getDriverDailyBalance = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").getValue(String.class);
                                                double currDriverDailyBalance = Double.parseDouble(Objects.requireNonNull(getDriverDailyBalance));
                                                double newDriverDailyBalance = currDriverDailyBalance - setWithdrawAmount;

                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").setValue(decimalFormat.format(newDriverDailyBalance));
                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").setValue(decimalFormat.format(setWithdrawAmount));

                                            } else {

                                                // Add data of withdrawn income to daily record.
                                                String getCurrWithdrawIncome = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").getValue(String.class);
                                                double currWithdrawIncome = Double.parseDouble(Objects.requireNonNull(getCurrWithdrawIncome));
                                                double newWithdrawIncome = currWithdrawIncome + setWithdrawAmount;

                                                // Update daily balance.
                                                String getDriverDailyBalance = currentUser.child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").getValue(String.class);
                                                double currDriverDailyBalance = Double.parseDouble(Objects.requireNonNull(getDriverDailyBalance));
                                                double newDriverDailyBalance = currDriverDailyBalance - setWithdrawAmount;

                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverDailyBalance").setValue(decimalFormat.format(newDriverDailyBalance));
                                                verifyUser.child(accountId).child("incomeData").child(currentYear).child(currentMonth).child(withdrawalRequestDate).child("driverWithdrawnIncome").setValue(decimalFormat.format(newWithdrawIncome));

                                            }

                                            // Code based from Easy Tuto.
                                            // (YT Link: https://www.youtube.com/watch?v=4BuRMScaaI4)
                                            // Push notification to alert the user (driver) about the status of withdrawal.
                                            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(driverConfirmWithdrawPage.this, "Para! Notification");
                                            notifBuilder.setContentTitle(text129);
                                            notifBuilder.setContentText(String.format("%s (Php %s) %s %s", text239, decimalFormat.format(setWithdrawAmount), text130, withdrawMethod));
                                            notifBuilder.setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(String.format("%s (Php %s) %s %s", text239, decimalFormat.format(setWithdrawAmount), text130, withdrawMethod)));
                                            notifBuilder.setSmallIcon(R.drawable.ic_notification_icon);
                                            notifBuilder.setAutoCancel(true);

                                            NotificationManagerCompat notifManagerCompat = NotificationManagerCompat.from(driverConfirmWithdrawPage.this);
                                            notifManagerCompat.notify(1, notifBuilder.build());

                                            // Redirect the user (driver) to withdraw success page if the withdraw request was a success.
                                            Intent intent = new Intent(driverConfirmWithdrawPage.this, driverWithdrawSuccessPageA.class);
                                            intent.putExtra("getUserId", accountId);
                                            intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                            intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                            intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                            intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                            intent.putExtra("getUserAccVerification", accountVerification);
                                            intent.putExtra("getUserBalance", decimalFormat.format(newBalance));

                                            startActivity(intent);

                                        } else {

                                            // Sends user (driver) withdrawal request details to Firebase but does not deduct the withdrawal amount from their balance yet.
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestDate").setValue(withdrawalRequestDateTime);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestMethod").setValue(withdrawMethod);
                                            verifyUser.child(accountId).child("withdrawalHistory").child(newWithdrawal).child("withdrawalRequestAmount").setValue(decimalFormat.format(setWithdrawAmount));

                                            // Redirect the user (driver) to withdraw success page if the withdraw request was a success.
                                            Intent intent = new Intent(driverConfirmWithdrawPage.this, driverWithdrawSuccessPageB.class);
                                            intent.putExtra("getUserId", accountId);
                                            intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                            intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                            intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                            intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                            intent.putExtra("getUserAccVerification", accountVerification);
                                            intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                            startActivity(intent);
                                        }
                                    }
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

                    // Redirect the user (driver) to withdraw failed page if the withdraw request failed.
                    Intent intent = new Intent(driverConfirmWithdrawPage.this, driverWithdrawFailedPage.class);
                    intent.putExtra("getUserId", accountId);
                    intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                    intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                    intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                    intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                    intent.putExtra("getUserAccVerification", accountVerification);
                    intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                    startActivity(intent);
                }

        });
    }
}
