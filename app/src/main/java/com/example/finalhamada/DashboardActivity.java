package com.example.finalhamada; // تعريف الحزمة (Package) التي ينتمي لها هذا الكلاس داخل المشروع

import android.annotation.SuppressLint; // لإخفاء تحذيرات معينة من Android Studio
import android.content.Intent; // Intent يُستخدم للتنقل بين الشاشات (Activities)
import android.os.Bundle; // Bundle يخزن بيانات حالة الشاشة عند إنشائها
import android.view.View; // View هو الكلاس الأساسي لكل عناصر الواجهة
import android.widget.ImageView; // ImageView لعرض الصور داخل الشاشة
import android.widget.ProgressBar; // ProgressBar لعرض شريط تقدم
import android.widget.TextView; // TextView لعرض النصوص

import androidx.appcompat.app.ActionBarDrawerToggle; // للتحكم بفتح/إغلاق القائمة الجانبية عبر زر الهامبرغر
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity
import androidx.appcompat.widget.Toolbar; // Toolbar هو الشريط العلوي المخصص
import androidx.drawerlayout.widget.DrawerLayout; // DrawerLayout هو الحاوية الرئيسية للقائمة الجانبية

import com.google.android.material.navigation.NavigationView; // NavigationView يمثل عناصر القائمة الجانبية

public class DashboardActivity extends AppCompatActivity { // تعريف Activity جديدة اسمها DashboardActivity

    private DrawerLayout drawerLayout; // DrawerLayout للتحكم بالقائمة الجانبية
    private NavigationView navigationView; // NavigationView يحتوي عناصر القائمة الجانبية
    private Toolbar toolbar; // Toolbar هو الشريط العلوي

    private TextView tvTitle, tvDailyS, tvCalories, tvkcal, tvWater, tvGlasses, tvMins,
            tvWorkout, tvActions, tvAddFood, tvAddExercise, tvViewProgress, tvProfile; // عناصر النصوص في الشاشة

    private ImageView imageView, imageView1, imageView2, imageView3; // صور وأيقونات داخل الشاشة

    private ProgressBar progressBar, progressBar2, progressBar3; // أشرطة التقدم لعرض الإنجاز

