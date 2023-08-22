package com.example.capstone2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

// This page is where the user (commuter) will initiate fare payment using QR.
public class commuterPayPage extends AppCompatActivity {

    // Code based from mkyong (mkyong.com)
    // Calling default locale is a "common source of bugs" therefore this is called instead.
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private String qrCode;

    ImageButton btnBack;

    // Getting the instance of our Firebase database.
    // Getting the already existing reference from our Firebase database.
    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_paypage);

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

        String transactionRoute = intent.getString("getTransactionRoute");

        // Initiate camera view.
        previewView = findViewById(R.id.cameraView);

        btnBack = (ImageButton) (findViewById(R.id.backButton));

        // Initiate Camera X / ZXing
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        requestCamera();

        // To return the user (commuter) to homepage.
        btnBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(commuterPayPage.this, commuterIntPayPage.class);
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
    }

    // Code based from LearnToDroid
    /*
    "Denotes that the annotated element should only be called
    on the given API level or higher." — Abhay (StackOverflow)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    */
    // Get permission to access phone's camera.
    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(commuterPayPage.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    // Initialize phone's camera once permission is granted.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String text32 = getString(R.string.text33);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, text32, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // To use phone's camera.
    private void startCamera() {
        String text223 = getString(R.string.text221);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, text223 + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // To preview phone's camera.
    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {

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

        String transactionRoute = intent.getString("getTransactionRoute");

        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);
        //previewView.setImplementationMode(PreviewView.ImplementationMode.PERFORMANCE);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        //preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // To analyze the QR code using phone's camera.
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                // (Encrypted) Driver's ID where the commuter will pay their fare.
                qrCode = _qrCode;

                // Code by loucadufault (GitHub)
                // Code for reference ID.
                // Converted to base64 (?); must be 22 or 13 characters.
                String fareRefChar = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);

                // Get the current date to string.
                String transactionDateTime = DateFormat.getDateTimeInstance().format(new Date());
                String transactionDate = DateFormat.getDateInstance().format(new Date());

                // Fare amount.
                double fareAmount = 17.00;
                // Applicable fare discount.
                double discountAmount = fareAmount * 0.20;
                double discountedFareAmount = fareAmount - discountAmount;

                // Redirects the user (commuter) to payment confirmation page.
                Intent intent = new Intent(commuterPayPage.this, commuterConfirmPayPage.class);
                intent.putExtra("getUserId", String.valueOf(accountId));
                intent.putExtra("getUserUsername", String.valueOf(accountUsername));
                intent.putExtra("getUserPassword", String.valueOf(accountPassword));
                intent.putExtra("getUserFullName", String.valueOf(accountFullName));
                intent.putExtra("getUserPhoneNumber", String.valueOf(accountPhoneNumber));
                intent.putExtra("getUserCommuterType", accountCommuterType);
                intent.putExtra("getUserAccVerification", String.valueOf(accountVerification));
                intent.putExtra("getUserBalance", String.valueOf(accountBalance));

                // To check whether the user (commuter) that will be paying is eligible for fare discount.
                if (!accountCommuterType.equals("Regular")) {
                    intent.putExtra("getFareAmount", decimalFormat.format(discountedFareAmount));
                } else {
                    intent.putExtra("getFareAmount", decimalFormat.format(fareAmount));
                }

                intent.putExtra("getQrCode", String.valueOf(qrCode));
                intent.putExtra("getReferenceId", fareRefChar);
                intent.putExtra("getTransactionDateTime", transactionDateTime);
                intent.putExtra("getTransactionDate", transactionDate);
                intent.putExtra("getTransactionRoute", transactionRoute);

                startActivity(intent);
            }

            @Override
            public void qrCodeNotFound() {
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }
}