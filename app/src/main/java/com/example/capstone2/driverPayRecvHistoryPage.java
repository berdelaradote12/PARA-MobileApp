package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class driverPayRecvHistoryPage extends AppCompatActivity {

    ImageButton btnBack;

    private RecyclerView lstPayRecvHistory;
    private FirebaseRecyclerAdapter<accountInfo, driverPayRecvHistoryPage.lstPayRecvHistoryHolder> lstPayRecvHistoryAdapter;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_payrecvhistorypage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        lstPayRecvHistory = findViewById(R.id.payrecvHistoryList);

        TextView currentDate = (TextView) findViewById(R.id.textView32);

        btnBack = (ImageButton) (findViewById(R.id.backButton));

        String text209 = getString(R.string.text209);

        currentDate.setText(text209);

        // Code based from Alex Mamo (StackOverflow).
        // Displays a RecyclerView linear display.
        lstPayRecvHistory.setLayoutManager(new LinearLayoutManager(this));

        // Creates a query to retrieve the required data from Firebase database.
        DatabaseReference lstPayRecvHistoryData = firebaseDatabase.child("accountInfo").child(accountId).child("paymentsReceived");
        Query lstPayRecvHistoryFinal = lstPayRecvHistoryData.limitToLast(5);

        // Creates a builder to connect the data from created query to RecyclerView.
        FirebaseRecyclerOptions<accountInfo> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<accountInfo>()
                .setQuery(lstPayRecvHistoryFinal, accountInfo.class)
                .build();

        // Creates an adapter to handle the data from Firebase database to RecyclerView.
        lstPayRecvHistoryAdapter = new FirebaseRecyclerAdapter<accountInfo, lstPayRecvHistoryHolder>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull lstPayRecvHistoryHolder lstPayRcvHistoryHolder, int position, @NonNull accountInfo accountInfo) {
                lstPayRcvHistoryHolder.setAccountInfo(accountInfo);
            }

            @Override
            public lstPayRecvHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_payrecvhistory, parent, false);

                return new lstPayRecvHistoryHolder(view);
            }

            // Code based from anrodon (GitHub)
            // Sorts the transaction history from most recent.
            @Override
            public accountInfo getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

        };

        lstPayRecvHistory.setAdapter(lstPayRecvHistoryAdapter);

        // To return the user to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverPayRecvHistoryPage.this, driverHomePage.class);
            intent1.putExtra("getUserId", accountId);
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);

        });
    }

    // Code based from Alex Mamo (StackOverflow).
    // Sets the data retrieved from Firebase database to the model class (accountInfo) and RecyclerView.
    public class lstPayRecvHistoryHolder extends RecyclerView.ViewHolder {

        private TextView txtPaidRouteHistory, txtPayRecvReferenceHistory, txtTimePayRecvHistory;

        lstPayRecvHistoryHolder(View itemView) {
            super(itemView);

            txtPaidRouteHistory = itemView.findViewById(R.id.textView29);
            txtPayRecvReferenceHistory = itemView.findViewById(R.id.textView30);
            txtTimePayRecvHistory = itemView.findViewById(R.id.textView31);

        }

        void setAccountInfo(accountInfo accountInfo) {

            String payRecvRouteHistory = accountInfo.getTransactionRoute();
            String payRecvReferenceHistory = accountInfo.getTransactionReference();
            String payRecvTimeHistory = accountInfo.getTransactionDate();

            txtPaidRouteHistory.setText(payRecvRouteHistory);
            txtPayRecvReferenceHistory.setText(payRecvReferenceHistory);
            txtTimePayRecvHistory.setText(payRecvTimeHistory);
        }

    }

    // To initialize the RecyclerView adapter.
    @Override
    protected void onStart() {
        super.onStart();
        lstPayRecvHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (lstPayRecvHistoryAdapter != null) {
            lstPayRecvHistoryAdapter.stopListening();
        }
    }
}
