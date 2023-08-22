package com.example.capstone2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
import java.util.Date;
import java.util.Objects;

// This page is where the cash-in transaction details will be sent to Firebase.
public class commuterCashInPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    EditText txtCashInAmount;
    Button btnInsertCash, btnCancel;
    ImageButton btnBack;

    // Getting the already existing reference from our Firebase database.
    // This will be used to input cash in data to Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_cashinpage);

        // Get the current user (commuter) data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notifChannel = new NotificationChannel("Para! Notification", "Para! Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }

        txtCashInAmount = (EditText) findViewById(R.id.cashInAmount);

        btnInsertCash = (Button) (findViewById(R.id.insCashButton));
        btnCancel = (Button) (findViewById(R.id.cancelButton));
        btnBack = (ImageButton) (findViewById(R.id.backButton));

        // To initiate cash in function based on the input amount of the user.
        btnInsertCash.setOnClickListener(v -> { recordCashIn(); });

        // To cancel initiated cash in by the user (commuter).
        btnCancel.setOnClickListener(v -> {
            Intent intent1 = new Intent(commuterCashInPage.this, commuterIntCashInPage.class);
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

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(view -> {
            Intent intent2 = new Intent(commuterCashInPage.this, commuterHomePage.class);
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

    private void recordCashIn() {

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

        String cashInMethod = intent.getString("getCashInMethod");
        String cashInAmount = txtCashInAmount.getText().toString();

        String text238 = getString(R.string.text238);
        String text239 = getString(R.string.text239);
        String text240 = getString(R.string.text240);

        // Creates database reference where cash in details will be sent.
        DatabaseReference cashInData = firebaseDatabase.child("accountInfo");

        // Code based from Peter Haddad (StackOverflow)
        cashInData.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot cashInTransaction : dataSnapshot.getChildren()) {

                            String newCashIn = cashInData.push().getKey();

                            // Get the current date to string.
                            String cashInDate = DateFormat.getDateTimeInstance().format(new Date());

                            double currBalance = Double.parseDouble(Objects.requireNonNull(accountBalance));
                            double setCashInAmount = Double.parseDouble(Objects.requireNonNull(cashInAmount));

                            double newBalance = currBalance + setCashInAmount;

                            // Send cash in details to the existing user data.
                            cashInData.child(accountId).child("cashInHistory").child(newCashIn).child("cashInDate").setValue(cashInDate);
                            cashInData.child(accountId).child("cashInHistory").child(newCashIn).child("cashInMethod").setValue(cashInMethod);
                            cashInData.child(accountId).child("cashInHistory").child(newCashIn).child("cashInAmount").setValue(cashInAmount);
                            cashInData.child(accountId).child("commuterBalance").setValue(decimalFormat.format(newBalance));

                            // Code based from Easy Tuto.
                            // (YT Link: https://www.youtube.com/watch?v=4BuRMScaaI4)
                            // Push notification to alert the user (commuter) about the status of cash in.
                            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(commuterCashInPage.this, "Para! Notification");
                            notifBuilder.setContentTitle(text238);
                            notifBuilder.setContentText(String.format("%s (Php %s) %s", text239, decimalFormat.format(setCashInAmount), text240));
                            notifBuilder.setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(String.format("%s (Php %s) %s", text239, decimalFormat.format(setCashInAmount), text240)));
                            notifBuilder.setSmallIcon(R.drawable.ic_notification_icon);
                            notifBuilder.setAutoCancel(true);

                            NotificationManagerCompat notifManagerCompat = NotificationManagerCompat.from(commuterCashInPage.this);
                            notifManagerCompat.notify(1, notifBuilder.build());

                            // Redirect the user to homepage with a new balance.
                            Intent intent = new Intent(commuterCashInPage.this, commuterHomePage.class);
                            intent.putExtra("getUserId", accountId);
                            intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                            intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                            intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                            intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                            intent.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                            intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                            intent.putExtra("getUserBalance", decimalFormat.format(newBalance));

                            startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
