package com.example.capstone2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class driverFullVerificationPage extends AppCompatActivity {

    // Code based from GeeksforGeeks.
    // Code based from CodingSTUFF.
    // Code based from Tech projects.
    // Creating variables for buttons and image view.
    MaterialButton btnVaxxCard;
    ImageView vaxxCardView;
    Button btnUpload, btnCancel;
    ImageButton btnBack;

    // Implementing URI to identify the source of the uploaded image.
    private Uri filePath;

    // Creating instance for Firebase Database and its reference.
    // For sending the uploaded image URL to Firebase Realtime Database.
    private DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("accountInfo");

    // Creating instance for Firebase Storage and its reference.
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private static final int PReqCode = 1;

    // Creating instance for selecting an image.
    ActivityResultLauncher<String> selectVaxxCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_fullverificationpage);

        // Get the current user data from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountVerification = intent.getString("getUserAccVerification");
        String accountBalance = intent.getString("getUserBalance");

        btnVaxxCard = findViewById(R.id.vaccineCardButton);
        btnUpload = findViewById(R.id.uploadButton);
        btnCancel = findViewById(R.id.cancelButton);
        btnBack = findViewById(R.id.backButton);

        vaxxCardView = findViewById(R.id.vaxxCardView);

        // Getting instance and reference of Firebase storage.
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // To select vaccine card image from user's gallery.
        selectVaxxCard = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    filePath = uri;
                    try {

                        // Setting vaccine card image on image view using Bitmap.
                        Bitmap bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        filePath);
                        vaxxCardView.setImageBitmap(bitmap);
                    }

                    catch (IOException e) {
                        // Log the exception
                        e.printStackTrace();
                    }
                });

        btnVaxxCard.setOnClickListener(v -> {
            // Code based from Aws Rh (Youtube).
            // https://youtu.be/yHAAg-RdKDY
            if (Build.VERSION.SDK_INT >= 22) {
                // To check whether the user allowed access to gallery and request for permission if not.
                checkAndRequestPermission();
            } else {
                selectVaxxCard.launch("image/*");
            }
        });

        // To upload the selected vaccine card image of the user.
        btnUpload.setOnClickListener(v -> uploadVaxxCard());

        // To return to the user (driver) to edit profile page.
        btnCancel.setOnClickListener(v -> {

            Intent intent1 = new Intent(driverFullVerificationPage.this, driverEditProfilePageA.class);
            intent1.putExtra("getUserId", accountId);
            intent1.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent1.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent1.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent1.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent1.putExtra("getUserAccVerification", accountVerification);
            intent1.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent1);
        });

        // To return the user (commuter) to their profile page.
        btnBack.setOnClickListener(v -> {

            Intent intent2 = new Intent(driverFullVerificationPage.this, driverProfilePage.class);
            intent2.putExtra("getUserId", accountId);
            intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
            intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
            intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
            intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
            intent2.putExtra("getUserAccVerification", String.valueOf(accountVerification));
            intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

            startActivity(intent2);
        });
    }

    private void checkAndRequestPermission() {
        String text31 = getString(R.string.text32);
        if (ContextCompat.checkSelfPermission(driverFullVerificationPage.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(driverFullVerificationPage.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(driverFullVerificationPage.this,
                        text31, Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(driverFullVerificationPage.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            selectVaxxCard.launch("image/*");
        }
    }

    private void uploadVaxxCard() {

        // Get the user info from last intent.
        Bundle intent = getIntent().getExtras();
        String accountId = intent.getString("getUserId");
        String accountUsername = intent.getString("getUserUsername");
        String accountPassword = intent.getString("getUserPassword");
        String accountFullName = intent.getString("getUserFullName");
        String accountPhoneNumber = intent.getString("getUserPhoneNumber");
        String accountBalance = intent.getString("getUserBalance");

        String text23 = getString(R.string.text23);
        String text25 = getString(R.string.text25);
        String text136 = getString(R.string.text136);

        if (filePath != null) {

            // To show progressDialog while uploading.
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle(text23);
            progressDialog.show();

            // Defining the child of storageReference.
            StorageReference storageChild = storageReference.child("account-vaxxcard/" + UUID.randomUUID().toString());

            // Adding listeners on successful, ongoing, and failed image upload.
            // To show the percentage of uploading image.
            storageChild.putFile(filePath)
                    // Successful image upload.
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                // To get the image URL that will be added on the Firebase database.
                                storageChild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        // This sets the URL of uploaded image.
                                        String imageUrl = uri.toString();

                                        // This change the verification status of the user (driver) account.
                                        String newAccountVerification = "Fully Verified";

                                        // Add URL of vaccination card to Firebase Realtime Database.
                                        DatabaseReference verifyUser = firebaseDatabase.child(accountId);

                                        verifyUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {
                                                if(dataSnapshot.exists())
                                                {
                                                    verifyUser.child("accountVerification").setValue(newAccountVerification);
                                                    verifyUser.child("accountVaccinationCard").setValue(imageUrl);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        // Dialog for when the image was successfully uploaded.
                                        progressDialog.dismiss();

                                        // Redirect the user (commuter) to edit profile page with updated profile.
                                        Intent intent2 = new Intent(driverFullVerificationPage.this, driverEditProfilePageA.class);
                                        intent2.putExtra("getUserId", accountId);
                                        intent2.putExtra("getUserUsername", String.valueOf(accountUsername));
                                        intent2.putExtra("getUserPassword", String.valueOf(accountPassword));
                                        intent2.putExtra("getUserFullName", String.valueOf(accountFullName));
                                        intent2.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                                        intent2.putExtra("getUserAccVerification", newAccountVerification);
                                        intent2.putExtra("getUserBalance", String.valueOf(accountBalance));

                                        startActivity(intent2);

                                        Toast.makeText(driverFullVerificationPage.this,
                                                text136, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                            })
                    // Failed image upload.
                    .addOnFailureListener(e -> {
                        // Dialog for when the image failed to upload.
                        progressDialog.dismiss();
                        Toast.makeText(driverFullVerificationPage.this,
                                text25 + e.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    })
                    // Ongoing image upload.
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(text23 + " " + (int) progress + "%");
                            });
        }
    }
}
