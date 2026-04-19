package com.example.finalhamada.data.notifications;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        if (title == null) {
            title = "Fitness Reminder";
        }

        if (message == null) {
            message = "Don't forget to track your food or exercise today!";
        }

        NotificationHelper.createNotificationChannel(context);

        NotificationCompat.Builder builder =
                NotificationHelper.buildNotification(context, title, message);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager != null) {
            manager.notify(1001, builder.build());
        }
    }
}