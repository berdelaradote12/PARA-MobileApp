package com.example.capstone2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.text.DecimalFormat;
import java.util.Objects;

// This page displays the user's (driver) homepage interface.
public class driverHomePage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    Button btnIncomeDetails;
    ImageButton btnWithdraw, btnHandedFare, btnPaymentReceived, btnUser;

    // Getting the already existing reference from our Firebase database.
    // This will be used to input cash in data to Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_homepage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifChannel = new NotificationChannel("Para! Notification", "Para! Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }

        String text143 = getString(R.string.text143);
        String text144 = getString(R.string.text144);
        String text145 = getString(R.string.text145);

        TextView driverBalance = findViewById(R.id.textView10);

        btnIncomeDetails = findViewById(R.id.incomeDetailsButton);
        btnWithdraw = findViewById(R.id.intWithdrawButton);
        btnHandedFare = findViewById(R.id.handedFareButton);
        btnPaymentReceived = findViewById(R.id.paymentReceivedButton);
        btnUser = findViewById(R.id.userButton);

        // Change string format of account balance to have "Php."
        driverBalance.setText(String.format("Php %s", accountBalance));

        // To check whole income details of the driver.
        btnIncomeDetails.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverHomePage.this, driverIncomeDataPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

            // Creates database reference where cash in details will be sent.
            DatabaseReference cashInData = firebaseDatabase.child("accountInfo");

            // Code based from Peter Haddad (StackOverflow)
            cashInData.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot currentTransaction : dataSnapshot.getChildren()) {

                            String getDriverDriverDailyBoundaryBalance = currentTransaction.child("driverDailyBoundary").getValue(String.class);
                            double currDriverDailyBoundaryBalance = Double.parseDouble(Objects.requireNonNull(getDriverDriverDailyBoundaryBalance));

                            if (currDriverDailyBoundaryBalance == 100.00) {

                                // Code based from Easy Tuto.
                                // (YT Link: https://www.youtube.com/watch?v=4BuRMScaaI4)
                                // Push notification to alert the user (commuter) about the status of cash in.
                                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(driverHomePage.this, "Para! Notification");
                                notifBuilder.setContentTitle(text143);
                                notifBuilder.setContentText(String.format("%s (Php %s) %s", text144, decimalFormat.format(currDriverDailyBoundaryBalance), text145));
                                notifBuilder.setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(String.format("%s (Php %s) %s", text144, decimalFormat.format(currDriverDailyBoundaryBalance), text145)));
                                notifBuilder.setSmallIcon(R.drawable.ic_notification_icon);
                                notifBuilder.setAutoCancel(true);

                                NotificationManagerCompat notifManagerCompat = NotificationManagerCompat.from(driverHomePage.this);
                                notifManagerCompat.notify(1, notifBuilder.build());

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        // To initiate withdraw profit function.
        btnWithdraw.setOnClickListener(v -> {
            Intent intent2 = new Intent(driverHomePage.this, driverIntWithdrawPage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);
        });

        // To initiate handed fare transaction function.
        btnHandedFare.setOnClickListener(v -> {
            Intent intent3 = new Intent(driverHomePage.this, driverHandedFarePayPage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserAccVerification", accountVerification);
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent3);
        });

        // To let user (driver) check their fare payment received history.
        btnPaymentReceived.setOnClickListener(v -> {
            Intent intent4 = new Intent(driverHomePage.this, driverPayRecvHistoryPage.class);
            intent4.putExtra("getUserId", String.valueOf(accountId));
            intent4.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent4.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent4.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent4.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent4.putExtra("getUserAccVerification", accountVerification);
            intent4.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent4);
        });

        // To redirect the user (driver) to their profile page.
        btnUser.setOnClickListener(v -> {
            Intent intent5 = new Intent(driverHomePage.this, driverProfilePage.class);
            intent5.putExtra("getUserId", String.valueOf(accountId));
            intent5.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent5.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent5.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent5.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent5.putExtra("getUserAccVerification", accountVerification);
            intent5.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent5);
        });
    }
}
