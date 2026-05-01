package com.example.finalhamada; // مكان الكلاس داخل المشروع

import android.Manifest; // يحتوي على أسماء الأذونات مثل POST_NOTIFICATIONS
import android.content.Intent; // يستخدم للتنقل بين الشاشات
import android.content.pm.PackageManager; // لفحص إذا الإذن مسموح أو لا
import android.os.Build; // لمعرفة إصدار Android
import android.os.Bundle; // لتخزين حالة الشاشة
import android.widget.TextView; // لعرض النصوص
import android.widget.Toast; // لإظهار رسالة قصيرة للمستخدم

import androidx.activity.result.ActivityResultLauncher; // الطريقة الحديثة لطلب الأذونات
import androidx.activity.result.contract.ActivityResultContracts; // عقود طلب الأذونات
import androidx.appcompat.app.ActionBarDrawerToggle; // ربط القائمة الجانبية
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات
import androidx.appcompat.widget.Toolbar; // الشريط العلوي
import androidx.core.content.ContextCompat; // لفحص الأذونات
import androidx.drawerlayout.widget.DrawerLayout; // القائمة الجانبية

import com.example.finalhamada.data.notifications.AlarmScheduler; // تشغيل AlarmManager
import com.example.finalhamada.data.notifications.NotificationHelper; // إنشاء Notification
import com.google.android.material.card.MaterialCardView; // تصميم البطاقات
import com.google.android.material.navigation.NavigationView; // القائمة الجانبية
import com.google.android.material.progressindicator.CircularProgressIndicator; // مؤشر دائري

public class DashboardActivity extends AppCompatActivity { // تعريف الشاشة

    private DrawerLayout drawerLayout; // حاوية القائمة الجانبية
    private NavigationView navigationView; // عناصر القائمة
    private Toolbar toolbar; // الشريط العلوي

    private MaterialCardView cardSteps, cardWater, cardSleep; // بطاقات البيانات
    private MaterialCardView btnAddFood, btnAddExercise; // أزرار الإضافة

    private CircularProgressIndicator circularCalories; // مؤشر السعرات

    private TextView tvStepsCount, tvStepsLabel; // نص الخطوات
    private TextView tvWaterGlasses, tvWaterLabel; // نص الماء
    private TextView tvSleepHours, tvSleepLabel; // نص النوم
    private TextView tvDailyS, tvCalories, tvMins; // الملخص

    /**
     * 🔥 الطريقة الحديثة لطلب إذن الإشعارات
     * يتم استدعاؤها عندما نحتاج الإذن
     */
    private final ActivityResultLauncher<String> requestNotificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                if (!isGranted) { // إذا المستخدم رفض الإذن
                    Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // تشغيل الإنشاء الأساسي

        setContentView(R.layout.activity_dashboard1); // ربط XML بالشاشة

        // --- إنشاء قناة الإشعارات ---
        NotificationHelper.createNotificationChannel(this);
        // بدونها الإشعارات لن تعمل على Android 8+

        // --- طلب إذن الإشعارات ---
        requestNotificationPermission();

        // --- تحديد وقت التذكير ---
        long triggerTime = System.currentTimeMillis() + 10000;
        // بعد 10 ثواني

        // --- تشغيل التذكير ---
        AlarmScheduler.scheduleReminder(
                this, // الشاشة الحالية
                triggerTime, // وقت التنفيذ
                "Fitness Reminder", // عنوان الإشعار
                "Don't forget to add your food or exercise today!" // نص الإشعار
        );

        // --- إعداد Toolbar ---
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- إعداد القائمة الجانبية ---
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // --- التعامل مع القائمة ---
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, Profile.class));
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // --- ربط العناصر ---
        cardSteps = findViewById(R.id.cardSteps);
        cardWater = findViewById(R.id.cardWater);
        cardSleep = findViewById(R.id.cardSleep);

        tvStepsCount = findViewById(R.id.tvStepsCount);
        tvStepsLabel = findViewById(R.id.tvStepsLabel);

        tvWaterGlasses = findViewById(R.id.tvWaterGlasses);
        tvWaterLabel = findViewById(R.id.tvWaterLabel);

        tvSleepHours = findViewById(R.id.tvSleepHours);
        tvSleepLabel = findViewById(R.id.tvSleepLabel);

        circularCalories = findViewById(R.id.circularCalories);
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        tvMins = findViewById(R.id.tvMins);

        circularCalories.setProgressCompat(45, true);

        // --- أزرار ---
        btnAddFood = findViewById(R.id.btnAddFood);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        btnAddFood.setOnClickListener(v ->
                startActivity(new Intent(this, FoodsActivtiy.class))
        );

        btnAddExercise.setOnClickListener(v ->
                startActivity(new Intent(this, Exercises.class))
        );
    }

    /**
     * 🔥 طلب إذن الإشعارات
     * يعمل فقط على Android 13+
     */
    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                // هنا يتم تشغيل popup طلب الإذن
                requestNotificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                );
            }
        }
    }

    /**
     * زر الرجوع
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