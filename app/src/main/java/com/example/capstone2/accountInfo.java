package com.example.capstone2;

import java.io.Serializable;

public class accountInfo implements Serializable {

    // Code based from GeeksforGeeks.
    // String variable for storing account key (ID).
    private String accountId;

    // String variable for storing account full name.
    String accountFullName;

    // String variable for storing account phone number.
    String accountPhoneNumber;

    // String variable for storing account (preferred) username.
    String accountUsername;

    // String variable for storing account (preferred) password.
    String accountPassword;

    // String variable for storing account type (commuter/driver).
    String accountType;

    // String variable for storing account verification (semi verified/fully verified).
    String accountVerification;

    // String variable for storing commuter type (student/PWD/senior citizen/regular).
    String commuterType;


    // Adding image url of account identification.
    // String variable for storing image url of account identification (driver).
    private String accountLicense;

    // String variable for storing image url of account identification (driver).
    private String accountValidId;

    // String variable for storing image url of account vaccination card (commuter/driver).
    private String accountVaccinationCard;


    // Account info when updated by user.
    // String variable for storing account (commuter) birthday.
    private String accountBirthday;

    // String variable for storing account (driver) plate number.
    private String accountPlateNumber;

    // String variable for storing account (commuter/driver) address.
    private String accountAddress;

    // String variable for storing account (commuter/driver) e-mail.
    private String accountEmail;


    // Account balance reflected on user homepage.
    // (Initial) Account balance (commuter).
    private String commuterBalance;

    // (Initial) Account balance (driver).
    private String driverBalance;

    // Current Date for the recorded income (driver).
    private String incomeCurrentDay;

    // Account daily balance (driver).
    private String driverDailyBalance;

    // Daily Boundary (driver).
    private String driverDailyBoundary;

    // Withdrawn Income (driver).
    private String driverWithdrawnIncome;

    // String variable for calling stored withdrawal amount of the driver.
    private String withdrawalRequestAmount;

    // String variable for calling stored withdrawal date of the driver.
    private String withdrawalRequestDate;

    // String variable for calling stored withdrawal method of the driver.
    private String withdrawalRequestMethod;


    // Transaction data.
    // String variable for storing transaction number (random number for reference).
    private String transactionReference;

    // String variable for storing transaction date (when the transaction was initiated by the user).
    private String transactionDate;


    // String variable for storing transaction route (where the user will be travelling).
    private String transactionRoute;

    // String variable for storing transaction amount (fare payment of the user).
    private String transactionAmount;


    // An empty constructor is required when using Firebase Realtime Database.
    public accountInfo() {

    }


    // Created getter and setter methods for all the variables.
    // Key (ID)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    // Full Name
    public String getAccountFullName() {
        return accountFullName;
    }

    public void setAccountFullName(String accountFullName) { this.accountFullName = accountFullName; }

    // Phone Number
    public String getAccountPhoneNumber() {
        return accountPhoneNumber;
    }

    public void setAccountPhoneNumber(String accountPhoneNumber) { this.accountPhoneNumber = accountPhoneNumber; }

    // Username
    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) { this.accountUsername = accountUsername; }

    // Password
    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) { this.accountPassword = accountPassword; }

    // Account Type
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType (String accountType) {
        this.accountType = accountType;
    }

    // Account Verification
    public String getAccountVerification() {
        return accountVerification;
    }

    public void setAccountVerification (String accountVerification) { this.accountVerification = accountVerification; }

    // Commuter Type
    public String getCommuterType() {
        return commuterType;
    }

    public void setCommuterType (String commuterType) {
        this.commuterType = commuterType;
    }

    // Account Identification (Driver)
    public String getAccountLicense() {
        return accountLicense;
    }

    public void setAccountLicense (String accountLicense) {
        this.accountLicense = accountLicense;
    }

    // Account Identification (Commuter)
    public String getAccountValidId() {
        return accountValidId;
    }

    public void setAccountValidId (String accountValidId) {
        this.accountValidId = accountValidId;
    }

    // Account Vaccination Card (Commuter/Driver)
    public String getAccountVaccinationCard() {
        return accountVaccinationCard;
    }

    public void setAccountVaccinationCard (String accountVaccinationCard) { this.accountVaccinationCard = accountVaccinationCard; }

    // Birthday (Commuter)
    public String getAccountBirthday() {
        return accountBirthday;
    }

    public void setAccountBirthday (String accountBirthday) { this.accountBirthday = accountBirthday; }

    // Plate Number (Driver)
    public String getAccountPlateNumber() {
        return accountPlateNumber;
    }

    public void setAccountPlateNumber (String accountPlateNumber) { this.accountPlateNumber = accountPlateNumber; }

    // Address
    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress (String accountAddress) {
        this.accountAddress = accountAddress;
    }

    // E-mail
    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail (String accountEmail) {
        this.accountEmail = accountEmail;
    }

    // Balance (Commuter)
    public String getCommuterBalance() {
        return commuterBalance;
    }

    public void setCommuterBalance (String commuterBalance) { this.commuterBalance = commuterBalance; }

    // Balance (Driver)
    public String getDriverBalance() {
        return driverBalance;
    }

    public void setDriverBalance (String driverBalance) { this.driverBalance = driverBalance; }

    // Daily Balance (Driver)
    public String getDriverDailyBalance() { return driverDailyBalance; }

    public void setDriverDailyBalance (String driverDailyBalance) { this.driverDailyBalance = driverDailyBalance; }

    // Current Date
    public String getIncomeCurrentDay() { return incomeCurrentDay; }

    public void setIncomeCurrentDay(String incomeCurrentDay) { this.incomeCurrentDay = incomeCurrentDay; }

    // Daily Boundary (Driver)
    public String getDriverDailyBoundary() {
        return driverDailyBoundary;
    }

    public void setDriverDailyBoundary(String driverDailyBoundary) { this.driverDailyBoundary = driverDailyBoundary; }

    // Withdrawn Income (Driver)
    public String getDriverWithdrawnIncome() {
        return driverWithdrawnIncome;
    }

    public void setDriverWithdrawnIncome(String driverWithdrawnIncome) { this.driverWithdrawnIncome = driverWithdrawnIncome; }

    // Withdrawal Amount (Driver)
    public String getWithdrawalRequestAmount() {
        return withdrawalRequestAmount;
    }

    public void setWithdrawalRequestAmount(String withdrawalRequestAmount) { this.withdrawalRequestAmount = withdrawalRequestAmount; }

    // Withdrawal Date (Driver)
    public String getWithdrawalRequestDate() {
        return withdrawalRequestDate;
    }

    public void setWithdrawalRequestDate(String withdrawalRequestDate) { this.withdrawalRequestDate = withdrawalRequestDate; }

    // Withdrawal Method (Driver)
    public String getWithdrawalRequestMethod() {
        return withdrawalRequestMethod;
    }

    public void setWithdrawalRequestMethod(String withdrawalRequestMethod) { this.withdrawalRequestMethod = withdrawalRequestMethod; }

    // Transaction Reference
    public String getTransactionReference() { return transactionReference; }

    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    // Transaction Date
    public String getTransactionDate() { return transactionDate; }

    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    // Transaction Route
    public String getTransactionRoute() { return transactionRoute; }

    public void setTransactionRoute(String transactionRoute) { this.transactionRoute = transactionRoute; }

    // Transaction Reference
    public String getTransactionAmount() { return transactionAmount; }

    public void setTransactionAmount(String transactionAmount) { this.transactionAmount = transactionAmount; }
}
