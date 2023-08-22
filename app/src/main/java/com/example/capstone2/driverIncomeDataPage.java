package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.Date;

public class driverIncomeDataPage extends AppCompatActivity {

    TextView txtCurrDate;
    ImageButton btnBack;
    // ImageButton btnSearch;
    // EditText dateSearch;

    private RecyclerView lstIncomeHistory;
    private FirebaseRecyclerAdapter<accountInfo, lstIncomeHistoryHolder> lstIncomeHistoryAdapter;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_incomedatapage);

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

        // Add the fare paid amount to driver's income data.
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String currentMonth = (String) DateFormat.format("MMMM", new Date());

        btnBack = findViewById(R.id.backButton);
        lstIncomeHistory = findViewById(R.id.incomeHistoryList);

        // Code based from Alex Mamo (StackOverflow).
        // Displays a RecyclerView linear display.
        lstIncomeHistory.setLayoutManager(new LinearLayoutManager(this));

        // Creates a query to retrieve the required data from Firebase database.
        DatabaseReference lstIncomeHistoryData = firebaseDatabase.child("accountInfo").child(accountId).child("incomeData").child(currentYear).child(currentMonth);
        Query lstIncomeHistoryFinal = lstIncomeHistoryData.limitToLast(2);

        // Creates a builder to connect the data from created query to RecyclerView.
        FirebaseRecyclerOptions<accountInfo> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<accountInfo>()
                .setQuery(lstIncomeHistoryFinal, accountInfo.class)
                .build();

        // Creates an adapter to handle the data from Firebase database to RecyclerView.
        lstIncomeHistoryAdapter = new FirebaseRecyclerAdapter<accountInfo, driverIncomeDataPage.lstIncomeHistoryHolder>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull driverIncomeDataPage.lstIncomeHistoryHolder driverIncomeHistoryHolder, int position, @NonNull accountInfo accountInfo) {
                driverIncomeHistoryHolder.setAccountInfo(accountInfo);
            }

            @Override
            public driverIncomeDataPage.lstIncomeHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_incomedata, parent, false);

                return new driverIncomeDataPage.lstIncomeHistoryHolder(view);
            }

            // Code based from anrodon (GitHub)
            // Sorts the transaction history from most recent.
            @Override
            public accountInfo getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

        };

        lstIncomeHistory.setAdapter(lstIncomeHistoryAdapter);

        // To return the user to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverIncomeDataPage.this, driverHomePage.class);
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

    // Code based from Alex Mamo (StackOverflow).
    // Sets the data retrieved from Firebase database to the model class (accountInfo) and RecyclerView.
    public class lstIncomeHistoryHolder extends RecyclerView.ViewHolder {

        public Button btnWithdrawalHistory;
        private TextView txtDailyIncome, txtWithdrawnIncome, txtBoundaryAmount;

        // Get data from previous intent, also new data for when redirecting to homepage.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountCommuterType = intent.getString("getUserCommuterType");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        lstIncomeHistoryHolder(View itemView) {
            super(itemView);

            btnWithdrawalHistory = itemView.findViewById(R.id.wdHistoryButton);

            txtCurrDate = itemView.findViewById(R.id.textView49);
            txtDailyIncome = itemView.findViewById(R.id.textView51);
            txtWithdrawnIncome = itemView.findViewById(R.id.textView52);
            txtBoundaryAmount = itemView.findViewById(R.id.textView53);

            btnWithdrawalHistory.setOnClickListener(v -> {

                Intent intent3 = new Intent(driverIncomeDataPage.this, driverWithdrawHistoryPage.class);
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

        void setAccountInfo(accountInfo accountInfo) {

            String dailyIncome = accountInfo.getDriverDailyBalance();
            String withdrawnIncome = accountInfo.getDriverWithdrawnIncome();
            String dailyBoundary = accountInfo.getDriverDailyBoundary();
            String currentDate = accountInfo.getIncomeCurrentDay();

            txtDailyIncome.setText(dailyIncome);
            txtWithdrawnIncome.setText(withdrawnIncome);
            txtBoundaryAmount.setText(dailyBoundary);
            txtCurrDate.setText(currentDate);

        }

    }

    // To initialize the RecyclerView adapter.
    @Override
    protected void onStart() {
        super.onStart();
        lstIncomeHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (lstIncomeHistoryAdapter != null) {
            lstIncomeHistoryAdapter.stopListening();
        }
    }
}
