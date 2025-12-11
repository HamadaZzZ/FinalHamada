package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * شاشة Progress
 * ----------------------------------------------
 * مسؤولة عن:
 * - عرض تقدم المستخدم الأسبوعي والشهري
 * - عرض اتجاه الوزن (Weight Trend) والسعرات المستهلكة
 * - عرض الرسوم البيانية المتعلقة بالتقدم
 * - التنقل بين شاشات التطبيق الرئيسية (Dashboard, Add Food, Add Exercise, Profile)
 */
public class Progress extends AppCompatActivity {

    /** العنصر الرئيسي للشاشة */
    private ViewGroup main;

    /** زر القائمة أو القائمة الجانبية */
    private ImageView btnMenu;

    /** عنوان الصفحة */
    private TextView tvProgress;

    /** Tabs للتبديل بين الوزن والسعرات */
    private TextView tabWeight;
    private TextView tabCalories;

    /** معلومات التقدم */
    private TextView tvWeightTrend;
    private TextView tvWeight;
    private TextView tvLast30Days2;
    private ImageView imgChart;
    private TextView tvWeeklySummary;
    private TextView tvYoureDoingGreat;
    private TextView tvYouveLost15LbsThisWeekKeepItUp;

    /** أزرار التنقل بين الشاشات */
    private TextView tvDashboard;
    private TextView tvAddFood;
    private TextView tvAddExercise;
    private TextView tvProfile;

    /**
     * تهيئة عناصر الشاشة وربطها بالكود
     * وضبط أحداث النقر للتنقل بين الشاشات الأخرى
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        /** ربط عناصر الواجهة */
        main = findViewById(R.id.main);
        btnMenu = findViewById(R.id.btnMenu);
        tvProgress = findViewById(R.id.tvProgress);
        tabWeight = findViewById(R.id.tabWeight);
        tabCalories = findViewById(R.id.tabCalories);
        tvWeightTrend = findViewById(R.id.tvWeightTrend);
        tvWeight = findViewById(R.id.tvWeight);
        tvLast30Days2 = findViewById(R.id.tvLast30Days2);
        imgChart = findViewById(R.id.imgChart);
        tvWeeklySummary = findViewById(R.id.tvWeeklySummary);
        tvYoureDoingGreat = findViewById(R.id.tvYoureDoingGreat);
        tvYouveLost15LbsThisWeekKeepItUp = findViewById(R.id.tvYouveLost15LbsThisWeekKeepItUp);
        tvDashboard = findViewById(R.id.tvDashboard);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvProfile = findViewById(R.id.tvProfile);

        /** التنقل بين الشاشات */
        tvDashboard.setOnClickListener(v -> startActivity(new Intent(Progress.this, DashboardActivity.class)));
        tvAddFood.setOnClickListener(v -> startActivity(new Intent(Progress.this, AddFoods.class)));
        tvAddExercise.setOnClickListener(v -> startActivity(new Intent(Progress.this, Exercises.class)));
        tvProfile.setOnClickListener(v -> startActivity(new Intent(Progress.this, Profile.class)));
    }
}