    @SuppressLint("MissingInflatedId") // إخفاء تحذير متعلق بالـ ID
    @Override
    protected void onCreate(Bundle savedInstanceState) { // دالة تُستدعى عند إنشاء الشاشة
        super.onCreate(savedInstanceState); // استدعاء دالة الأب لتهيئة الـ Activity
        setContentView(R.layout.activity_dashboard1); // ربط ملف XML الخاص بالشاشة

        final View mainLayout = findViewById(R.id.main); // جلب العنصر الرئيسي في الشاشة
        if (mainLayout != null) { // التأكد أن العنصر موجود
            mainLayout.setOnApplyWindowInsetsListener((v, insets) -> { // Listener لضبط الحواف (Edge-to-Edge)
                v.setPadding( // ضبط padding حسب شريط النظام
                        insets.getSystemWindowInsetLeft(), // المسافة اليسار
                        insets.getSystemWindowInsetTop(), // المسافة من الأعلى (Status bar)
                        insets.getSystemWindowInsetRight(), // المسافة اليمين
                        insets.getSystemWindowInsetBottom() // المسافة من الأسفل (Navigation bar)
                );
                return insets; // إعادة القيم بعد التعديل
            });
        }

        toolbar = findViewById(R.id.toolbar); // ربط الـ Toolbar من XML
        setSupportActionBar(toolbar); // تعيين Toolbar كشريط علوي رسمي للشاشة

        drawerLayout = findViewById(R.id.drawer_layout); // ربط DrawerLayout
        navigationView = findViewById(R.id.navigation_view); // ربط NavigationView

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( // إنشاء زر تحكم لفتح/إغلاق القائمة
                this, // السياق الحالي
                drawerLayout, // الـ DrawerLayout المرتبط
                toolbar, // الـ Toolbar الذي يحتوي زر الهامبرغر
                R.string.navigation_drawer_open, // نص عند فتح القائمة
                R.string.navigation_drawer_close // نص عند إغلاق القائمة
        );

        drawerLayout.addDrawerListener(toggle); // إضافة الـ Toggle كمستمع لحالة الـ Drawer
        toggle.syncState(); // مزامنة حالة الزر مع حالة القائمة

        navigationView.setNavigationItemSelectedListener(item -> { // Listener عند الضغط على عنصر من القائمة الجانبية
            int id = item.getItemId(); // جلب ID العنصر الذي تم الضغط عليه

            if (id == R.id.nav_settings) { // إذا كان العنصر هو Settings
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class)); // فتح شاشة Settings
            }

            drawerLayout.closeDrawers(); // إغلاق القائمة بعد الضغط
            return true; // إرجاع true لإخبار النظام أن الحدث تم التعامل معه
        });

        tvTitle = findViewById(R.id.tvTitle); // ربط عنوان الشاشة
        tvDailyS = findViewById(R.id.tvDailyS); // ربط نص Daily Summary
        tvCalories = findViewById(R.id.tvCalories); // ربط نص السعرات
        progressBar = findViewById(R.id.progressBar); // ربط شريط التقدم الأول
        tvkcal = findViewById(R.id.tvkcal); // ربط نص kcal
        tvWater = findViewById(R.id.tvWater); // ربط نص الماء
        progressBar2 = findViewById(R.id.progressBar2); // ربط شريط التقدم الثاني
        tvGlasses = findViewById(R.id.tvGlasses); // ربط نص عدد الأكواب
        tvMins = findViewById(R.id.tvMins); // ربط نص عدد الدقائق
        progressBar3 = findViewById(R.id.progressBar3); // ربط شريط التقدم الثالث
        tvWorkout = findViewById(R.id.tvWorkout); // ربط نص التمارين
        tvActions = findViewById(R.id.tvActions); // ربط نص الإجراءات

        imageView = findViewById(R.id.imageView); // ربط صورة
        imageView1 = findViewById(R.id.imageView1); // ربط صورة
        imageView2 = findViewById(R.id.imageView2); // ربط صورة
        imageView3 = findViewById(R.id.imageView3); // ربط صورة

        tvAddFood = findViewById(R.id.tvAddFood); // ربط زر إضافة طعام
        tvAddExercise = findViewById(R.id.tvAddExercise); // ربط زر إضافة تمرين
        tvViewProgress = findViewById(R.id.tvViewProgress); // ربط زر عرض التقدم
        tvProfile = findViewById(R.id.tvProfile); // ربط زر الملف الشخصي

        tvAddFood.setOnClickListener(v -> // عند الضغط على Add Food
                startActivity(new Intent(DashboardActivity.this, FoodsActivtiy.class)) // فتح شاشة Foods
        );

        tvAddExercise.setOnClickListener(v -> // عند الضغط على Add Exercise
                startActivity(new Intent(DashboardActivity.this, Exercises.class)) // فتح شاشة Exercises
        );

        tvViewProgress.setOnClickListener(v -> // عند الضغط على View Progress
                startActivity(new Intent(DashboardActivity.this, Progress.class)) // فتح شاشة Progress
        );

        tvProfile.setOnClickListener(v -> // عند الضغط على Profile
                startActivity(new Intent(DashboardActivity.this, Profile.class)) // فتح شاشة Profile
        );
    }

    @SuppressLint("GestureBackNavigation") // إخفاء تحذير متعلق بزر الرجوع الحديث
    @Override
    public void onBackPressed() { // يتم استدعاؤها عند الضغط على زر الرجوع
        if (drawerLayout.isDrawerOpen(navigationView)) { // إذا كانت القائمة الجانبية مفتوحة
            drawerLayout.closeDrawers(); // يتم إغلاقها فقط
        } else {
            super.onBackPressed(); // غير ذلك يتم تنفيذ الرجوع الطبيعي للشاشة السابقة
        }
    }
}