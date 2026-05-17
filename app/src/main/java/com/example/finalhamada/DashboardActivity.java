package com.example.finalhamada;

import android.Manifest;
import android.content.Intent; // يستخدم للتنقل بين الشاشات المختلفة داخل التطبيق.
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle; // يستخدم لحفظ واسترجاع حالة الشاشة عند إنشائها.
import android.widget.Button; // يستخدم للتحكم بزر AI Coach.
import android.widget.TextView; // يستخدم لعرض النصوص داخل الشاشة.
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle; // يربط القائمة الجانبية مع زر الهامبرغر في الـ Toolbar.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity حديثة.
import androidx.appcompat.widget.Toolbar; // شريط الأدوات العلوي.
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout; // التخطيط الرئيسي للقائمة الجانبية.

import com.example.finalhamada.data.GeminiHelper; // كلاس مساعد لإرسال الطلبات إلى Gemini AI.
import com.example.finalhamada.data.ResponseCallBack; // واجهة لاستقبال رد Gemini عند النجاح أو الخطأ.
import com.example.finalhamada.data.notifications.AlarmScheduler;
import com.example.finalhamada.data.notifications.NotificationHelper;
import com.google.android.material.card.MaterialCardView; // كرت من Material Design.
import com.google.android.material.navigation.NavigationView; // القائمة الجانبية.
import com.google.android.material.progressindicator.CircularProgressIndicator; // مؤشر تقدم دائري.

/**
 * ============================================================
 * DashboardActivity
 * ============================================================
 *
 DashboardActivity
 هي الشاشة الرئيسية في التطبيق،
 وتعرض ملخصًا عامًا لنشاط المستخدم
 مثل الخطوات والماء والسعرات.

 كما توفر انتقالًا سريعًا
 إلى شاشات الطعام والتمارين،
 وتحتوي على قائمة جانبية
 للوصول إلى الملف الشخصي والإعدادات.

 كما تحتوي على ميزات إضافية
 مثل AI Coach.
 */
public class DashboardActivity extends AppCompatActivity {

    // ============================================================
    // Drawer + Toolbar
    // ============================================================

    /**
     * DrawerLayout هو التخطيط الذي يسمح بفتح القائمة الجانبية.
     */
    private DrawerLayout drawerLayout;

    /**
     NavigationView
     هو المسؤول عن عرض عناصر القائمة الجانبية
     مثل:
     Home,
     Profile,
     Settings,
     Logout.

     كما يسمح للمستخدم
     بالضغط على هذه العناصر
     والتنقل بين الشاشات المختلفة.
     */
    private NavigationView navigationView;

    /**
     * Toolbar هو الشريط العلوي في الشاشة.
     */
    private Toolbar toolbar;

    // ============================================================
    // Status Cards
    // ============================================================

    /**
     * بطاقات عرض معلومات عامة في أعلى الشاشة.
     */
    private MaterialCardView cardSteps, cardWater, cardSleep;

    /**
     * أزرار على شكل Cards:
     * Add Food يفتح شاشة الأطعمة.
     * Add Exercise يفتح شاشة التمارين.
     */
    private MaterialCardView btnAddFood, btnAddExercise;

    // ============================================================
    // Daily Summary
    // ============================================================

    /**
     * مؤشر دائري يعرض نسبة تقدم السعرات بشكل تصميمي.
     */
    private CircularProgressIndicator circularCalories;

    /**
     * TextViews خاصة ببطاقة Daily Summary.
     */
    private TextView tvStepsCount, tvStepsLabel;
    private TextView tvWaterGlasses, tvWaterLabel;
    private TextView tvSleepHours, tvSleepLabel;
    private TextView tvDailyS, tvCalories, tvMins;

    // ============================================================
    // AI Coach
    // ============================================================

    /**
     * TextView يعرض رد الذكاء الاصطناعي.
     */
    private TextView tvAiCoachAnswer;

    /**
     * زر تشغيل الذكاء الاصطناعي.
     */
    private Button btnAiAdvice;

