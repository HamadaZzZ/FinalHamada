package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * ============================================================
 * YourGoal Activity
 * ============================================================
 *
 * شاشة اختيار الهدف الرئيسي للمستخدم:
 * - خسارة وزن (Lose Weight)
 * - الحفاظ على الوزن (Maintain Weight)
 * - زيادة الوزن (Gain Weight)
 *
 * الوظائف:
 * 1️⃣ عرض ثلاثة خيارات باستخدام RadioButtons + LinearLayouts
 * 2️⃣ التأكد من اختيار واحد فقط (RadioGroup)
 * 3️⃣ الضغط على LinearLayout يقوم بتحديد RadioButton المقابل
 * 4️⃣ حفظ الهدف في Firebase Realtime Database تحت المسار:
 *    users/{uid}/profile/goal
 * 5️⃣ الانتقال إلى Dashboard بعد الحفظ
 *
 * توثيق إضافي:
 * - Realtime Database: قاعدة بيانات سحابية بصيغة JSON
 * - HashMap: تُحوّل تلقائيًا إلى JSON عند الحفظ
 */
public class YourGoal extends AppCompatActivity {

    // ==========================
    // UI Components
    // ==========================
    private RadioButton rbLose, rbMaintain, rbGain;
    private LinearLayout LLloseWeight, LLmaintainWeight, LLgainWeight;
    private Button btnContinue;
    private RadioGroup radioGroup;

    // ==========================
    // Firebase
    // ==========================
    private FirebaseAuth auth;
    private DatabaseReference dbRef;

    /**
     * onCreate
     * ------------------------------------------------------
     * ربط عناصر الواجهة + Edge-to-Edge layout + Firebase
     * + تصحيح اختيار واحد فقط + إعداد زر Continue
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_goal);

        // ====== Edge-to-Edge layout ======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // ====== Bind UI elements ======
        rbLose = findViewById(R.id.rbLose);
        rbMaintain = findViewById(R.id.rbMaintain);
        rbGain = findViewById(R.id.rbGain);

        LLloseWeight = findViewById(R.id.LLloseWeight);
        LLmaintainWeight = findViewById(R.id.LLmaintainWeight);
        LLgainWeight = findViewById(R.id.LLgainWeight);

        btnContinue = findViewById(R.id.btnContinue);
        radioGroup = findViewById(R.id.radioGroupGoals);

        // ====== Firebase ======
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        // ====== LinearLayouts clickable + تصحيح اختيار واحد فقط ======
        LLloseWeight.setOnClickListener(v -> rbLose.setChecked(true));
        LLmaintainWeight.setOnClickListener(v -> rbMaintain.setChecked(true));
        LLgainWeight.setOnClickListener(v -> rbGain.setChecked(true));

        // ====== Continue button ======
        btnContinue.setOnClickListener(v -> saveGoalToFirebase());
    }

    /**
     * saveGoalToFirebase
     * ------------------------------------------------------
     * 1️⃣ التأكد من اختيار المستخدم لهدف واحد فقط
     * 2️⃣ حفظ الهدف في Firebase Realtime Database باستخدام HashMap
     * 3️⃣ الانتقال إلى DashboardActivity عند النجاح
     */
    private void saveGoalToFirebase() {

        String selectedGoal = "";

        if (rbLose.isChecked()) selectedGoal = "Lose Weight";
        else if (rbMaintain.isChecked()) selectedGoal = "Maintain Weight";
        else if (rbGain.isChecked()) selectedGoal = "Gain Weight";

        if (selectedGoal.isEmpty()) {
            Toast.makeText(this, "Please select a goal", Toast.LENGTH_SHORT).show();
            return;
        }

        // ==========================
        // HashMap لتخزين الهدف
        // ==========================
        /**
         * HashMap
         * ------------------------------------------------------
         * - Key = اسم الحقل داخل Firebase
         * - Value = قيمة الهدف الذي اختاره المستخدم
         *
         * Firebase Realtime Database يستقبل HashMap كـ JSON تلقائيًا.
         * مثال عند الحفظ:
         * {
         *   "goal": "Lose Weight"
         * }
         */
        HashMap<String, Object> goalData = new HashMap<>();
        goalData.put("goal", selectedGoal);

        // UID المستخدم الحالي
        String uid = auth.getCurrentUser().getUid();

        /**
         * حفظ الهدف داخل:
         * users/{uid}/profile/goal
         * updateChildren يضمن دمج البيانات دون حذف الحقول الأخرى
         */
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(goalData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Goal saved successfully", Toast.LENGTH_SHORT).show();
                    // الانتقال للشاشة التالية
                    startActivity(new Intent(YourGoal.this, DashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to save goal: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
