package com.example.capstone2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

// This page will validate if the user (commuter) is ready to pay the fare.
public class commuterConfirmPayPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    Button btnConfirm, btnCancel;
    ImageButton btnBack;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_confirmpaypage);

        // Always call the acquired data from the user who logged-in.
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
        String transactionRoute = intent.getString("getTransactionRoute");
        String transactionDateTime = intent.getString("getTransactionDateTime");
        String transactionDate = intent.getString("getTransactionDate");
        String fareAmount = intent.getString("getFareAmount");

        String driverId = intent.getString("getQrCode");

        TextView payerName = findViewById(R.id.textView54);
        TextView paymentDate = findViewById(R.id.textView55);
        TextView travelRoute = findViewById(R.id.textView56);
        TextView paymentAmount = findViewById(R.id.textView58);
        TextView paymentReference = findViewById(R.id.textView60);

        btnConfirm = findViewById(R.id.confirmButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);

        payerName.setText(accountFullName);
        paymentDate.setText(transactionDateTime);
        travelRoute.setText(transactionRoute);
        paymentAmount.setText(String.format("Php %s", fareAmount));
        paymentReference.setText(referenceId);

        // Record fare payment transaction of the user.
        btnConfirm.setOnClickListener(v -> recordFarePayment());

        // To cancel fare payment transaction.
        btnCancel.setOnClickListener(v -> {
            Intent intent1 = new Intent(commuterConfirmPayPage.this, commuterIntPayPage.class);
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

        // To return the user to homepage.
        btnBack.setOnClickListener(view -> {
            Intent intent2 = new Intent(commuterConfirmPayPage.this, commuterPayPage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent2.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));


            intent2.putExtra("getQrCode", String.valueOf(driverId));
            intent2.putExtra("getReferenceId", referenceId);
            intent2.putExtra("getTransactionDateTime", transactionDateTime);
            intent2.putExtra("getTransactionDate", transactionDate);
            intent2.putExtra("getTransactionRoute", transactionRoute);

            startActivity(intent2);
        });
    }

    // This will send the fare payment transaction details to Firebase database.
    private void recordFarePayment() {

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
        String transactionRoute = intent.getString("getTransactionRoute");
        String transactionDateTime = intent.getString("getTransactionDateTime");
        String transactionDate = intent.getString("getTransactionDate");
        String fareAmount = intent.getString("getFareAmount");

        String driverId = intent.getString("getQrCode");

        // To verify that the data from QR Code is the driver's id.
        // If yes, proceed in sending the transaction data to Firebase.
        DatabaseReference verifyTransaction = firebaseDatabase.child("accountInfo");

        verifyTransaction.orderByChild("accountId").equalTo(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot currentTransaction : dataSnapshot.getChildren()) {

                    String getDriverCurrBalance = currentTransaction.child("driverBalance").getValue(String.class);
                    String getDriverDriverDailyBoundaryBalance = currentTransaction.child("driverDailyBoundary").getValue(String.class);

                    double currDriverBalance = Double.parseDouble(Objects.requireNonNull(getDriverCurrBalance));
                    double currDriverDailyBoundaryBalance = Double.parseDouble(Objects.requireNonNull(getDriverDriverDailyBoundaryBalance));

                    // Creates database reference where transaction details will be sent.
                    DatabaseReference transactionData = firebaseDatabase.child("accountInfo");

                    // Code based from Peter Haddad (StackOverflow)
                    transactionData.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot intTransaction : dataSnapshot.getChildren()) {

                                    String newTransaction = transactionData.push().getKey();

                                    String getCurrBalance = intTransaction.child("commuterBalance").getValue(String.class);
                                    double currBalance = Double.parseDouble(Objects.requireNonNull(getCurrBalance));
                                    double setFareAmount = Double.parseDouble(Objects.requireNonNull(fareAmount));

                                    double newBalance = currBalance - setFareAmount;

                                    if (currBalance > setFareAmount) {

                                        // Send transaction details to the existing user data.
                                        // For Commuter.
                                        transactionData.child(accountId).child("paymentsHistory").child(newTransaction).child("transactionReference").setValue(String.valueOf(referenceId));
                                        transactionData.child(accountId).child("paymentsHistory").child(newTransaction).child("transactionDate").setValue(transactionDateTime);
                                        transactionData.child(accountId).child("paymentsHistory").child(newTransaction).child("transactionRoute").setValue(transactionRoute);
                                        transactionData.child(accountId).child("paymentsHistory").child(newTransaction).child("transactionAmount").setValue(fareAmount);
                                        transactionData.child(accountId).child("commuterBalance").setValue(decimalFormat.format(newBalance));

                                        // Redirect to next page with new balance.
                                        Intent intent = new Intent(commuterConfirmPayPage.this, commuterPaySuccessPage.class);
                                        intent.putExtra("getUserId", accountId);
                                        intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                        intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                        intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                        intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                        intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                                        intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                        intent.putExtra("getUserBalance", decimalFormat.format(newBalance));

                                        intent.putExtra("getReferenceId", String.valueOf(referenceId));
                                        intent.putExtra("getTransactionDate", String.valueOf(transactionDateTime));
                                        intent.putExtra("getTransactionRoute", transactionRoute);
                                        intent.putExtra("getFareAmount", fareAmount);
                                        startActivity(intent);

                                        double setBoundaryAmount = 100.00;

                                        // Add the fare paid amount to driver's income data.
                                        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                                        String currentMonth = (String) DateFormat.format("MMMM", new Date());
                                        String currentDay = (String) DateFormat.format("MMMM dd, yyyy", new Date());

                                        if (currDriverDailyBoundaryBalance != 100.00) {

                                            double addBoundary = currDriverDailyBoundaryBalance + setFareAmount;
                                            verifyTransaction.child(driverId).child("driverDailyBoundary").setValue(decimalFormat.format(addBoundary));

                                            if (currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).exists()) {

                                                String getDailyBoundary = currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").getValue(String.class);

                                                double resetIncome = 0.00;
                                                double dailyBoundary = Double.parseDouble(Objects.requireNonNull(getDailyBoundary));
                                                double addDailyBoundary = dailyBoundary + setFareAmount;

                                                // Resets amount in daily income and daily boundary.

                                                verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").setValue(decimalFormat.format(resetIncome));
                                                verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").setValue(decimalFormat.format(addDailyBoundary));
                                            }

                                        }

                                        if (currDriverDailyBoundaryBalance > 100.00) {

                                            // For driver daily balance.
                                            String getDailyIncome = currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").getValue(String.class);
                                            String getDailyBoundary = currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").getValue(String.class);

                                            double dailyIncome = Double.parseDouble(Objects.requireNonNull(getDailyIncome));
                                            double dailyBoundary = Double.parseDouble(Objects.requireNonNull(getDailyBoundary));
                                            double deductDailyBoundary = dailyBoundary - setBoundaryAmount;
                                            double newAddedDailyIncome = dailyIncome + (setFareAmount + deductDailyBoundary);

                                            // For driver total balance.
                                            double deductBoundary = currDriverDailyBoundaryBalance - setBoundaryAmount;
                                            double newAddedIncome = currDriverBalance + (setFareAmount + deductBoundary);

                                            verifyTransaction.child(driverId).child("driverBalance").setValue(decimalFormat.format(newAddedIncome));
                                            verifyTransaction.child(driverId).child("driverDailyBoundary").setValue(decimalFormat.format(setBoundaryAmount));

                                            // For driver daily balance.
                                            if (dailyBoundary > 100.00) {

                                                verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").setValue(decimalFormat.format(newAddedDailyIncome));
                                                verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").setValue(decimalFormat.format(setBoundaryAmount));

                                            }

                                        } else if (!currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).exists()) {

                                            // New day transaction.
                                            double resetIncome = 0.00;

                                            verifyTransaction.child(driverId).child("driverDailyBoundary").setValue(decimalFormat.format(setFareAmount));


                                            verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("incomeCurrentDay").setValue(currentDay);
                                            verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").setValue(decimalFormat.format(resetIncome));
                                            verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").setValue(decimalFormat.format(setFareAmount));
                                            verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverWithdrawnIncome").setValue(decimalFormat.format(resetIncome));

                                        }

                                        if (currDriverDailyBoundaryBalance == 100.00) {

                                            // For driver total balance.
                                            double addedIncome = currDriverBalance + setFareAmount;
                                            verifyTransaction.child(driverId).child("driverBalance").setValue(decimalFormat.format(addedIncome));

                                            // To get exact daily income.
                                            if (currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).exists()) {

                                                // For driver daily balance.
                                                String getDailyIncome = currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").getValue(String.class);
                                                String getDailyBoundary = currentTransaction.child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBoundary").getValue(String.class);

                                                double dailyIncome = Double.parseDouble(Objects.requireNonNull(getDailyIncome));
                                                double dailyBoundary = Double.parseDouble(Objects.requireNonNull(getDailyBoundary));

                                                // For driver daily balance.
                                                if (dailyBoundary == 100.00) {
                                                    double addedDailyIncome = dailyIncome + setFareAmount;

                                                    verifyTransaction.child(driverId).child("incomeData").child(currentYear).child(currentMonth).child(transactionDate).child("driverDailyBalance").setValue(decimalFormat.format(addedDailyIncome));
                                                }
                                            }
                                        }

                                    } else {

                                        // Redirect to next page with existing balance.
                                        Intent intent = new Intent(commuterConfirmPayPage.this, commuterPayFailedPage.class);
                                        intent.putExtra("getUserId", String.valueOf(accountId));
                                        intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                                        intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                                        intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                                        intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                        intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                                        intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                                        intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                                        intent.putExtra("getReferenceId", String.valueOf(referenceId));
                                        intent.putExtra("getTransactionDate", String.valueOf(transactionDateTime));
                                        intent.putExtra("getTransactionRoute", transactionRoute);
                                        intent.putExtra("getFareAmount", fareAmount);
                                        startActivity(intent);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Method for an error is received.
                //Toast.makeText(commuterConfirmPayPage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}