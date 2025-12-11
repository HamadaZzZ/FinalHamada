package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * شاشة AddFoods
 * ----------------------------------------------
 * مسؤولة عن:
 * - عرض الأطعمة المختلفة
 * - البحث عن الأطعمة
 * - عرض السعرات الحالية والهدف اليومي
 * - التنقل بين الشاشات الرئيسية مثل Dashboard، YourGoal، Exercises، Progress
 */
public class AddFoods extends AppCompatActivity {

    /** عنوان الشاشة */
    private TextView tvTile;

    /** حقل البحث عن الطعام */
    private EditText etSearchFood;

    /** عرض الهدف اليومي للمستخدم */
    private TextView tvTdGoal;

    /** عرض عدد السعرات المأخوذة */
    private TextView tvCalrories;

    /** شريط تقدم السعرات */
    private ProgressBar progressCalories;

    /** نص سعرات حرارية */
    private TextView tvCAL;

    /** نص الأطعمة الحديثة */
    private TextView tvRecent;

    /** نص تفاح */
    private TextView tvApple;

    /** سعرات تفاح */
    private TextView tvcalApple;

    /** نص موز */
    private TextView tvBanana;

    /** سعرات موز */
    private TextView tvcalBanana;

    /** نص برتقال */
    private TextView tvOrange;

    /** سعرات برتقال */
    private TextView tvcalOrange;

    /** نص بروكلي */
    private TextView tvBroccoli;

    /** سعرات بروكلي */
    private TextView tvcalBroccoli;

    /** زر الانتقال للصفحة الرئيسية */
    private TextView tvHome;

    /** زر الانتقال لشاشة الأهداف */
    private TextView tvGoals;

    /** زر تبويب الأطعمة (نشط في هذه الشاشة) */
    private TextView tvFood;

    /** زر الانتقال لشاشة التمارين */
    private TextView tvExcercises;

    /** زر الانتقال لشاشة التقدم */
    private TextView tvProgress;

    /**
     * تهيئة عناصر الواجهة وربطها بالكود
     * وضبط التنقل بين الصفحات عند الضغط على الأزرار
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foods);

        /** تطبيق Edge-to-Edge */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** ربط عناصر الواجهة بالكود */
        tvTile = findViewById(R.id.tvTile);
        etSearchFood = findViewById(R.id.etSearchFood);
        tvTdGoal = findViewById(R.id.tvTdGoal);
        tvCalrories = findViewById(R.id.tvCalrories);
        progressCalories = findViewById(R.id.progressCalories);
        tvCAL = findViewById(R.id.tvCAL);
        tvRecent = findViewById(R.id.tvRecent);
        tvApple = findViewById(R.id.tvApple);
        tvcalApple = findViewById(R.id.tvcalApple);
        tvBanana = findViewById(R.id.tvBanana);
        tvcalBanana = findViewById(R.id.tvcalBanana);
        tvOrange = findViewById(R.id.tvOrange);
        tvcalOrange = findViewById(R.id.tvcalOrange);
        tvBroccoli = findViewById(R.id.tvBroccoli);
        tvcalBroccoli = findViewById(R.id.tvcalBroccoli);
        tvHome = findViewById(R.id.tvHome);
        tvGoals = findViewById(R.id.tvGoals);
        tvFood = findViewById(R.id.tvFood);
        tvExcercises = findViewById(R.id.tvExcercises);
        tvProgress = findViewById(R.id.tvProgress);

        /** إعداد التنقل عند الضغط على الأزرار */
        tvHome.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, DashboardActivity.class)));

        tvGoals.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, YourGoal.class)));

        tvExcercises.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, Exercises.class)));

        tvProgress.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, Progress.class)));
    }
}
