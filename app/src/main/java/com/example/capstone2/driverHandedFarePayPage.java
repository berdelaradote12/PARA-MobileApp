package com.example.capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

// This page will record the payments handed directly to the driver.
public class driverHandedFarePayPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    String  farePaid, routeOrigin, routeDestination, transactionRoute, otherFarePaid, fareChange;
    EditText txtOtherAmount;
    TextView txtSelFareAmount, txtSelOriRoute, txtSelDstRoute, txtFareAmount, txtChange;
    Spinner fareAmountOption, routeOriginOption, routeDestinationOption;
    Button btnDiscounted, btnAccept, btnCancel;
    ImageButton btnFareAmount, btnRouteOrigin, btnRouteDestination, btnBack;

    double regularFareAmount = 17.00;
    double discountAmount = regularFareAmount * 0.20;
    double discountedFareAmount = regularFareAmount - discountAmount;
    double totalFareAmount;

    int clickCount = 0;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_handedfarepaypage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String text128 = getString(R.string.text128);
        String text130 = getString(R.string.text130);
        String text241 = getString(R.string.text241);
        String text243 = getString(R.string.text243);

        txtSelFareAmount = findViewById(R.id.selectedFareAmount);
        txtSelOriRoute = findViewById(R.id.selectedRouteOrigin);
        txtSelDstRoute = findViewById(R.id.selectedRouteDestination);
        txtOtherAmount = findViewById(R.id.otherFareAmount);
        txtFareAmount = findViewById(R.id.textView27);
        txtChange = findViewById(R.id.textView28);

        fareAmountOption = findViewById(R.id.fareAmountSpinner);
        routeOriginOption = findViewById(R.id.selRouteOriSpinner);
        routeDestinationOption = findViewById(R.id.selRouteDstSpinner);

        btnFareAmount = findViewById(R.id.fareAmountButton);
        btnRouteOrigin = findViewById(R.id.selRouteOriButton);
        btnRouteDestination = findViewById(R.id.selRouteDstButton);
        btnDiscounted = findViewById(R.id.discountedButton);
        btnAccept = findViewById(R.id.intWithdrawButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);

        txtFareAmount.setText(decimalFormat.format(regularFareAmount));

        // To select the fare amount handed by the commuter.
        btnFareAmount.setOnClickListener(v -> {
            otherFarePaid = txtOtherAmount.getText().toString();
            if (clickCount == 0) {
                fareAmountOption.performClick();
                if(!(otherFarePaid.equals(""))) {
                    txtOtherAmount.setText("");
                    return;
                }
                clickCount++;
            } else if (clickCount == 1) {
                fareAmountOption.performClick();
                txtOtherAmount.setText("");
                clickCount = 0;
            }
        });

        fareAmountOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedMethod = adapterView.getItemAtPosition(i).toString();
                switch (selectedMethod) {
                    case "17.00":
                    case "30.00":
                    case "45.00":
                    case "60.00":
                    case "75.00":
                    case "90.00":
                        farePaid = adapterView.getItemAtPosition(i).toString();
                        txtSelFareAmount.setText(String.format("Php %s", farePaid));
                        txtSelFareAmount.setTextColor(Color.WHITE);

                        double selFareAmount = Double.parseDouble(Objects.requireNonNull(farePaid));
                        double change = selFareAmount - regularFareAmount;

                        // To provide discount on the fare amount handed by the commuter.
                        btnDiscounted.setOnClickListener(v -> {

                            if (clickCount == 0) {
                                txtFareAmount.setText(decimalFormat.format(discountedFareAmount));
                                totalFareAmount = discountedFareAmount;

                                double changeB = selFareAmount - discountedFareAmount;
                                txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(changeB)));
                                fareChange = decimalFormat.format(changeB);

                                clickCount++;
                            } else if (clickCount == 1) {
                                txtFareAmount.setText(decimalFormat.format(regularFareAmount));
                                totalFareAmount = regularFareAmount;

                                double changeC = selFareAmount - regularFareAmount;
                                txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(changeC)));
                                fareChange = decimalFormat.format(changeC);

                                clickCount = 0;
                            }
                        });

                        txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(change)));
                        fareChange = decimalFormat.format(change);
                        break;
                    case "":
                        farePaid = text130;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // To select the route origin of the commuter.
        btnRouteOrigin.setOnClickListener(v -> { routeOriginOption.performClick(); });

        routeOriginOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedMethod = adapterView.getItemAtPosition(i).toString();
                switch (selectedMethod) {
                    case "Baclaran":
                    case "EDSA":
                    case "Libertad":
                    case "Gil Puyat":
                    case "Vito Cruz":
                    case "Quirino":
                    case "Pedro Gil":
                    case "United Nations":
                    case "Central Terminal":
                    case "Carriedo":
                    case "Doroteo Jose":
                    case "Bambang":
                    case "Tayuman":
                    case "Blumentritt":
                    case "Abad Santos":
                    case "R. Papa":
                    case "5th Avenue":
                    case "Monumento":
                    case "Balintawak":
                    case "Roosevelt":
                        routeOrigin = adapterView.getItemAtPosition(i).toString();
                        txtSelOriRoute.setText(routeOrigin);
                        txtSelOriRoute.setTextColor(Color.WHITE);

                        break;
                    case "":
                        routeOrigin = text241;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // To select the route destination of the commuter.
        btnRouteDestination.setOnClickListener(v -> { routeDestinationOption.performClick(); });

        routeDestinationOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedMethod = adapterView.getItemAtPosition(i).toString();
                switch (selectedMethod) {
                    case "Baclaran":
                    case "EDSA":
                    case "Libertad":
                    case "Gil Puyat":
                    case "Vito Cruz":
                    case "Quirino":
                    case "Pedro Gil":
                    case "United Nations":
                    case "Central Terminal":
                    case "Carriedo":
                    case "Doroteo Jose":
                    case "Bambang":
                    case "Tayuman":
                    case "Blumentritt":
                    case "Abad Santos":
                    case "R. Papa":
                    case "5th Avenue":
                    case "Monumento":
                    case "Balintawak":
                    case "Roosevelt":
                        routeDestination = adapterView.getItemAtPosition(i).toString();
                        txtSelDstRoute.setText(routeDestination);
                        txtSelDstRoute.setTextColor(Color.WHITE);

                        break;
                    case "":
                        routeDestination = text243;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Code based from user2336315 (StackOverflow).
        // Updates txtChange when the user started typing.
        txtOtherAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() != 0) {
                    farePaid = txtOtherAmount.getText().toString(); }

                double insAmount = Double.parseDouble(farePaid);
                double change = insAmount - regularFareAmount;
                totalFareAmount = regularFareAmount;

                // To provide discount on the fare amount handed by the commuter.
                btnDiscounted.setOnClickListener(v -> {

                    if (clickCount == 0) {
                        txtFareAmount.setText(decimalFormat.format(discountedFareAmount));
                        totalFareAmount = discountedFareAmount;

                        double changeB = insAmount - discountedFareAmount;
                        txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(changeB)));
                        fareChange = decimalFormat.format(changeB);

                        clickCount++;
                    } else if (clickCount == 1) {
                        txtFareAmount.setText(decimalFormat.format(regularFareAmount));
                        totalFareAmount = regularFareAmount;

                        double changeC = insAmount - regularFareAmount;
                        txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(changeC)));
                        fareChange = decimalFormat.format(changeC);

                        clickCount = 0;
                    }

                });

                txtSelFareAmount.setText(text130);
                txtSelFareAmount.setTextSize(18);
                txtSelFareAmount.setTextColor(Color.parseColor("#B2FFFFFF"));

                txtChange.setText(String.format("%s Php %s", text128, decimalFormat.format(change)));
                fareChange = decimalFormat.format(change);
            }
        });

        // To initiate withdraw request function.
        btnAccept.setOnClickListener(v -> { recordHandedFare(); });

        // To return the user (driver) to homepage.
        btnCancel.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverHandedFarePayPage.this, driverHomePage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });

        // To return the user (driver) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverHandedFarePayPage.this, driverHomePage.class);
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

    // This will send the handed fare transaction details to Firebase database.
    private void recordHandedFare() {

        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String referenceId = "Manual payment";
        transactionRoute = routeOrigin + " to " + routeDestination;

        // Get the current date to string.
        String transactionDateTime = DateFormat.getDateTimeInstance().format(new Date());

        // Sends the transaction details to Firebase database.
        DatabaseReference verifyTransaction = firebaseDatabase.child("accountInfo");

        verifyTransaction.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot currentTransaction : dataSnapshot.getChildren()) {

                    // Send transaction details to the existing user data.
                    // For Driver.
                    String newPayment = verifyTransaction.push().getKey();
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionReference").setValue(referenceId);
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionRoute").setValue(transactionRoute);
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionDate").setValue(transactionDateTime);
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionAmount").setValue(decimalFormat.format(totalFareAmount));
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionPayment").setValue(farePaid);
                    verifyTransaction.child(accountId).child("paymentsReceived").child(newPayment).child("transactionChange").setValue(fareChange);

                    Intent intent3 = new Intent(driverHandedFarePayPage.this, driverPayRecvHistoryPage.class);
                    intent3.putExtra("getUserId", accountId);
                    intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
                    intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
                    intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
                    intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                    intent3.putExtra("getUserAccVerification", accountVerification);
                    intent3.putExtra("getUserBalance", accountBalance);

                    startActivity(intent3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Method for an error is received.
                // Toast.makeText(driverHandedFarePayPage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
