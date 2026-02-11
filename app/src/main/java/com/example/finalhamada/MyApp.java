    package com.example.finalhamada;

    import android.app.Application;
    import android.content.SharedPreferences;
    import androidx.appcompat.app.AppCompatDelegate;

    /**
     * ============================================================
     * MyApp
     * ============================================================
     * هذا الكلاس يمثل التطبيق كله (Application class).
     * - يُنفذ مرة واحدة عند تشغيل التطبيق.
     * - يقرأ حالة Dark Mode من SharedPreferences ويطبقها على كل التطبيق.
     * ============================================================
     */
    public class MyApp extends Application {

        @Override
        public void onCreate() {
            super.onCreate();

            // قراءة حالة Dark Mode من SharedPreferences
            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            boolean darkMode = prefs.getBoolean("dark_mode", false);

            // تطبيق الوضع على كل التطبيق
            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
