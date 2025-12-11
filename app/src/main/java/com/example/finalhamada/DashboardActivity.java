package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * شاشة DashboardActivity
 * ----------------------------------------------
 * الشاشة الرئيسية للتطبيق تحتوي على:
 * - التقدم اليومي للمستخدم (سعرات، ماء، تمارين)
 * - القائمة الجانبية (Navigation Drawer)
 * - أزرار سريعة للوصول لشاشات أخرى (إضافة طعام، تمرين، عرض التقدم، الملف الشخصي)
 */
public class DashboardActivity extends AppCompatActivity {

    /** DrawerLayout للقائمة الجانبية */
    private DrawerLayout drawerLayout;

    /** NavigationView للقائمة الجانبية */
    private NavigationView navigationView;

    /** شريط الأدوات */
    private Toolbar toolbar;

    /** عناصر النصوص في الشاشة */
    private TextView tvTitle, tvDailyS, tvCalories, tvkcal, tvWater, tvGlasses, tvMins,
            tvWorkout, tvActions, tvAddFood, tvAddExercise, tvViewProgress, tvProfile;

    /** صور وأيقونات في الشاشة */
    private ImageView imageView, imageView1, imageView2, imageView3;

    /** ProgressBars لعرض التقدم */
    private ProgressBar progressBar, progressBar2, progressBar3;

    /**
     * تهيئة عناصر الشاشة وضبط Navigation Drawer والأزرار السريعة
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);

        /** Edge-to-Edge padding */
        final View mainLayout = findViewById(R.id.main);
        if (mainLayout != null) {
            mainLayout.setOnApplyWindowInsetsListener((v, insets) -> {
                v.setPadding(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom()
                );
                return insets;
            });
        }

        /** Toolbar + Navigation Drawer */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /** التعامل مع ضغط عناصر القائمة الجانبية */
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            return true;
        });

        /** ربط عناصر الواجهة بالكود */
        tvTitle = findViewById(R.id.tvTitle);
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        progressBar = findViewById(R.id.progressBar);
        tvkcal = findViewById(R.id.tvkcal);
        tvWater = findViewById(R.id.tvWater);
        progressBar2 = findViewById(R.id.progressBar2);
        tvGlasses = findViewById(R.id.tvGlasses);
        tvMins = findViewById(R.id.tvMins);
        progressBar3 = findViewById(R.id.progressBar3);
        tvWorkout = findViewById(R.id.tvWorkout);
        tvActions = findViewById(R.id.tvActions);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.ImageView2);
        imageView3 = findViewById(R.id.ImageView3);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvViewProgress = findViewById(R.id.tvViewProgress);
        tvProfile = findViewById(R.id.tvProfile);

        /** ضبط الأزرار السريعة للانتقال للشاشات الأخرى */
        tvAddFood.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AddFoods.class))
        );
        tvAddExercise.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Exercises.class))
        );
        tvViewProgress.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Progress.class))
        );
        tvProfile.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Profile.class))
        );
    }

    /**
     * عند الضغط على زر العودة:
     * يغلق القائمة الجانبية أولًا، ثم ينفذ الرجوع العادي
     */
    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
