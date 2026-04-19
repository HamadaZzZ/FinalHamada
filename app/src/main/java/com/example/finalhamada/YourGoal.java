package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.LinearLayout; // تمثيل لحاوية العناصر الخطية.
import android.widget.RadioButton; // تمثيل لخيار فردي ضمن مجموعة خيارات.
import android.widget.RadioGroup; // حاوية لمجموعة من RadioButtons تسمح باختيار واحد فقط.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة أسفل الشاشة.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.
import androidx.core.graphics.Insets; // للتعامل مع أبعاد حواف النظام (System Bars).
import androidx.core.view.ViewCompat; // توفير ميزات التوافق لعناصر الواجهة.
import androidx.core.view.WindowInsetsCompat; // للتعامل مع مسافات النظام (أشرطة الحالة والتنقل).

import com.google.firebase.auth.FirebaseAuth; // نظام المصادقة في Firebase (للحصول على معرف المستخدم).
import com.google.firebase.database.DatabaseReference; // مرجع للوصول لمكان محدد في قاعدة البيانات السحابية.
import com.google.firebase.database.FirebaseDatabase; // الوصول لقاعدة بيانات Firebase Realtime السحابية.

import java.util.HashMap; // بنية بيانات (مفتاح -> قيمة) لتسهيل إرسال البيانات للـ Firebase.

/**
 * YourGoal Activity: شاشة تحديد الهدف الرياضي.
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدم اختيار هدفه الرئيسي من بين ثلاثة خيارات:
 * 1. خسارة الوزن (Lose Weight).
 * 2. الحفاظ على الوزن الحالي (Maintain Weight).
 * 3. زيادة الوزن (Gain Weight).
 * يتم تخزين هذا الهدف في قاعدة البيانات السحابية لتخصيص خطة المستخدم.
 */
public class YourGoal extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Components) ===
    private RadioButton rbLose, rbMaintain, rbGain; // خيارات الأهداف
    private LinearLayout LLloseWeight, LLmaintainWeight, LLgainWeight; // حاويات الخيارات القابلة للنقر
    private Button btnContinue; // زر المتابعة
    private RadioGroup radioGroup; // مجموعة خيارات الأهداف لضمان اختيار واحد فقط

    // === كائنات خدمات Firebase ===
    private FirebaseAuth auth;          // نظام المصادقة للحصول على هوية المستخدم
    private DatabaseReference dbRef;    // مرجع للتعامل مع قاعدة البيانات السحابية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_your_goal.xml بهذا الكود
        setContentView(R.layout.activity_your_goal);

        // ضبط واجهة المستخدم لتتوافق مع حواف الشاشة (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- ربط العناصر البرمجية بالـ IDs من ملف الـ XML ---
        rbLose = findViewById(R.id.rbLose);
        rbMaintain = findViewById(R.id.rbMaintain);
        rbGain = findViewById(R.id.rbGain);

        LLloseWeight = findViewById(R.id.LLloseWeight);
        LLmaintainWeight = findViewById(R.id.LLmaintainWeight);
        LLgainWeight = findViewById(R.id.LLgainWeight);

        btnContinue = findViewById(R.id.btnContinue);
        radioGroup = findViewById(R.id.radioGroupGoals);

        // تهيئة نظام مصادقة Firebase
        auth = FirebaseAuth.getInstance();
        
        // الحصول على المرجع الرئيسي لقاعدة بيانات التطبيق في Firebase
        dbRef = FirebaseDatabase.getInstance().getReference();

        // --- جعل حاويات الخيارات (Layouts) قابلة للنقر لتسهيل الاختيار على المستخدم ---
        LLloseWeight.setOnClickListener(v -> rbLose.setChecked(true));
        LLmaintainWeight.setOnClickListener(v -> rbMaintain.setChecked(true));
        LLgainWeight.setOnClickListener(v -> rbGain.setChecked(true));

        // --- إعداد حدث النقر على زر "متابعة" ---
        btnContinue.setOnClickListener(v -> saveGoalToFirebase());
    }

    /**
     * دالة saveGoalToFirebase: تقوم بتحديد الهدف المختار وحفظه في Firebase.
     */
    private void saveGoalToFirebase() {

        String selectedGoal = "";

        // تحديد النص المقابل للهدف المختار
        if (rbLose.isChecked()) selectedGoal = "خسارة وزن";
        else if (rbMaintain.isChecked()) selectedGoal = "الحفاظ على الوزن";
        else if (rbGain.isChecked()) selectedGoal = "زيادة وزن";

        // التحقق من أن المستخدم قام باختيار هدف واحد على الأقل
        if (selectedGoal.isEmpty()) {
            Toast.makeText(this, "يرجى اختيار هدف واحد للمتابعة", Toast.LENGTH_SHORT).show();
            return;
        }

        // تجهيز البيانات في HashMap
        HashMap<String, Object> goalData = new HashMap<>();
        goalData.put("goal", selectedGoal);

        // الحصول على المعرف الفريد للمستخدم الحالي (UID)
        if (auth.getCurrentUser() == null) return;
        String uid = auth.getCurrentUser().getUid();

        /**
         * حفظ الهدف المختار في مسار الملف الشخصي للمستخدم:
         * users -> [User_UID] -> profile
         * نستخدم updateChildren لتعديل حقل الهدف فقط دون المساس ببيانات الطول والوزن وغيرها.
         */
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(goalData)
                .addOnSuccessListener(aVoid -> {
                    // في حال نجاح عملية الحفظ
                    Toast.makeText(this, "تم تحديد هدفك بنجاح", Toast.LENGTH_SHORT).show();
                    
                    // الانتقال للوحة التحكم الرئيسية (DashboardActivity)
                    startActivity(new Intent(YourGoal.this, DashboardActivity.class));
                    finish(); // إغلاق شاشة تحديد الهدف
                })
                .addOnFailureListener(e -> {
                    // في حال فشل الحفظ
                    Toast.makeText(this, "حدث خطأ أثناء الحفظ: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
