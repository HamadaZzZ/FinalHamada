package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

/**
 * شاشة Profile
 * ----------------------------------------------
 * مسؤولة عن:
 * - عرض معلومات المستخدم الشخصية (الاسم، الطول، الوزن، مؤشر BMI)
 * - تعديل الملف الشخصي
 * - تسجيل الخروج
 * - التنقل بين شاشات التطبيق الرئيسية (Dashboard, Add Food, Add Exercise, Progress, Profile)
 */
public class Profile extends AppCompatActivity {

    /** العنصر الرئيسي للشاشة */
    private ViewGroup main;

    /** عنوان الصفحة */
    private TextView tvTitle;

    /** زر الإعدادات */
    private ImageView btnSettings;

    /** صورة الملف الشخصي */
    private ImageView imgProfile;

    /** اسم المستخدم */
    private TextView tvName;

    /** العنوان الفرعي */
    private TextView tvSubtitle;

    /** نص الطول */
    private TextView tvN57;
    private TextView tvHeight;

    /** نص الوزن */
    private TextView tvN135Lbs;
    private TextView tvWeight;

    /** مؤشر BMI */
    private TextView tvN225;
    private TextView tvBmi;

    /** زر تعديل الملف الشخصي */
    private Button btnEditProfile;

    /** زر تسجيل الخروج */
    private Button btnLogout;

    /** أزرار التنقل بين الشاشات */
    private TextView tvDashboard;
    private TextView tvAddFood;
    private TextView tvAddExercise;
    private TextView tvProgress;
    private TextView tvProfile;

    /**
     * تهيئة عناصر الواجهة وربطها بالكود
     * وضبط أزرار تعديل الملف الشخصي وتسجيل الخروج
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /** ربط عناصر الواجهة */
        main = findViewById(R.id.main);
        tvTitle = findViewById(R.id.tvTitle);
        btnSettings = findViewById(R.id.btnSettings);
        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvN57 = findViewById(R.id.tvN57);
        tvHeight = findViewById(R.id.tvHeight);
        tvN135Lbs = findViewById(R.id.tvN135Lbs);
        tvWeight = findViewById(R.id.tvWeight);
        tvN225 = findViewById(R.id.tvN225);
        tvBmi = findViewById(R.id.tvBmi);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        tvDashboard = findViewById(R.id.tvDashboard);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvProgress = findViewById(R.id.tvProgress);
        tvProfile = findViewById(R.id.tvProfile);

        /** زر تعديل الملف الشخصي */
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Profile.class);
            startActivity(intent);
        });

        /** زر تسجيل الخروج */
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, SplashScreen.class);
            startActivity(intent);
        });
    }
}
