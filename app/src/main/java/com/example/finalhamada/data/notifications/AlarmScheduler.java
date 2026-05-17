package com.example.finalhamada.data.notifications; // package الخاص بالإشعارات

import android.app.AlarmManager; // مسؤول عن تشغيل التذكيرات في وقت محدد
import android.app.PendingIntent; // Intent مؤجل يتم تشغيله لاحقًا
import android.content.Context; // يمثل بيئة التطبيق الحالية
import android.content.Intent; // يستخدم لإرسال البيانات إلى AlarmReceiver

/**
 * ============================================================
 * AlarmScheduler
 * ============================================================
 *
 * هذا الكلاس مسؤول عن:
 * - تحديد وقت تشغيل التذكير.
 * - تشغيل AlarmManager.
 * - إرسال البيانات إلى AlarmReceiver.
 */
public class AlarmScheduler {

    /**
     * ============================================================
     * scheduleReminder
     * ============================================================
     *
     * هذه الدالة تقوم بجدولة Reminder.
     *
     * @param context Context الحالي للتطبيق.
     * @param triggerTimeMillis الوقت الذي سيعمل فيه التذكير.
     * @param title عنوان الإشعار.
     * @param message نص الإشعار.
     */
    public static void scheduleReminder(
            Context context,
            long triggerTimeMillis,
            String title,
            String message
    ) {

        /*
         * Intent:
         * يحدد أي BroadcastReceiver سيتم تشغيله.
         */
        Intent intent = new Intent(context, AlarmReceiver.class);

        // إرسال عنوان الإشعار
        intent.putExtra("title", title);

        // إرسال نص الإشعار
        intent.putExtra("message", message);

        /*
         * PendingIntent:
         * يسمح للنظام بتشغيل AlarmReceiver لاحقًا.
         */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                1001,
                intent,

                /*
                 * FLAG_UPDATE_CURRENT:
                 * إذا كان يوجد PendingIntent قديم
                 * يتم تحديثه.
                 *
                 * FLAG_IMMUTABLE:
                 * يمنع تعديل الـ Intent لاحقًا.
                 */
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        /*
         * AlarmManager:
         * مسؤول عن تشغيل التذكير في الوقت المحدد.
         */
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        /*
         * إذا كان AlarmManager موجود
         * يتم تحديد وقت تشغيل التذكير.
         */
        if (alarmManager != null) {

            /*
             * RTC_WAKEUP:
             * يشغل التذكير حسب وقت الجهاز الحقيقي
             * حتى إذا كان الجهاز نائمًا.
             */
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
            );
        }
    }
}