package com.example.finalhamada;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

/**
 * ============================================================
 * SettingsFragment
 * ============================================================
 * شاشة الإعدادات في التطبيق.
 * المميزات:
 * 1️⃣ تبديل Dark/Light Mode
 * 2️⃣ تسجيل الخروج (Logout)
 * 3️⃣ سياسة الخصوصية (Privacy Policy)
 *
 * ملاحظة:
 * - Preferences معرفة في res/xml/root_preferences.xml
 * - Dark Mode هنا يحدث التطبيق فورًا ويخزن الاختيار في SharedPreferences.
 * ============================================================
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // تسجيل الخروج
        Preference logoutPreference = findPreference("logout");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                // كود تسجيل الخروج
                // FirebaseAuth.getInstance().signOut();
                // startActivity(new Intent(getActivity(), LoginActivity.class));
                // getActivity().finish();
                return true;
            });
        }

        // سياسة الخصوصية
        Preference privacyPreference = findPreference("privacy_policy");
        if (privacyPreference != null) {
            privacyPreference.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://your-privacy-policy-url.com")));
                return true;
            });
        }

        // Dark Mode toggle
        SwitchPreferenceCompat darkModePreference = findPreference("dark_mode");
        if (darkModePreference != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE);

            // استعادة الوضع عند فتح الإعدادات
            boolean darkMode = prefs.getBoolean("dark_mode", false);
            darkModePreference.setChecked(darkMode);
            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Listener لتغيير الثيم فورًا وحفظ الاختيار
            darkModePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isDark = (Boolean) newValue;

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("dark_mode", isDark);
                editor.apply();

                if (isDark) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                return true;
            });
        }
    }
}
