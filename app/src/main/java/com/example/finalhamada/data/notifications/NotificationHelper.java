package com.example.finalhamada.data.notifications; // package الخاص بالإشعارات

import android.app.NotificationChannel; // يمثل Notification Channel
import android.app.NotificationManager; // مسؤول عن إنشاء وعرض الإشعارات
import android.content.Context; // يمثل Context الحالي للتطبيق
import android.os.Build; // لمعرفة إصدار Android

import androidx.core.app.NotificationCompat; // لبناء Notification حديث

import com.example.finalhamada.R; // للوصول إلى Resources مثل الأيقونات

/**
 * ============================================================
 * NotificationHelper
 * ============================================================
 *
 * هذا الكلاس مسؤول عن:
 * - إنشاء Notification Channel.
 * - بناء Notification جاهز للعرض.
 */
public class NotificationHelper {

    /**
     * CHANNEL_ID:
     * معرف القناة الخاصة بالإشعارات.
     */
    public static final String CHANNEL_ID = "fitness_reminder_channel";

    /**
     * ============================================================
     * createNotificationChannel
     * ============================================================
     *
     * هذه الدالة تنشئ قناة الإشعارات.
     * ضرورية في Android 8+.
     *
     * @param context Context الحالي للتطبيق.
     */
    public static void createNotificationChannel(Context context) {

        /*
         * Android O = Android 8
         * Notification Channels تعمل فقط من Android 8+.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /*
             * NotificationChannel:
             * يمثل القناة الخاصة بالإشعارات.
             */
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Fitness Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );

            /*
             * وصف القناة.
             */
            channel.setDescription("Notifications for food and exercise reminders");

            /*
             * NotificationManager:
             * مسؤول عن إنشاء القناة.
             */
            NotificationManager manager =
                    context.getSystemService(NotificationManager.class);

            /*
             * إذا كان manager موجود
             * يتم إنشاء القناة.
             */
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * ============================================================
     * buildNotification
     * ============================================================
     *
     * هذه الدالة تبني Notification جاهز.
     *
     * @param context Context الحالي.
     * @param title عنوان الإشعار.
     * @param message نص الإشعار.
     *
     * @return NotificationCompat.Builder
     */
    public static NotificationCompat.Builder buildNotification(
            Context context,
            String title,
            String message
    ) {

        /*
         * NotificationCompat.Builder:
         * يستخدم لبناء الإشعار.
         */
        return new NotificationCompat.Builder(context, CHANNEL_ID)

                /*
                 * أيقونة الإشعار.
                 */
                .setSmallIcon(R.drawable.ic_launcher_foreground)

                /*
                 * عنوان الإشعار.
                 */
                .setContentTitle(title)

                /*
                 * نص الإشعار.
                 */
                .setContentText(message)

                /*
                 * أهمية الإشعار.
                 */
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                /*
                 * يغلق الإشعار تلقائيًا
                 * عند ضغط المستخدم عليه.
                 */
                .setAutoCancel(true);
    }
}