package com.example.finalhamada;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent; // للتنقل بين الشاشات
import android.os.Bundle; // لتخزين حالة الـ Activity
import android.view.View; // للوصول لعناصر الواجهة والتحكم فيها
import android.widget.ImageView; // لعرض أيقونات وصور
import android.widget.TextView; // لعرض النصوص

import androidx.appcompat.app.ActionBarDrawerToggle; // للتحكم بزر الهامبرغر للقائمة الجانبية
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity
import androidx.appcompat.widget.Toolbar; // Toolbar هو الشريط العلوي
import androidx.drawerlayout.widget.DrawerLayout; // DrawerLayout هو الحاوية للقائمة الجانبية

import com.google.android.material.card.MaterialCardView; // لبطاقات Material
import com.google.android.material.navigation.NavigationView; // لواجهة القائمة الجانبية
import com.google.android.material.progressindicator.CircularProgressIndicator; // شريط تقدم دائري

/**
 * DashboardActivity
 * هذه الشاشة تعرض:
 * - Cards لمتابعة Steps، Water، Sleep
 * - ملخص يومي للسعرات والتمارين
 * - أزرار لإضافة طعام وتمارين
 * - قائمة جانبية Drawer مع Toolbar
 */
public class DashboardActivity extends AppCompatActivity {

    // عناصر الـ Drawer وToolbar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    // Cards Steps, Water, Sleep
    private MaterialCardView cardSteps, cardWater, cardSleep;

    // أزرار Add Food و Add Exercise
    private MaterialCardView btnAddFood, btnAddExercise;

    // Circular Progress للـ Daily Summary
    private CircularProgressIndicator circularCalories;

    // نصوص العرض
    private TextView tvStepsCount, tvStepsLabel;
    private TextView tvWaterGlasses, tvWaterLabel;
    private TextView tvSleepHours, tvSleepLabel;
    private TextView tvDailyS, tvCalories, tvMins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1); // ربط XML بالشاشة

        /*** Toolbar و Drawer ***/
        toolbar = findViewById(R.id.toolbar); // ربط Toolbar
        setSupportActionBar(toolbar); // تعيين Toolbar كشريط علوي رسمي

        drawerLayout = findViewById(R.id.drawer_layout); // ربط DrawerLayout
        navigationView = findViewById(R.id.navigation_view); // ربط NavigationView

        // زر الهامبرغر لفتح/إغلاق القائمة الجانبية
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // معالجة الضغط على عناصر Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_settings) {
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(DashboardActivity.this, Profile.class));
            } else if (id == R.id.nav_home) {
                drawerLayout.closeDrawers(); // يبقى في الصفحة الحالية
            }
            drawerLayout.closeDrawers();
            return true;
        });

        /*** ربط Cards Steps, Water, Sleep ***/
        cardSteps = findViewById(R.id.cardSteps);
        cardWater = findViewById(R.id.cardWater);
        cardSleep = findViewById(R.id.cardSleep);

        tvStepsCount = findViewById(R.id.tvStepsCount);
        tvStepsLabel = findViewById(R.id.tvStepsLabel);

        tvWaterGlasses = findViewById(R.id.tvWaterGlasses);
        tvWaterLabel = findViewById(R.id.tvWaterLabel);

        tvSleepHours = findViewById(R.id.tvSleepHours);
        tvSleepLabel = findViewById(R.id.tvSleepLabel);

        /*** Daily Summary ***/
        circularCalories = findViewById(R.id.circularCalories);
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        tvMins = findViewById(R.id.tvMins);

        // مثال: تعيين نسبة التقدم في السعرات
        circularCalories.setProgressCompat(45, true); // 45% كمثال

        /*** أزرار Actions ***/
        btnAddFood = findViewById(R.id.btnAddFood);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        btnAddFood.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, FoodsActivtiy.class))
        );

        btnAddExercise.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Exercises.class))
        );

        /*** يمكن إضافة OnClick لكل Card لو حبيت مثلا التفاصيل ***/
        cardSteps.setOnClickListener(v -> {
            // مثال: فتح صفحة تفاصيل Steps
            // startActivity(new Intent(DashboardActivity.this, StepsDetail.class));
        });

        cardWater.setOnClickListener(v -> {
            // مثال: فتح صفحة تفاصيل Water
        });

        cardSleep.setOnClickListener(v -> {
            // مثال: فتح صفحة تفاصيل Sleep
        });
    }

    /*** التعامل مع زر الرجوع عند فتح Drawer ***/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}