package com.syscache.pro;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import android.view.Gravity;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hardware se Unique Device ID nikalna
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Check karna ke app pehle se unlocked to nahi
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isUnlocked = prefs.getBoolean("isUnlocked", false);

        if (isUnlocked) {
            showUnlockedScreen();
            return;
        }

        // Lock Screen UI (Design)
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 100, 50, 50);
        layout.setBackgroundColor(Color.parseColor("#121212")); // Dark Premium Theme
        layout.setGravity(Gravity.CENTER);

        TextView title = new TextView(this);
        title.setText("SysCache Pro Locked");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);

        TextView idView = new TextView(this);
        idView.setText("Your Device ID:\n" + deviceId + "\n\nTo unlock, send this ID to Admin.");
        idView.setTextColor(Color.parseColor("#00FF00")); // Neon Green
        idView.setTextSize(16);
        idView.setGravity(Gravity.CENTER);
        idView.setPadding(0, 50, 0, 50);

        EditText keyInput = new EditText(this);
        keyInput.setHint("Enter Activation Key here");
        keyInput.setHintTextColor(Color.GRAY);
        keyInput.setTextColor(Color.WHITE);
        keyInput.setGravity(Gravity.CENTER);

        Button unlockBtn = new Button(this);
        unlockBtn.setText("UNLOCK APP");
        unlockBtn.setBackgroundColor(Color.parseColor("#00FF00"));
        unlockBtn.setTextColor(Color.BLACK);

        // Unlock Button Logic (Offline Algorithm)
        unlockBtn.setOnClickListener(v -> {
            String enteredKey = keyInput.getText().toString().trim();
            // Simple offline key generator logic (Aapke liye key hogi: deviceId ka hash code)
            String correctKey = String.valueOf(Math.abs(deviceId.hashCode())).substring(0, 6);

            if(enteredKey.equals(correctKey)) {
                Toast.makeText(this, "App Unlocked! System Service Active.", Toast.LENGTH_LONG).show();
                prefs.edit().putBoolean("isUnlocked", true).apply();
                showUnlockedScreen();
            } else {
                Toast.makeText(this, "Invalid Activation Key!", Toast.LENGTH_SHORT).show();
            }
        });

        layout.addView(title);
        layout.addView(idView);
        layout.addView(keyInput);
        layout.addView(unlockBtn);

        setContentView(layout);
    }

    private void showUnlockedScreen() {
        TextView successText = new TextView(this);
        successText.setText("SysCache Service is Running in Background.\nMonitoring System Cache...");
        successText.setTextColor(Color.parseColor("#00FF00"));
        successText.setTextSize(18);
        successText.setGravity(Gravity.CENTER);
        successText.setBackgroundColor(Color.parseColor("#121212"));
        setContentView(successText);
        
        // Yahan baad mein hum Shizuku aur Notification Service ko start karne ka code dalenge
    }
}
