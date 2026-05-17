package com.example.finalhamada;

import android.os.Bundle; // Bundle يستخدم لحفظ حالة الشاشة وإعادة استرجاعها.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.

import com.google.android.material.appbar.MaterialToolbar; // Toolbar بتصميم Material Design.

/**
 * SettingsActivity:
 * ---------------------------------------------------------
 * هذه الشاشة مسؤولة عن عرض صفحة الإعدادات داخل التطبيق.
 *
 * وظيفتها الأساسية:
 * - عرض Toolbar مع زر الرجوع.
 * - تحميل SettingsFragment داخل الشاشة.
 *
 * أهمية هذه الشاشة:
 * تعمل كحاوية (Container)
 * للـ SettingsFragment.
 *
 * بدونها لن يكون هناك مكان
 * لعرض شاشة الإعدادات.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * onCreate:
     * ---------------------------------------------------------
     * أول دالة تعمل عند فتح الشاشة.
     *
     * تقوم بـ:
     * - ربط Activity مع XML.
     * - تجهيز Toolbar.
     * - تفعيل زر الرجوع.
     * - تحميل SettingsFragment داخل الحاوية.
     *
     * بدونها لن يتم تجهيز الشاشة أو عرض Fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         * setContentView:
         * ---------------------------------------------------------
         * تربط الكود البرمجي
         * مع ملف التصميم XML.
         *
         * بدونها لن تظهر عناصر الشاشة.
         */
        setContentView(R.layout.activity_settings);

        /**
         * MaterialToolbar:
         * ---------------------------------------------------------
         * يمثل الشريط العلوي للشاشة.
         *
         * استخدمته لعرض:
         * - عنوان الشاشة.
         * - زر الرجوع.
         */
        MaterialToolbar toolbar =
                findViewById(R.id.toolbar_settings);

        /**
         * setSupportActionBar:
         * ---------------------------------------------------------
         * تجعل الـ Toolbar
         * يعمل كـ ActionBar رسمي للشاشة.
         *
         * بدونها قد يظهر Toolbar كشكل فقط
         * دون وظائف ActionBar.
         */
        setSupportActionBar(toolbar);

        /**
         * getSupportActionBar:
         * ---------------------------------------------------------
         * ترجع الـ ActionBar الحالي للشاشة.
         *
         * نتحقق أولًا أنه ليس null
         * حتى لا يحدث Crash.
         */
        if (getSupportActionBar() != null) {

            /**
             * setDisplayHomeAsUpEnabled:
             * ---------------------------------------------------------
             * تضيف زر الرجوع داخل Toolbar.
             *
             * بدونها لن يظهر سهم الرجوع.
             */
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /**
             * setDisplayShowHomeEnabled:
             * ---------------------------------------------------------
             * تفعّل عرض أيقونة الرجوع داخل Toolbar.
             */
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /**
         * setNavigationOnClickListener:
         * ---------------------------------------------------------
         * Listener ينتظر ضغط المستخدم
         * على زر الرجوع داخل Toolbar.
         *
         * عند الضغط:
         * يرجع للشاشة السابقة.
         *
         * بدون هذا Listener
         * قد يظهر زر الرجوع
         * لكنه لا يعمل.
         */
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        /**
         * savedInstanceState == null:
         * ---------------------------------------------------------
         * نتحقق إذا كانت هذه أول مرة
         * يتم فيها إنشاء الشاشة.
         *
         * الهدف:
         * منع إنشاء Fragment أكثر من مرة
         * عند دوران الشاشة أو إعادة إنشائها.
         */
        if (savedInstanceState == null) {

            /**
             * getSupportFragmentManager:
             * ---------------------------------------------------------
             * مسؤول عن إدارة الـ Fragments داخل Activity.
             *
             * من خلاله نستطيع:
             * - إضافة Fragment
             * - حذف Fragment
             * - استبدال Fragment
             */
            getSupportFragmentManager()

                    /**
                     * beginTransaction:
                     * ---------------------------------------------------------
                     * يبدأ عملية تعديل على الـ Fragments.
                     */
                    .beginTransaction()

                    /**
                     * replace:
                     * ---------------------------------------------------------
                     * تستبدل الحاوية settings_container
                     * بـ SettingsFragment.
                     *
                     * أي:
                     * عرض SettingsFragment داخل الشاشة.
                     *
                     * بدون replace
                     * لن يظهر الـ Fragment.
                     */
                    .replace(
                            R.id.settings_container,
                            new SettingsFragment()
                    )

                    /**
                     * commit:
                     * ---------------------------------------------------------
                     * ينفذ عملية الـ Fragment Transaction.
                     *
                     * بدون commit
                     * لن يتم تنفيذ أي تغيير.
                     */
                    .commit();
        }
    }
}