/*
    Code from LearToDroid
    Github Link: https://github.com/learntodroid/AndroidQRCodeScanner/blob/master/app/src/main/java/com/learntodroid/androidqrcodescanner/QRCodeFoundListener.java

 */

package com.example.capstone2;

public interface QRCodeFoundListener {
    void onQRCodeFound(String qrCode);
    void qrCodeNotFound();
}
