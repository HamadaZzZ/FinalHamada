package com.example.finalhamada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // هنا يمكنك إضافة منطق برمجي للتعامل مع النقرات
        // مثال: التعامل مع زر تسجيل الخروج
        Preference logoutPreference = findPreference("logout");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                //  <<<<< ضع هنا كود تسجيل الخروج الخاص بك >>>>>
                // مثلا: FirebaseAuth.getInstance().signOut();
                // ثم الانتقال لشاشة تسجيل الدخول
                // Intent intent = new Intent(getActivity(), LoginActivity.class);
                // startActivity(intent);
                // getActivity().finish();
                return true;
            });
        }

        // مثال: فتح رابط سياسة الخصوصية
        Preference privacyPreference = findPreference("privacy_policy");
        if (privacyPreference != null) {
            privacyPreference.setOnPreferenceClickListener(preference -> {
                // استبدل الرابط برابط سياسة الخصوصية الخاصة بك
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://your-privacy-policy-url.com"));
                startActivity(browserIntent);
                return true;
            });
        }
    }
}
