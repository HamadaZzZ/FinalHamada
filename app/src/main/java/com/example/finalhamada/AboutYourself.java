package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
 * AboutYourself Activity
 * ---------------------------------------------------------
 * هذه الشاشة مسؤولة عن أخذ معلومات المستخدم الأساسية
 * مثل:
 * - العمر
 * - الطول
 * - الوزن
 * - الجنس
 *
 * ثم حفظ هذه البيانات داخل Firebase Database
 * لاستخدامها لاحقًا داخل التطبيق.
 */
public class AboutYourself extends AppCompatActivity {

    // حقول إدخال البيانات
    private EditText etAge, etHeight, etWeight;

    // مجموعة اختيار الجنس
    private RadioGroup genderGroup;

    // خيارات الجنس
    private RadioButton radioMale, radioFemale, radioOther;

    // زر الانتقال للمرحلة التالية
    private Button nextButton;

    // Firebase Authentication للحصول على المستخدم الحالي
    private FirebaseAuth auth;

    // مرجع قاعدة البيانات السحابية
    private DatabaseReference dbRef;

    /**
     * دالة onCreate:
     * ---------------------------------------------------------
     * تعتبر نقطة البداية للشاشة.
     *
     * تقوم بـ:
     * - ربط عناصر الواجهة بالكود
     * - تهيئة Firebase
     * - تجهيز زر "التالي"
     *
     * أهمية الدالة:
     * بدونها لن تعمل الشاشة ولن يتم ربط عناصر الواجهة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط الشاشة بملف التصميم XML
        setContentView(R.layout.activity_about_yourself);

        // ضبط الشاشة لتتناسب مع حواف النظام
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        // ربط العناصر البرمجية بعناصر الواجهة
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        genderGroup = findViewById(R.id.genderGroup);

        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);

        nextButton = findViewById(R.id.nextButton);

        // تهيئة Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // تهيئة Firebase Database
        dbRef = FirebaseDatabase.getInstance().getReference();

        /**
         * عند الضغط على زر "التالي"
         * يتم استدعاء دالة حفظ البيانات.
         */
        nextButton.setOnClickListener(v -> saveUserData());
    }

    /**
     * دالة saveUserData:
     * ---------------------------------------------------------
     * تقوم هذه الدالة بـ:
     * - قراءة البيانات من الحقول
     * - التحقق من صحة البيانات
     * - تحويل القيم الرقمية
     * - حفظ البيانات داخل Firebase
     *
     * أهمية الدالة:
     * بدونها لن يتم حفظ معلومات المستخدم،
     * وبالتالي لن يستطيع التطبيق عرض بياناته لاحقًا.
     */
    private void saveUserData() {

        // قراءة النصوص من الحقول
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        String gender = "";

        /**
         * تحديد الجنس الذي اختاره المستخدم.
         *
         * إذا حذفنا هذا الجزء،
         * لن يستطيع التطبيق معرفة الجنس المختار.
         */
        int selectedId = genderGroup.getCheckedRadioButtonId();

        if (selectedId == radioMale.getId()) {
            gender = "Male ";
        } else if (selectedId == radioFemale.getId()) {
            gender = "Female";
        } else if (selectedId == radioOther.getId()) {
            gender = "Other";
        }

        /**
         * التحقق من أن جميع الحقول ممتلئة.
         *
         * أهمية هذا الفحص:
         * يمنع المستخدم من إرسال بيانات ناقصة.
         *
         * بدون هذا الفحص قد يتم حفظ بيانات فارغة داخل قاعدة البيانات.
         */
        if (ageStr.isEmpty() ||
                heightStr.isEmpty() ||
                weightStr.isEmpty() ||
                gender.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all required fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        long age;
        double height, weight;

        /**
          try/catch
         للتعامل مع الأخطاء التي قد تحدث أثناء تحويل النصوص إلى أرقام
         مثل parseLong و parseDouble.

         إذا أدخل المستخدم نصًا بدل رقم،
         فإن try/catch تمنع حدوث Crash للتطبيق
         وتسمح بعرض رسالة خطأ للمستخدم بدل إغلاق التطبيق.
         */
        try {

            age = Long.parseLong(ageStr);

            height = Double.parseDouble(heightStr);

            weight = Double.parseDouble(weightStr);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter valid numbers only",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        /**
         * HashMap:
         * ---------------------------------------------------------
         * تستخدم لتجميع بيانات المستخدم
         * على شكل:
         * Key -> Value
         *
         * مثال:
         * age -> 18
         *
         * أهمية استخدامها:
         * تسهل إرسال جميع البيانات إلى Firebase مرة واحدة.
         */
        HashMap<String, Object> profileData = new HashMap<>();

        profileData.put("age", age);

        profileData.put("height", height);

        profileData.put("weight", weight);

        profileData.put("gender", gender);

        /**
         * التحقق من وجود مستخدم مسجل دخول.
         *
         * بدون هذا الفحص قد يحدث خطأ
         * إذا لم يكن هناك مستخدم حالي.
         */
        if (auth.getCurrentUser() == null) return;

        /**
         * getUid():
         * ---------------------------------------------------------
         * تجلب الرقم الخاص بالمستخدم الحالي.
         *
         * كل مستخدم في Firebase يمتلك رقمًا مختلفًا خاصًا به،
         * حتى يتم حفظ بيانات كل مستخدم بشكل منفصل.
         *
         * أهمية هذا الرقم:
         * يمنع اختلاط بيانات المستخدمين مع بعض.
         */
        String uid = auth.getCurrentUser().getUid();

        /**
         * updateChildren():
         * ---------------------------------------------------------
         * تقوم بتحديث البيانات داخل Firebase
         * دون حذف البيانات القديمة.
         *
         * أهمية استخدامها:
         * إذا استخدمنا setValue قد يتم حذف بيانات أخرى موجودة مسبقًا.
         */
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(profileData)

                /**
                 * يتم تنفيذ هذا الجزء
                 * عند نجاح عملية الحفظ.
                 */
                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(
                            this,
                            "Your data has been saved successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    // الانتقال لشاشة تحديد الهدف
                    startActivity(
                            new Intent(
                                    AboutYourself.this,
                                    YourGoal.class
                            )
                    );

                    // إغلاق الشاشة الحالية
                    finish();
                })

                /**
                 * يتم تنفيذ هذا الجزء
                 * إذا حدث خطأ أثناء الحفظ.
                 */
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Save failed " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}