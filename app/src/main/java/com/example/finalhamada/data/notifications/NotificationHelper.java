package com.example.finalhamada.data.notifications;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.finalhamada.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "fitness_reminder_channel";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Fitness Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Notifications for food and exercise reminders");

            NotificationManager manager =
                    context.getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public static NotificationCompat.Builder buildNotification(
            Context context,
            String title,
            String message
    ) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
    }
}