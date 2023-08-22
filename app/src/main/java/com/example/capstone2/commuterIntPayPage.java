package com.example.capstone2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class commuterIntPayPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a "common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    String routeOrigin, routeDestination, transactionRoute;
    TextView txtSelOriRoute, txtSelDstRoute;
    Spinner routeOriOption, routeDstOption;
    Button btnSelect, btnCancel;
    ImageButton btnRouteOrigin, btnRouteDestination, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_intpaypage);

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

        String text242 = getString(R.string.text242);
        String text244 = getString(R.string.text244);

        txtSelOriRoute = findViewById(R.id.selectedOriginRoute);
        txtSelDstRoute = findViewById(R.id.selectedDestinationRoute);

        routeOriOption = findViewById(R.id.routeOriginSpinner);
        routeDstOption = findViewById(R.id.routeDestinationSpinner);

        btnRouteOrigin = findViewById(R.id.routeOriginButton);
        btnRouteDestination = findViewById(R.id.routeDestinationButton);
        btnSelect = findViewById(R.id.selectButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);

        // To select the route origin.
        btnRouteOrigin.setOnClickListener(v -> { routeOriOption.performClick(); });

        routeOriOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        routeOrigin = text242;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // To select the route destination.
        btnRouteDestination.setOnClickListener(v -> { routeDstOption.performClick(); });

        routeDstOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        routeOrigin = text244;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnSelect.setOnClickListener(v -> {

            transactionRoute = routeOrigin + " to " + routeDestination;

            Intent intent1 = new Intent(commuterIntPayPage.this, commuterPayPage.class);
            intent1.putExtra("getUserId", String.valueOf(accountId));
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            intent1.putExtra("getTransactionRoute", transactionRoute);

            startActivity(intent1);

        });

        // To return the user (commuter) to homepage.
        btnCancel.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterIntPayPage.this, commuterHomePage.class);
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

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent3 = new Intent(commuterIntPayPage.this, commuterHomePage.class);
            intent3.putExtra("getUserId", String.valueOf(accountId));
            intent3.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent3.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent3.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent3.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent3.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
            intent3.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent3.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent3);

        });


    }
}
