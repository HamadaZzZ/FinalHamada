package com.example.finalhamada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * ============================================================
 * SettingsFragment
 * ============================================================
 * هذا Fragment مسؤول عن شاشة الإعدادات في التطبيق.
 *
 * مميزات:
 * - يستخدم PreferenceFragmentCompat لعرض قائمة الإعدادات (Preferences) بسهولة.
 * - يدعم التعامل مع الأحداث مثل:
 *   1️⃣ تسجيل الخروج (logout)
 *   2️⃣ فتح رابط سياسة الخصوصية (privacy_policy)
 *
 * سبب استخدام Fragment:
 * - Fragment هو وحدة واجهة قابلة لإعادة الاستخدام داخل Activity.
 * - يسمح بعرض شاشات متعددة في نفس Activity (مثلاً Activity تحتوي على Drawer أو Tabs).
 * - أكثر مرونة من استخدام Activity مستقلة لكل إعداد.
 *
 * ملاحظة:
 * - Preferences يتم تعريفها في ملف XML: res/xml/root_preferences.xml
 * - كل عنصر Preference في XML يمكن التعامل معه برمجياً داخل Fragment.
 * ============================================================
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * onCreatePreferences
     * -------------------
     * تُستدعى عند إنشاء Fragment لتهيئة قائمة الإعدادات.
     *
     * @param savedInstanceState حالة الحفظ السابقة إذا وجدت
     * @param rootKey المفتاح الجذري للعرض (يمكن أن يكون null)
     */
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        // ربط XML بالإعدادات
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // ---------------- التعامل مع زر تسجيل الخروج ----------------
        Preference logoutPreference = findPreference("logout");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                // مثال على كود تسجيل الخروج:
                // FirebaseAuth.getInstance().signOut();
                // Intent intent = new Intent(getActivity(), LoginActivity.class);
                // startActivity(intent);
                // getActivity().finish();

                return true; // true يعني تم التعامل مع الحدث
            });
        }

        // ---------------- التعامل مع زر سياسة الخصوصية ----------------
        Preference privacyPreference = findPreference("privacy_policy");
        if (privacyPreference != null) {
            privacyPreference.setOnPreferenceClickListener(preference -> {
                // فتح رابط سياسة الخصوصية في المتصفح
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://your-privacy-policy-url.com")
                );
                startActivity(browserIntent);
                return true;
            });
        }
    }
}
