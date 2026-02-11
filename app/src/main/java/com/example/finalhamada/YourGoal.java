package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ============================================================
 * YourGoal Activity
 * ============================================================
 * شاشة "هدفك" حيث يختار المستخدم هدفه الرئيسي:
 * - خسارة وزن
 * - الحفاظ على الوزن
 * - زيادة الوزن
 *
 * الوظائف الرئيسية:
 * 1️⃣ عرض ثلاثة خيارات باستخدام RadioButtons و LinearLayouts
 * 2️⃣ السماح باختيار خيار واحد فقط (RadioGroup)
 * 3️⃣ الضغط على أي LinearLayout يقوم بتحديد الراديو المقابل
 * 4️⃣ زر Continue للانتقال إلى Dashboard
 *
 * ملاحظات:
 * - تم استخدام Edge-to-Edge layout لتجنب قص النصوص خلف شريط النظام.
 * - LinearLayouts قابلة للنقر لتسهيل اختيار الهدف دون الضغط المباشر على RadioButton.
 * ============================================================
 */
public class YourGoal extends AppCompatActivity {

    // ==========================
    // عناصر واجهة المستخدم (UI)
    // ==========================
    private TextView tvYourGoal;        // نص عنوان "هدفك"
    private TextView tvaim;             // نص وصف الهدف

    private RadioButton rbLose;          // خيار خسارة الوزن
    private RadioButton rbMaintain;      // خيار الحفاظ على الوزن
    private RadioButton rbGain;          // خيار زيادة الوزن

    private TextView tvLoseWeight;       // نص "Lose Weight"
    private TextView tvmainWeight;       // نص "Maintain Weight"
    private TextView tvGainWeight;       // نص "Gain Weight"

    private LinearLayout LLloseWeight;   // LinearLayout لخسارة الوزن
    private LinearLayout LLmaintainWeight;// LinearLayout للحفاظ على الوزن
    private LinearLayout LLgainWeight;   // LinearLayout لزيادة الوزن

    private Button btnContinue;          // زر الاستمرار

    private RadioGroup radioGroup;       // مجموعة RadioButtons لضمان اختيار واحد فقط

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة
     * تقوم بـ:
     * 1️⃣ ربط عناصر الواجهة مع الكود
     * 2️⃣ تفعيل Edge-to-Edge padding
     * 3️⃣ ربط LinearLayouts مع RadioButtons لتسهيل الاختيار
     * 4️⃣ إعداد زر Continue للانتقال إلى Dashboard
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_goal);

        // ====== Edge-to-edge layout ======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // ====== Bind UI elements ======
        tvYourGoal = findViewById(R.id.tvYourGoal);
        tvaim = findViewById(R.id.tvaim);

        rbLose = findViewById(R.id.rbLose);
        rbMaintain = findViewById(R.id.rbMaintain);
        rbGain = findViewById(R.id.rbGain);

        tvLoseWeight = findViewById(R.id.tvLoseWeight);
        tvmainWeight = findViewById(R.id.tvmainWeight);
        tvGainWeight = findViewById(R.id.tvGainWeight);

        LLloseWeight = findViewById(R.id.LLloseWeight);
        LLmaintainWeight = findViewById(R.id.LLmaintainWeight);
        LLgainWeight = findViewById(R.id.LLgainWeight);

        btnContinue = findViewById(R.id.btnContinue);
        radioGroup = findViewById(R.id.radioGroupGoals);

        // ====== Make LinearLayouts clickable and update RadioGroup ======
        LLloseWeight.setOnClickListener(v -> radioGroup.check(rbLose.getId()));
        LLmaintainWeight.setOnClickListener(v -> radioGroup.check(rbMaintain.getId()));
        LLgainWeight.setOnClickListener(v -> radioGroup.check(rbGain.getId()));

        // ====== Continue button ======
        btnContinue.setOnClickListener(v -> {
            /**
             * الانتقال إلى DashboardActivity
             * --------------------------------------------------
             * بعد اختيار الهدف، المستخدم ينتقل للشاشة التالية.
             */
            Intent intent = new Intent(YourGoal.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