    /**
     * ============================================================
     * Notification Permission Launcher
     * ============================================================
     *
     * يستخدم لطلب إذن الإشعارات في Android 13+.
     */
    private final ActivityResultLauncher<String> requestNotificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                if (!isGranted) {
                    Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                }

            });

    /**
     * ============================================================
     * onCreate
     * ============================================================
     *
     * هذه الدالة تعمل عند فتح الشاشة.
     * يتم بداخلها:
     * - ربط ملف XML مع Java.
     * - ربط عناصر الواجهة حسب الـ id.
     * - إعداد القائمة الجانبية.
     * - إعداد أزرار التنقل.
     * - إعداد زر AI Coach.
     *
     * @param savedInstanceState يستخدم لاسترجاع حالة الشاشة إذا أعاد النظام إنشاءها.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط هذه الشاشة بملف التصميم activity_dashboard1.xml
        setContentView(R.layout.activity_dashboard1);

        // ============================================================
        // Notifications Setup
        // ============================================================

        /*
         * إنشاء قناة الإشعارات.
         * بدونها الإشعارات لن تعمل على Android 8+.
         */
        NotificationHelper.createNotificationChannel(this);

        /*
         * طلب إذن الإشعارات في Android 13+.
         */
        requestNotificationPermission();

        /*
         * تحديد وقت التذكير.
         * هنا بعد 10 ثواني كتجربة.
         */
        long triggerTime = System.currentTimeMillis() + 10000;

        /*
         * تشغيل التذكير.
         */
        AlarmScheduler.scheduleReminder(
                this,
                triggerTime,
                "Fitness Reminder",
                "Don't forget to add your food or exercise today!"
        );

        // ============================================================
        // 1) Toolbar + Drawer Setup
        // ============================================================

        // ربط الـ Toolbar من XML.
        toolbar = findViewById(R.id.toolbar);

        // جعل الـ Toolbar هو شريط الأدوات الرسمي لهذه الشاشة.
        setSupportActionBar(toolbar);

        // ربط DrawerLayout من XML.
        drawerLayout = findViewById(R.id.drawer_layout);

        // ربط NavigationView من XML.
        navigationView = findViewById(R.id.navigation_view);

        /*
         * ActionBarDrawerToggle:
         * يربط زر الهامبرغر الموجود في الـ Toolbar مع DrawerLayout.
         * عند الضغط عليه يتم فتح أو إغلاق القائمة الجانبية.
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        // إضافة الـ toggle كمستمع لحركة فتح وإغلاق القائمة.
        drawerLayout.addDrawerListener(toggle);

        // syncState()
        //تقوم بمزامنة حالة أيقونة الهامبرغر
        //مع حالة القائمة الجانبية.
        //
        //أي أنها تجعل الأيقونة تتحدث تلقائيًا
        //عند فتح أو إغلاق الـ Drawer.
        toggle.syncState();

        /*
         * التعامل مع الضغط على عناصر القائمة الجانبية.
         */
        navigationView.setNavigationItemSelectedListener(item -> {

            // الحصول على id العنصر الذي ضغط عليه المستخدم.
            int id = item.getItemId();

            if (id == R.id.nav_settings) {
                // فتح شاشة الإعدادات.
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));

            } else if (id == R.id.nav_profile) {
                // فتح شاشة الملف الشخصي.
                startActivity(new Intent(DashboardActivity.this, Profile.class));

            } else if (id == R.id.nav_home) {
                // إذا المستخدم ضغط Home، فقط أغلق القائمة.
                drawerLayout.closeDrawers();
            }

            // إغلاق القائمة بعد اختيار أي عنصر.
            drawerLayout.closeDrawers();

            // true يعني أن الضغط تمت معالجته.
            return true;
        });

        // ============================================================
        // 2) Status Cards Binding
        // ============================================================

        // ربط بطاقات الخطوات، الماء، والنوم.
        cardSteps = findViewById(R.id.cardSteps);
        cardWater = findViewById(R.id.cardWater);
        cardSleep = findViewById(R.id.cardSleep);

        // ربط نصوص بطاقة الخطوات.
        tvStepsCount = findViewById(R.id.tvStepsCount);
        tvStepsLabel = findViewById(R.id.tvStepsLabel);

        // ربط نصوص بطاقة الماء.
        tvWaterGlasses = findViewById(R.id.tvWaterGlasses);
        tvWaterLabel = findViewById(R.id.tvWaterLabel);

        // ربط نصوص بطاقة النوم.
        tvSleepHours = findViewById(R.id.tvSleepHours);
        tvSleepLabel = findViewById(R.id.tvSleepLabel);

        // ============================================================
        // 3) Daily Summary Binding
        // ============================================================

        // ربط مؤشر السعرات الدائري.
        circularCalories = findViewById(R.id.circularCalories);

        // ربط نصوص Daily Summary.
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        tvMins = findViewById(R.id.tvMins);

        /*
         * تعيين نسبة التقدم.
         * الرقم 45 هنا مثال فقط وليس محسوبًا من قاعدة البيانات.
         */
        circularCalories.setProgressCompat(45, true);

        // ============================================================
        // 4) Action Buttons
        // ============================================================

        // ربط زر Add Food.
        btnAddFood = findViewById(R.id.btnAddFood);

        // ربط زر Add Exercise.
        btnAddExercise = findViewById(R.id.btnAddExercise);

        /*
         * عند الضغط على Add Food:
         * يتم فتح FoodsActivtiy.
         *
         * انتبه:
         * اسم الكلاس عندك مكتوب FoodsActivtiy وليس FoodsActivity.
         * لذلك تركته كما هو حتى لا يخرب المشروع.
         */
        btnAddFood.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, FoodsActivtiy.class))
        );

        /*
         * عند الضغط على Add Exercise:
         * يتم فتح شاشة Exercises.
         */
        btnAddExercise.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, Exercises.class))
        );

        // ============================================================
        // 5) AI Coach Setup
        // ============================================================

        // ربط TextView الذي سيعرض رد Gemini.
        tvAiCoachAnswer = findViewById(R.id.tvAiCoachAnswer);

        // ربط زر تشغيل Gemini.
        btnAiAdvice = findViewById(R.id.btnAiAdvice);

        /*
         * عند الضغط على زر Get AI Advice:
         * 1. نعرض رسالة انتظار.
         * 2. نجهز prompt للذكاء الاصطناعي.
         * 3. نرسل الطلب إلى GeminiHelper.
         * 4. إذا رجع رد، نعرضه داخل tvAiCoachAnswer.
         * 5. إذا صار خطأ، نعرض رسالة الخطأ.
         */
        btnAiAdvice.setOnClickListener(v -> {

            // رسالة تظهر للمستخدم أثناء انتظار رد AI.
            tvAiCoachAnswer.setText("AI is thinking...");

            /*
             * هذا هو السؤال الذي نرسله للذكاء الاصطناعي.
             * جعلناه عامًا وصادقًا لأن بيانات Daily Summary عندك ليست مربوطة بقاعدة البيانات.
             */
            String prompt = "Give a short fitness and nutrition advice for a beginner who wants to gain weight and build muscle.";

            /*
             * إرسال الطلب إلى Gemini.
             * ResponseCallBack يستخدم لأن الرد لا يرجع فورًا.
             */
            GeminiHelper.getInstance().sendMessage(prompt, new ResponseCallBack() {

                /**
                 * يتم استدعاء هذه الدالة عندما يرجع Gemini رد ناجح.
                 *
                 * @param response النص الذي رجع من Gemini.
                 */
                @Override
                public void onResponse(String response) {

                    /*
                     * runOnUiThread:
                     * ضروري لأن تحديث الواجهة يجب أن يتم على UI Thread.
                     */
                    runOnUiThread(() -> tvAiCoachAnswer.setText(response));
                }

                /**
                 * يتم استدعاء هذه الدالة إذا حدث خطأ.
                 *
                 * @param error يحتوي تفاصيل الخطأ.
                 */
                @Override
                public void onError(Throwable error) {

                    /*
                     * عرض رسالة الخطأ حتى نعرف سبب المشكلة بدل ما التطبيق يسكت.
                     */
                    runOnUiThread(() ->
                            tvAiCoachAnswer.setText("Error: " + error.getMessage())
                    );
                }
            });
        });


        // ============================================================
        // 6) Optional Cards Clicks
        // ============================================================

        /*
         * هاي الأماكن اختيارية.
         * لاحقًا تقدر تفتح شاشة تفاصيل للخطوات أو الماء أو النوم.
         */
        cardSteps.setOnClickListener(v -> {
            // لاحقًا: افتح شاشة تفاصيل الخطوات.
        });

        cardWater.setOnClickListener(v -> {
            // لاحقًا: افتح شاشة تفاصيل الماء.
        });

        cardSleep.setOnClickListener(v -> {
            // لاحقًا: افتح شاشة تفاصيل النوم.
        });
    }

    /**
     * ============================================================
     * requestNotificationPermission
     * ============================================================
     *
     * طلب إذن الإشعارات.
     * يعمل فقط على Android 13+.
     */
    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                requestNotificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                );
            }
        }
    }

    /**
     * ============================================================
     * onBackPressed
     * ============================================================
     *
     * وظيفة هذه الدالة:
     * إذا كانت القائمة الجانبية مفتوحة، يغلقها.
     * إذا كانت مغلقة، ينفذ الرجوع الطبيعي.
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