package com.example.finalhamada;

import android.content.Intent; // Intent يستخدم لفتح شاشة أو رابط خارجي.
import android.content.SharedPreferences; // لتخزين واسترجاع إعدادات بسيطة داخل الجهاز.
import android.net.Uri; // لتحويل رابط نصي إلى رابط يمكن فتحه.
import android.os.Bundle; // لحفظ واسترجاع حالة الـ Fragment.

import androidx.annotation.Nullable; // يعني أن القيمة يمكن أن تكون null.
import androidx.appcompat.app.AppCompatDelegate; // للتحكم في وضع التطبيق: Dark أو Light.
import androidx.preference.Preference; // عنصر إعداد عادي داخل شاشة الإعدادات.
import androidx.preference.PreferenceFragmentCompat; // Fragment خاص بعرض الإعدادات من ملف XML.
import androidx.preference.SwitchPreferenceCompat; // عنصر إعداد من نوع Switch مثل Dark Mode.

/**
 * SettingsFragment:
 * ---------------------------------------------------------
 * هذا Fragment مسؤول عن عرض إعدادات التطبيق.
 *
 * يحتوي على:
 * - Dark Mode Switch
 * - Logout Preference
 * - Privacy Policy Preference
 *
 * أهمية هذا الكلاس:
 * بدونه لن تظهر إعدادات التطبيق،
 * ولن يستطيع المستخدم تغيير Dark Mode أو فتح سياسة الخصوصية.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * onCreatePreferences:
     * ---------------------------------------------------------
     * هذه الدالة تعمل عند إنشاء شاشة الإعدادات.
     *
     * وظيفتها:
     * - تحميل ملف إعدادات XML.
     * - ربط عناصر الإعدادات بالكود.
     * - تجهيز أحداث الضغط والتغيير.
     *
     * بدونها لن تظهر عناصر الإعدادات داخل الشاشة.
     */
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        /**
         * setPreferencesFromResource:
         * ---------------------------------------------------------
         * تقوم بتحميل عناصر الإعدادات من ملف XML:
         * res/xml/root_preferences.xml
         *
         * هذا الملف يحتوي تعريف الإعدادات مثل:
         * dark_mode, logout, privacy_policy.
         *
         * بدون هذه الدالة سيظهر Fragment فارغًا.
         */
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        /**
         * findPreference("logout"):
         * ---------------------------------------------------------
         * تبحث عن عنصر إعداد اسمه logout
         * داخل ملف root_preferences.xml.
         *
         * نستخدمها حتى نتحكم بما يحدث
         * عند ضغط المستخدم على Logout.
         */
        Preference logoutPreference = findPreference("logout");

        /**
         * التحقق من أن logoutPreference ليس null.
         *
         * السبب:
         * إذا لم يكن المفتاح موجودًا داخل XML،
         * فإن findPreference ترجع null.
         *
         * بدون هذا الفحص قد يحدث Crash.
         */
        if (logoutPreference != null) {

            /**
             * setOnPreferenceClickListener:
             * ---------------------------------------------------------
             * Listener ينتظر ضغط المستخدم
             * على خيار Logout داخل الإعدادات.
             *
             * حاليًا الكود داخلها معلّق،
             * لكنه المكان الصحيح لتنفيذ تسجيل الخروج.
             */
            logoutPreference.setOnPreferenceClickListener(preference -> {

                // هنا يمكن تنفيذ تسجيل الخروج من Firebase لاحقًا.
                // FirebaseAuth.getInstance().signOut();
                // startActivity(new Intent(getActivity(), SignIn.class));
                // getActivity().finish();

                /**
                 * return true:
                 * يعني أننا تعاملنا مع حدث الضغط بنجاح.
                 */
                return true;
            });
        }

        /**
         * findPreference("privacy_policy"):
         * ---------------------------------------------------------
         * تبحث عن خيار سياسة الخصوصية داخل ملف الإعدادات XML.
         */
        Preference privacyPreference = findPreference("privacy_policy");

        if (privacyPreference != null) {

            /**
             * عند الضغط على Privacy Policy
             * يتم فتح رابط خارجي في المتصفح.
             *
             * Intent.ACTION_VIEW:
             * يستخدم لفتح رابط أو محتوى خارجي.
             *
             * Uri.parse:
             * يحول النص إلى رابط يمكن للنظام فتحه.
             */
            privacyPreference.setOnPreferenceClickListener(preference -> {

                startActivity(
                        new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://your-privacy-policy-url.com")
                        )
                );

                return true;
            });
        }

        /**
         * findPreference("dark_mode"):
         * ---------------------------------------------------------
         * تبحث عن Switch الخاص بالـ Dark Mode
         * داخل ملف root_preferences.xml.
         *
         * SwitchPreferenceCompat:
         * عنصر إعداد يسمح للمستخدم بتشغيل/إيقاف خيار معين.
         */
        SwitchPreferenceCompat darkModePreference = findPreference("dark_mode");

        if (darkModePreference != null) {

            /**
             * SharedPreferences:
             * ---------------------------------------------------------
             * نستخدمها لحفظ اختيار المستخدم
             * هل يريد Dark Mode أو Light Mode.
             *
             * الملف اسمه settings
             * وهو نفس الملف الذي يقرأ منه MyApp عند تشغيل التطبيق.
             */
            SharedPreferences prefs =
                    getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE);

            /**
             * قراءة الحالة المحفوظة للـ Dark Mode.
             *
             * إذا لم توجد قيمة محفوظة،
             * تكون القيمة الافتراضية false
             * أي Light Mode.
             */
            boolean darkMode = prefs.getBoolean("dark_mode", false);

            /**
             * setChecked:
             * ---------------------------------------------------------
             * يجعل وضع الـ Switch في الواجهة
             * مطابقًا للقيمة المحفوظة.
             *
             * إذا كانت darkMode = true
             * يظهر الـ Switch مفعلًا.
             */
            darkModePreference.setChecked(darkMode);

            /**
             * تطبيق الثيم الحالي مباشرة عند فتح الإعدادات.
             */
            if (darkMode) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );

            } else {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
            }

            /**
             * setOnPreferenceChangeListener:
             * ---------------------------------------------------------
             * Listener ينتظر تغيير المستخدم لقيمة الـ Switch.
             *
             * إذا غير المستخدم Dark Mode:
             * - نحفظ القيمة الجديدة.
             * - نطبق الثيم مباشرة.
             *
             * بدون هذا Listener:
             * الـ Switch قد يظهر،
             * لكن تغيير المستخدم لن يؤثر على التطبيق.
             */
            darkModePreference.setOnPreferenceChangeListener((preference, newValue) -> {

                /**
                 * newValue:
                 * ---------------------------------------------------------
                 * القيمة الجديدة القادمة من الـ Switch.
                 *
                 * تكون Object لذلك نحولها إلى Boolean.
                 */
                boolean isDark = (Boolean) newValue;

                /**
                 * Editor:
                 * ---------------------------------------------------------
                 * يستخدم لتعديل القيم داخل SharedPreferences.
                 *
                 * بدون Editor لا يمكننا حفظ القيمة الجديدة.
                 */
                SharedPreferences.Editor editor = prefs.edit();

                /**
                 * putBoolean:
                 * ---------------------------------------------------------
                 * تحفظ قيمة dark_mode الجديدة.
                 *
                 * المفتاح: dark_mode
                 * القيمة: true أو false
                 */
                editor.putBoolean("dark_mode", isDark);

                /**
                 * apply:
                 * ---------------------------------------------------------
                 * تطبق عملية الحفظ.
                 *
                 * بدون apply لن يتم حفظ التغيير.
                 */
                editor.apply();

                /**
                 * تطبيق الثيم حسب اختيار المستخدم.
                 */
                if (isDark) {

                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES
                    );

                } else {

                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                    );
                }

                /**
                 * return true:
                 * يسمح للـ Switch بتغيير حالته في الواجهة.
                 */
                return true;
            });
        }
    }
}