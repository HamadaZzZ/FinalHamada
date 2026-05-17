package com.example.finalhamada;

import android.app.Application; // الكلاس الأساسي للتطبيق، يعمل قبل أي Activity.
import android.content.SharedPreferences; // يستخدم لحفظ واسترجاع الإعدادات البسيطة.
import androidx.appcompat.app.AppCompatDelegate; // للتحكم بوضع التطبيق (Dark / Light).

/**
 * MyApp:
 * ---------------------------------------------------------
 * هذا الكلاس يمثل التطبيق بالكامل (Application Class).
 *
 * يتم تشغيله مرة واحدة فقط
 * عند فتح التطبيق لأول مرة،
 * قبل تشغيل أي شاشة.
 *
 * استخدمته لتطبيق الإعدادات العامة
 * على جميع شاشات التطبيق،
 * مثل:
 * Dark Mode / Light Mode.
 */
public class MyApp extends Application {

    /**
     * دالة onCreate:
     * ---------------------------------------------------------
     * أول دالة تعمل عند تشغيل التطبيق.
     *
     * استخدمتها من أجل:
     * - قراءة إعدادات المستخدم المحفوظة.
     * - معرفة إذا كان المستخدم اختار Dark Mode.
     * - تطبيق الثيم على التطبيق كامل.
     *
     * أهمية الدالة:
     * بدونها لن يتم تطبيق الإعدادات العامة
     * تلقائيًا عند تشغيل التطبيق.
     */
    @Override
    public void onCreate() {

        super.onCreate();

        /**
         * SharedPreferences:
         * ---------------------------------------------------------
         *  لحفظ واسترجاع الإعدادات البسيطة
         * داخل ذاكرة الجهاز.
         *
         * استخدمتها لحفظ:
         * حالة Dark Mode.
         *
         * أهمية استخدامها:
         * تجعل التطبيق يتذكر اختيار المستخدم
         * حتى بعد إغلاق التطبيق.
         */
        SharedPreferences prefs =
                getSharedPreferences("settings", MODE_PRIVATE);

        /**
         * getBoolean():
         * ---------------------------------------------------------
         * تقوم بقراءة قيمة dark_mode
         * من SharedPreferences.
         *
         * إذا لم تكن القيمة موجودة،
         * يتم استخدام false كقيمة افتراضية.
         */
        boolean darkMode =
                prefs.getBoolean("dark_mode", false);

        /**
         * التحقق إذا كان المستخدم
         * فعّل الوضع الليلي.
         */
        if (darkMode) {

            /**
             * setDefaultNightMode():
             * ---------------------------------------------------------
             * تقوم بتفعيل Dark Mode
             * على جميع شاشات التطبيق.
             *
             * MODE_NIGHT_YES:
             * يعني تشغيل الوضع الليلي.
             */
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );

        } else {

            /**
             * MODE_NIGHT_NO:
             * ---------------------------------------------------------
             * يعني تشغيل الوضع النهاري (Light Mode).
             */
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }
    }
}