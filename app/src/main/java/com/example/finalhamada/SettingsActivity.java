package com.example.finalhamada;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalhamada.R;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * ============================================================
 * SettingsActivity
 * ============================================================
 * هذه الـ Activity مسؤولة عن عرض شاشة الإعدادات بالكامل.
 *
 * المميزات:
 * 1️⃣ تحتوي على Toolbar مع زر الرجوع.
 * 2️⃣ تعرض SettingsFragment داخل الحاوية المخصصة له.
 *
 * العلاقة مع Fragment:
 * - هذه Activity تعمل كحاوية (Container) للـ Fragment.
 * - SettingsFragment هو الذي يحتوي على قائمة الإعدادات والخيارات.
 * - استخدام Fragment يجعل الإعدادات قابلة لإعادة الاستخدام في مكان آخر إذا احتجنا.
 *
 * مثال الاستخدام:
 * - عند الضغط على زر "Settings" في التطبيق، يتم فتح هذه الـ Activity.
 * - ثم يتم تحميل SettingsFragment داخلها.
 * ============================================================
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * onCreate
     * -------------------
     * تُستدعى عند إنشاء Activity لأول مرة.
     * - تربط واجهة المستخدم (layout)
     * - تضبط Toolbar مع زر الرجوع
     * - تقوم بتحميل SettingsFragment داخل الحاوية
     *
     * @param savedInstanceState حالة Activity المحفوظة إذا وجدت
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // =================== إعداد Toolbar ===================
        MaterialToolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        // إضافة زر الرجوع في Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // عند الضغط على زر الرجوع في Toolbar → نرجع للخلف
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // =================== تحميل Fragment ===================
        // إذا هذه أول مرة يتم إنشاء Activity (ليست إعادة استعادة)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }
}
