package com.example.finalhamada;

import android.app.Application; // الكلاس الأساسي لتعريف إعدادات التطبيق العامة.
import android.content.SharedPreferences; // أداة لتخزين واسترجاع إعدادات بسيطة (مثل وضع النهار/الليل).
import androidx.appcompat.app.AppCompatDelegate; // أداة للتحكم في سمات التطبيق (Themes).

/**
 * MyApp: كلاس التطبيق الرئيسي (Application Class).
 * ---------------------------------------------------------
 * يتم تشغيل هذا الكلاس مرة واحدة فقط عند بدء تشغيل التطبيق (قبل أي شاشة).
 * نستخدمه لتهيئة الإعدادات العامة التي يجب أن تطبق على جميع الشاشات،
 * مثل التحقق من تفضيلات المستخدم لوضع الرؤية (Dark Mode).
 */
public class MyApp extends Application {

    /**
     * دالة onCreate: يتم استدعاؤها عند تشغيل التطبيق لأول مرة.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // --- استعادة إعدادات المستخدم من الذاكرة الدائمة (SharedPreferences) ---
        // نفتح ملف الإعدادات المسمى "settings" بوضع الخصوصية (MODE_PRIVATE)
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        
        // قراءة قيمة "dark_mode"، وإذا لم توجد نعتبر القيمة الافتراضية "false" (وضع النهار)
        boolean darkMode = prefs.getBoolean("dark_mode", false);

        // --- تطبيق الوضع المختار على مستوى التطبيق بالكامل ---
        if (darkMode) {
            // تفعيل الوضع الليلي (Dark Mode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // تفعيل وضع النهار (Light Mode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
