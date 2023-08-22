package com.example.capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class driverIntWithdrawPage extends AppCompatActivity {

    EditText txtWithdrawAmount;
    TextView txtSelWithdrawMethod, txtSelWdMethodWarning;
    String withdrawMethod;
    Spinner withdrawMethodOption;
    Button btnWithdrawAll, btnWithdraw, btnCancel;
    ImageButton btnDropDown, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_intwithdrawpage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        String text126 = getString(R.string.text129);

        txtWithdrawAmount = findViewById(R.id.withdrawAmount);
        txtSelWithdrawMethod = findViewById(R.id.selectedWithdrawMethod);
        txtSelWdMethodWarning = findViewById(R.id.textView16);

        withdrawMethodOption = findViewById(R.id.withdrawMethodSpinner);

        btnWithdrawAll = findViewById(R.id.withdrawAllButton);
        btnDropDown = findViewById(R.id.withdrawMethodButton);
        btnWithdraw = findViewById(R.id.intWithdrawButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);

        // To withdraw all the user's (driver) balance.
        btnWithdrawAll.setOnClickListener(v -> { txtWithdrawAmount.setText(accountBalance); });

        // To select the user (driver) withdraw method.
        btnDropDown.setOnClickListener(v -> {
            withdrawMethodOption.performClick();
        });

        withdrawMethodOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedMethod = adapterView.getItemAtPosition(i).toString();
                switch (selectedMethod) {
                    case "Cash Pick Up (Cebuana Lhuillier)":
                    case "Cash Pick Up (Palawan)":
                            withdrawMethod = adapterView.getItemAtPosition(i).toString();
                            txtSelWithdrawMethod.setText(withdrawMethod);
                            txtSelWdMethodWarning.setVisibility(View.VISIBLE);
                            txtSelWithdrawMethod.setTextColor(Color.WHITE);
                            break;
                    case "PayMaya":
                    case "G-Cash":
                            withdrawMethod = adapterView.getItemAtPosition(i).toString();
                            txtSelWithdrawMethod.setText(withdrawMethod);
                            txtSelWithdrawMethod.setTextColor(Color.WHITE);
                            break;
                    case "":
                            withdrawMethod = text126;
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // To initiate withdraw request function.
        btnWithdraw.setOnClickListener(v -> {

            String withdrawAmount = txtWithdrawAmount.getText().toString();

            Intent intent1 = new Intent(driverIntWithdrawPage.this, driverConfirmWithdrawPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent1.putExtra("getUserWithdrawAmount", withdrawAmount);
            intent1.putExtra("getUserWithdrawMethod", withdrawMethod);

            startActivity(intent1);

        });

        // To return the user (driver) to homepage.
        btnCancel.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverIntWithdrawPage.this, driverHomePage.class);
            intent2.putExtra("getUserId", String.valueOf(accountId));
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", accountVerification);
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);

        });

        // To return the user (driver) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent3 = new Intent(driverIntWithdrawPage.this, driverHomePage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserAccVerification", accountVerification);
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent3);

        });

    }
}
