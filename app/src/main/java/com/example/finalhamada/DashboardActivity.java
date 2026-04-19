package com.example.finalhamada;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent; // استيراد كلاس Intent لاستخدامه في التنقل بين الـ Activities المختلفة.
import android.os.Bundle; // كلاس Bundle يستخدم لتخزين الحالة السابقة للـ Activity.
import android.view.View; // كلاس View هو الأب لجميع عناصر واجهة المستخدم.
import android.widget.ImageView; // لعرض الصور في الواجهة.
import android.widget.TextView; // لعرض النصوص للمستخدم.

import androidx.appcompat.app.ActionBarDrawerToggle; // أداة لربط DrawerLayout بالـ Toolbar مع أيقونة الهامبرغر.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي الذي يجب أن ترث منه أي شاشة تدعم ميزات التوافق.
import androidx.appcompat.widget.Toolbar; // عنصر واجهة لعرض شريط الأدوات العلوي.
import androidx.drawerlayout.widget.DrawerLayout; // تخطيط يسمح بوجود قائمة جانبية تنسحب من حواف الشاشة.

import com.google.android.material.card.MaterialCardView; // بطاقة بتصميم Material توفر ظلالاً وزوايا دائرية.
import com.google.android.material.navigation.NavigationView; // واجهة المستخدم الخاصة بمحتويات القائمة الجانبية.
import com.google.android.material.progressindicator.CircularProgressIndicator; // مؤشر تقدم دائري يوضح الإنجاز.

/**
 * DashboardActivity: شاشة لوحة التحكم الرئيسية للتطبيق.
 * -------------------------------------------------------------------------
 * تقوم هذه الشاشة بعرض ملخص شامل لنشاط المستخدم اليومي، بما في ذلك:
 * - الخطوات، استهلاك الماء، وساعات النوم.
 * - ملخص السعرات الحرارية والتمارين.
 * - توفر قائمة جانبية للوصول السريع للملف الشخصي والإعدادات.
 */
public class DashboardActivity extends AppCompatActivity {

    // === تعريف عناصر الواجهة (UI Elements) ===

    // عناصر التحكم في القائمة الجانبية والشريط العلوي
    private DrawerLayout drawerLayout; // حاوية القائمة الجانبية
    private NavigationView navigationView; // محتويات القائمة (Home, Profile, etc.)
    private Toolbar toolbar; // شريط الأدوات العلوي

    // بطاقات عرض البيانات (Steps, Water, Sleep)
    private MaterialCardView cardSteps, cardWater, cardSleep;

    // أزرار الاختصارات (إضافة طعام، إضافة تمرين)
    private MaterialCardView btnAddFood, btnAddExercise;

    // مؤشر التقدم الدائري لعرض ملخص السعرات
    private CircularProgressIndicator circularCalories;

    // عناصر عرض النصوص (العدادات والعناوين)
    private TextView tvStepsCount, tvStepsLabel;
    private TextView tvWaterGlasses, tvWaterLabel;
    private TextView tvSleepHours, tvSleepLabel;
    private TextView tvDailyS, tvCalories, tvMins;

    /**
     * دالة onCreate: نقطة البداية عند تشغيل النشاط.
     * @param savedInstanceState لتخزين واسترجاع حالة الشاشة عند إعادة إنشائها.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ربط الكود بملف التصميم activity_dashboard1.xml
        setContentView(R.layout.activity_dashboard1);

        /*** 1. إعداد شريط الأدوات والقائمة الجانبية (Toolbar & Drawer) ***/
        toolbar = findViewById(R.id.toolbar); // ربط متغير الـ Toolbar بالـ ID الخاص به.
        setSupportActionBar(toolbar); // تعيين الـ Toolbar ليعمل كـ ActionBar رسمي للشاشة.

        drawerLayout = findViewById(R.id.drawer_layout); // ربط حاوية القائمة الجانبية.
        navigationView = findViewById(R.id.navigation_view); // ربط محتويات القائمة الجانبية.

        // إعداد أداة التبديل (Toggle) لفتح وإغلاق القائمة الجانبية عبر زر الهامبرغر.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open, // نص لوصف حالة الفتح (للمساعدة الصوتية)
                R.string.navigation_drawer_close  // نص لوصف حالة الإغلاق
        );
        drawerLayout.addDrawerListener(toggle); // إضافة المستمع لعمليات الفتح والإغلاق.
        toggle.syncState(); // مزامنة حالة الأيقونة مع حالة القائمة.

        // التعامل مع النقر على عناصر القائمة الجانبية.
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId(); // الحصول على الـ ID الخاص بالعنصر المضغط عليه.
            
            if (id == R.id.nav_settings) {
                // فتح شاشة الإعدادات
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
            } else if (id == R.id.nav_profile) {
                // فتح شاشة الملف الشخصي
                startActivity(new Intent(DashboardActivity.this, Profile.class));
            } else if (id == R.id.nav_home) {
                // البقاء في الشاشة الحالية
                drawerLayout.closeDrawers();
            }
            
            drawerLayout.closeDrawers(); // إغلاق القائمة الجانبية بعد الاختيار.
            return true;
        });

        /*** 2. ربط عناصر عرض البيانات (Status Cards) ***/
        cardSteps = findViewById(R.id.cardSteps);
        cardWater = findViewById(R.id.cardWater);
        cardSleep = findViewById(R.id.cardSleep);

        tvStepsCount = findViewById(R.id.tvStepsCount);
        tvStepsLabel = findViewById(R.id.tvStepsLabel);

        tvWaterGlasses = findViewById(R.id.tvWaterGlasses);
        tvWaterLabel = findViewById(R.id.tvWaterLabel);

        tvSleepHours = findViewById(R.id.tvSleepHours);
        tvSleepLabel = findViewById(R.id.tvSleepLabel);

        /*** 3. إعداد الملخص اليومي (Daily Summary) ***/
        circularCalories = findViewById(R.id.circularCalories);
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        tvMins = findViewById(R.id.tvMins);

        // تحديث نسبة التقدم في السعرات بشكل برمجي (مثال: 45%).
        circularCalories.setProgressCompat(45, true);

        /*** 4. إعداد أزرار الإجراءات السريعة (Actions) ***/
        btnAddFood = findViewById(R.id.btnAddFood);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        // عند الضغط على "Add Food": الانتقال لشاشة الأطعمة.
        btnAddFood.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, FoodsActivtiy.class))
        );

        // عند الضغط على "Add Exercise": الانتقال لشاشة التمارين.
        btnAddExercise.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Exercises.class))
        );

        /*** 5. التعامل مع النقر على البطاقات (اختياري للتفاصيل) ***/
        cardSteps.setOnClickListener(v -> {
            // يمكن إضافة كود هنا لفتح شاشة تفاصيل الخطوات.
        });

        cardWater.setOnClickListener(v -> {
            // فتح تفاصيل شرب الماء.
        });

        cardSleep.setOnClickListener(v -> {
            // فتح تفاصيل النوم.
        });
    }

    /**
     * التعامل مع زر الرجوع في الهاتف.
     * إذا كانت القائمة الجانبية مفتوحة، يتم إغلاقها بدلاً من الخروج من التطبيق.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
