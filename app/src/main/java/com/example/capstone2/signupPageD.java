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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class signupPageD extends AppCompatActivity {

    // Code based from GeeksforGeeks.
    // Code based from CodingSTUFF.
    // Code based from Tech projects.
    // Creating variables for buttons and image view.
    MaterialButton btnValidId;
    ImageView idView;
    Button btnUpload, btnBack;

    // Implementing URI to identify the source of the uploaded image.
    private Uri filePath;

    // Creating instance for Firebase Database and its reference.
    // For sending the uploaded image URL to Firebase Realtime Database.
    private final DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("accountInfo");

    // Creating a variable for the object class.
    accountInfo accountInfo;

    // Creating instance for Firebase Storage and its reference.
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private static final int PReqCode = 1;

    // Creating instance for selecting an image.
    ActivityResultLauncher<String> selectValidId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_paged);

        btnValidId = findViewById(R.id.validIdButton);
        btnUpload = findViewById(R.id.uploadButton);
        btnBack = findViewById(R.id.backButton);

        idView = findViewById(R.id.idView);

        // Getting instance and reference of Firebase storage.
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // To select license image from user's gallery.
        selectValidId = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    filePath = uri;
                    try {

                        // Setting license image on image view using Bitmap.
                        Bitmap bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        filePath);
                        idView.setImageBitmap(bitmap);
                    }

                    catch (IOException e) {
                        // Log the exception
                        e.printStackTrace();
                    }
                });

        btnValidId.setOnClickListener(v -> {
            // Code based from Aws Rh (Youtube).
            // https://youtu.be/yHAAg-RdKDY
            if (Build.VERSION.SDK_INT >= 22) {
                // To check whether the user allowed access to gallery and request for permission if not.
                checkAndRequestPermission();
            } else {
                selectValidId.launch("image/*");
            }
        });

        // To upload the selected license image of the user.
        btnUpload.setOnClickListener(v -> uploadValidId());

        // To return to the main sign-up page.
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(signupPageD.this, signupPageA.class);
            startActivity(intent);
        });
    }

    private void checkAndRequestPermission() {
        String text31 = getString(R.string.text32);
        if (ContextCompat.checkSelfPermission(signupPageD.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(signupPageD.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(signupPageD.this,
                        text31, Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(signupPageD.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            selectValidId.launch("image/*");
        }
    }

    private void uploadValidId() {

        // Get the user info from last intent.
        Bundle intent = getIntent().getExtras();
        String getUserFullName = intent.getString("getUserFullName");
        String getUserPhoneNumber = intent.getString("getUserPhoneNumber");
        String getUserPrefUsername = intent.getString("getUserPrefUsername");
        String getUserPrefPassword = intent.getString("getUserPrefPassword");
        String getInitialBal = intent.getString("getInitialBal");
        String getUserAccType = intent.getString("getUserAccType");
        String getUserComAccType = intent.getString("getUserComAccType");
        String getUserAccVerification = intent.getString("getUserAccVerification");

        String text23 = getString(R.string.text23);
        String text25 = getString(R.string.text25);
        String text28 = getString(R.string.text28);

        if (filePath != null) {

            // To show progressDialog while uploading.
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle(text23);
            progressDialog.show();

            // Defining the child of storageReference.
            StorageReference storageChild = storageReference.child("account-validid/" + UUID.randomUUID().toString());

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

                                        // Code based from Samuel Robert (StackOverflow)
                                        // This sets the data to the database reference which will be sent to Firebase.
                                        String accountId = firebaseDatabase.push().getKey();

                                        // This sets the URL of uploaded image.
                                        String imageUrl = uri.toString();

                                        // Add user info to database.
                                        accountInfo = new accountInfo();
                                        accountInfo.setAccountFullName(getUserFullName);
                                        accountInfo.setAccountPhoneNumber(getUserPhoneNumber);
                                        accountInfo.setAccountUsername(getUserPrefUsername);
                                        accountInfo.setAccountPassword(getUserPrefPassword);
                                        accountInfo.setCommuterBalance(getInitialBal);
                                        accountInfo.setAccountType(getUserAccType);
                                        accountInfo.setCommuterType(getUserComAccType);
                                        accountInfo.setAccountVerification(getUserAccVerification);

                                        accountInfo.setAccountBirthday("");
                                        accountInfo.setAccountAddress("");
                                        accountInfo.setAccountEmail("");

                                        accountInfo.setAccountValidId(imageUrl);
                                        accountInfo.setAccountId(accountId);
                                        firebaseDatabase.child(accountId).setValue(accountInfo);

                                        // Dialog for when the image was successfully uploaded.
                                        progressDialog.dismiss();
                                        Intent intent1 = new Intent(signupPageD.this, loginPage.class);
                                        startActivity(intent1);

                                        // Temporary
                                        Toast.makeText(signupPageD.this,
                                                text28, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                            })
                    // Failed image upload.
                    .addOnFailureListener(e -> {
                        // Dialog for when the image failed to upload.
                        progressDialog.dismiss();
                        Toast.makeText(signupPageD.this,
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
