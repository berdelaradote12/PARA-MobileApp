package com.example.capstone2;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// This page displays all the previous payments initiated by the commuter.
public class commuterPayHistoryPage extends AppCompatActivity {

    ImageButton btnBack;
    // ImageButton btnSearch;
    // EditText dateSearch;

    TextView currentTransactions;

    private RecyclerView lstPaymentHistory;
    private FirebaseRecyclerAdapter<accountInfo, lstPayHistoryHolder> lstPayHistoryAdapter;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_payhistorypage);

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

        lstPaymentHistory = findViewById(R.id.paymentHistoryList);

        currentTransactions = findViewById(R.id.textView75);

        btnBack = findViewById(R.id.backButton);

        String text209 = getString(R.string.text209);

        currentTransactions.setText(text209);

        // Code based from Alex Mamo (StackOverflow).
        // Displays a RecyclerView linear display.
        lstPaymentHistory.setLayoutManager(new LinearLayoutManager(this));

        // Creates a query to retrieve the required data from Firebase database.
        DatabaseReference lstPaymentHistoryData = firebaseDatabase.child("accountInfo").child(accountId).child("paymentsHistory");
        Query lstPaymentHistoryFinal = lstPaymentHistoryData.limitToLast(5);

        // Creates a builder to connect the data from created query to RecyclerView.
        FirebaseRecyclerOptions<accountInfo> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<accountInfo>()
                .setQuery(lstPaymentHistoryFinal, accountInfo.class)
                .build();

        // Creates an adapter to handle the data from Firebase database to RecyclerView.
        lstPayHistoryAdapter = new FirebaseRecyclerAdapter<accountInfo, lstPayHistoryHolder>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull lstPayHistoryHolder commuterPayHistoryHolder, int position, @NonNull accountInfo accountInfo) {
                commuterPayHistoryHolder.setAccountInfo(accountInfo);

                // Code based from bastien (StackOverflow)
                commuterPayHistoryHolder.btnReceipt.setTag(position);
            }

            @Override
            public lstPayHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commuter_payhistory, parent, false);

                return new lstPayHistoryHolder(view);
            }

            // Code based from anrodon (GitHub)
            // Sorts the transaction history from most recent.
            @Override
            public accountInfo getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

        };

        lstPaymentHistory.setAdapter(lstPayHistoryAdapter);

        // To return the user to homepage.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(commuterPayHistoryPage.this, commuterHomePage.class);
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
    public class lstPayHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Button btnReceipt;
        private TextView txtRouteHistory, txtFarePaymentHistory, txtFarePaymentReference;

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

        lstPayHistoryHolder(View itemView) {
            super(itemView);

            btnReceipt = itemView.findViewById(R.id.receiptButton);

            txtRouteHistory = itemView.findViewById(R.id.textView76);
            txtFarePaymentHistory = itemView.findViewById(R.id.textView77);

            btnReceipt.setOnClickListener(this);
        }

        void setAccountInfo(accountInfo accountInfo) {

            String paymentRouteHistory = accountInfo.getTransactionRoute();
            String paymentFarePaymentHistory = accountInfo.getTransactionAmount();

            txtRouteHistory.setText(paymentRouteHistory);
            txtFarePaymentHistory.setText(paymentFarePaymentHistory);

        }

        @Override
        public void onClick(View v) {

            int position = (int) v.getTag();

            // Code based from Alex Mamo (StackOverflow)
            // Adds into array list the data retrieved from Firebase.
            DatabaseReference payHistoryData = firebaseDatabase.child("accountInfo").child(accountId).child("paymentsHistory");
            Query payHistoryDataFinal = payHistoryData.limitToLast(5);
            payHistoryDataFinal.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Problem : will not work until after 5 transactions.
                    int counter = 4;
                    for(DataSnapshot currPayHistoryData : dataSnapshot.getChildren()) {

                        // Code based from Jay Mungara (StackOverflow)
                        if(counter == position) {
                            String currPayHistoryDataReference = currPayHistoryData.child("transactionReference").getValue(String.class);
                            String currPayHistoryDataDate = currPayHistoryData.child("transactionDate").getValue(String.class);
                            String currPayHistoryDataRoute = currPayHistoryData.child("transactionRoute").getValue(String.class);
                            String currPayHistoryDataAmount = currPayHistoryData.child("transactionAmount").getValue(String.class);

                            Intent intent1 = new Intent(commuterPayHistoryPage.this, commuterPayReceiptPage.class);
                            intent1.putExtra("getUserId", String.valueOf(accountId));
                            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
                            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
                            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
                            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

                            // Code based from Karthikeya Boyini (TutorialsPoint)
                            intent1.putExtra("getUserPaymentReference", currPayHistoryDataReference);
                            intent1.putExtra("getUserPaymentDate", currPayHistoryDataDate);
                            intent1.putExtra("getUserPaymentRoute", currPayHistoryDataRoute);
                            intent1.putExtra("getUserPaymentAmount", currPayHistoryDataAmount);

                            startActivity(intent1);
                            break;
                        } else {
                            String currPayHistoryDataReference = currPayHistoryData.child("transactionReference").getValue(String.class);
                            String currPayHistoryDataDate = currPayHistoryData.child("transactionDate").getValue(String.class);
                            String currPayHistoryDataRoute = currPayHistoryData.child("transactionRoute").getValue(String.class);
                            String currPayHistoryDataAmount = currPayHistoryData.child("transactionAmount").getValue(String.class);

                            Intent intent1 = new Intent(commuterPayHistoryPage.this, commuterPayReceiptPage.class);
                            intent1.putExtra("getUserId", String.valueOf(accountId));
                            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
                            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
                            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
                            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                            intent1.putExtra("getUserCommuterType", String.valueOf(accountCommuterType));
                            intent1.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

                            // Code based from Karthikeya Boyini (TutorialsPoint)
                            intent1.putExtra("getUserPaymentReference", currPayHistoryDataReference);
                            intent1.putExtra("getUserPaymentDate", currPayHistoryDataDate);
                            intent1.putExtra("getUserPaymentRoute", currPayHistoryDataRoute);
                            intent1.putExtra("getUserPaymentAmount", currPayHistoryDataAmount);

                            startActivity(intent1);
                        }
                        counter--;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
    }

    // To initialize the RecyclerView adapter.
    @Override
    protected void onStart() {
        super.onStart();
        lstPayHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (lstPayHistoryAdapter != null) {
            lstPayHistoryAdapter.stopListening();
        }
    }
}
