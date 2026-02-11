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
 * ============================================================
 * DashboardActivity
 * ============================================================
 * الشاشة الرئيسية للتطبيق بعد تسجيل الدخول.
 *
 * الوظائف الرئيسية:
 * 1️⃣ عرض التقدم اليومي للمستخدم (سعرات حرارية، ماء، تمارين)
 * 2️⃣ عرض ProgressBars لقياس الإنجاز اليومي
 * 3️⃣ قائمة جانبية Navigation Drawer للوصول للإعدادات
 * 4️⃣ أزرار سريعة للوصول لشاشات Foods, Exercises, Progress, Profile
 *
 * ملاحظات:
 * - تم تفعيل Edge-to-Edge Layout لتجنب قص المحتوى خلف شريط النظام.
 * - يتم التحكم بزر الرجوع لإغلاق القائمة الجانبية إذا كانت مفتوحة.
 * ============================================================
 */
public class DashboardActivity extends AppCompatActivity {

    /** DrawerLayout للقائمة الجانبية */
    private DrawerLayout drawerLayout;

    /** NavigationView للقائمة الجانبية */
    private NavigationView navigationView;

    /** Toolbar أعلى الشاشة */
    private Toolbar toolbar;

    /** عناصر النصوص في الشاشة */
    private TextView tvTitle, tvDailyS, tvCalories, tvkcal, tvWater, tvGlasses, tvMins,
            tvWorkout, tvActions, tvAddFood, tvAddExercise, tvViewProgress, tvProfile;

    /** صور وأيقونات في الشاشة */
    private ImageView imageView, imageView1, imageView2, imageView3;

    /** ProgressBars لعرض التقدم اليومي */
    private ProgressBar progressBar, progressBar2, progressBar3;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة
     * تقوم بـ:
     * 1️⃣ ربط عناصر الواجهة (TextViews, ImageViews, ProgressBars)
     * 2️⃣ تفعيل Edge-to-Edge padding
     * 3️⃣ إعداد Toolbar و Navigation Drawer
     * 4️⃣ ربط عناصر القائمة الجانبية مثل Settings
     * 5️⃣ إعداد أزرار الوصول السريع للشاشات الأخرى
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);

        // ====== Edge-to-Edge padding ======
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

        // ====== Toolbar + Navigation Drawer ======
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

        /**
         * التعامل مع ضغط عناصر القائمة الجانبية
         * عند الضغط على Settings يتم فتح SettingsActivity
         */
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_settings) {
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // ====== ربط عناصر الواجهة ======
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
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);

        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvViewProgress = findViewById(R.id.tvViewProgress);
        tvProfile = findViewById(R.id.tvProfile);

        // ====== أزرار الوصول السريع ======
        tvAddFood.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, FoodsActivtiy.class))
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
     * onBackPressed
     * --------------------------------------------------
     * عند الضغط على زر الرجوع:
     * 1️⃣ إذا كانت القائمة الجانبية مفتوحة يتم إغلاقها
     * 2️⃣ خلاف ذلك يتم تنفيذ السلوك الافتراضي للرجوع
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
