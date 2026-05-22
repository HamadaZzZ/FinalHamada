package com.example.finalhamada.data.notifications; // package الخاص بكلاسات الإشعارات

import android.app.NotificationManager; // مسؤول عن عرض الإشعارات على الجهاز
import android.content.BroadcastReceiver; // كلاس يستقبل AlarmManager عند تشغيل الوقت المحدد
import android.content.Context; // يمثل بيئة التطبيق الحالية
import android.content.Intent; // يحتوي البيانات المرسلة من AlarmScheduler

import androidx.core.app.NotificationCompat; // لبناء الإشعار بطريقة حديثة

/**
 * ============================================================
 * AlarmReceiver
 * ============================================================
 *
 * هذا الكلاس يستقبل التذكير من AlarmManager
 * وعند وصول الوقت المحدد:
 * - ينشئ Notification
 * - ويعرضه للمستخدم.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * ============================================================
     * onReceive
     * ============================================================
     *
     * هذه الدالة تعمل تلقائيًا
     * عندما يصل AlarmManager إلى الوقت المحدد.
     *
     * @param context يمثل Context الحالي للتطبيق.
     * @param intent يحتوي البيانات القادمة من AlarmScheduler.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // قراءة عنوان الإشعار القادم من Intent
        String title = intent.getStringExtra("title");

        // قراءة نص الإشعار القادم من Intent
        String message = intent.getStringExtra("message");

        /*
         * إذا لم يصل عنوان للإشعار
         * نضع عنوان افتراضي.
         */
        if (title == null) {
            title = "Fitness Reminder";
        }

        /*
         * إذا لم يصل نص للإشعار
         * نضع رسالة افتراضية.
         */
        if (message == null) {
            message = "Don't forget to track your food or exercise today!";
        }

        /*
         * إنشاء Notification Channel.
         * ضروري في Android 8+.
         */
        NotificationHelper.createNotificationChannel(context);

        /*
         * بناء الإشعار باستخدام NotificationHelper.
         */
        NotificationCompat.Builder builder =
                NotificationHelper.buildNotification(context, title, message);

        /*
         * NotificationManager:
         * مسؤول عن عرض الإشعار على الجهاز.
         */
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        /*
         * إذا كان الـ manager موجود
         * اعرض الإشعار.
         */
        if (manager != null) {

            /*
             * notify:
             * الرقم 1001 هو id الإشعار.
             */
            manager.notify(1001, builder.build());// يطلع الاشعار على شاشه الجهاز
        }
    }
}