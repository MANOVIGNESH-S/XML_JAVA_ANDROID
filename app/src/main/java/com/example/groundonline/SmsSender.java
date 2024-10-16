package com.example.groundonline;

import android.app.Activity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSender {

    private Activity activity;

    // Constructor to initialize the activity
    public SmsSender(Activity activity) {
        this.activity = activity;
    }

    // Method to send a single SMS
    public void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(activity, "SMS sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
            Log.d("SMS", "Successfully sent SMS to: " + phoneNumber);
        } catch (Exception e) {
            Log.e("SMS", "Failed to send SMS to: " + phoneNumber, e);
            Toast.makeText(activity, "Failed to send SMS to: " + phoneNumber, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to send SMS with retry logic
    public void sendSmsWithRetry(String phoneNumber, String message, int retries) {
        new Thread(() -> {
            for (int i = 0; i < retries; i++) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.d("SMS", "Successfully sent SMS to: " + phoneNumber);
                    activity.runOnUiThread(() ->
                            Toast.makeText(activity, "SMS sent to: " + phoneNumber, Toast.LENGTH_SHORT).show()
                    );
                    return; // Exit if successful
                } catch (Exception e) {
                    Log.e("SMS", "Attempt " + (i + 1) + " failed for: " + phoneNumber, e);
                    if (i == retries - 1) {
                        activity.runOnUiThread(() ->
                                Toast.makeText(activity, "Failed to send SMS to: " + phoneNumber, Toast.LENGTH_SHORT).show()
                        );
                    }
                    try {
                        Thread.sleep(2000); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt(); // Restore the interrupted status
                    }
                }
            }
        }).start();
    }
}
