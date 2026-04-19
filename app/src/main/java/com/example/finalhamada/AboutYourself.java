package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.EditText; // تمثيل لحقل إدخال النص القابل للتعديل.
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
 * AboutYourself Activity: شاشة "حدثنا عن نفسك".
 * ---------------------------------------------------------
 * تهدف هذه الشاشة إلى جمع البيانات الجسدية الأساسية للمستخدم (العمر، الطول، الوزن، الجنس)
 * بعد عملية التسجيل مباشرة، وتخزينها في قاعدة البيانات السحابية لتهيئة تجربة مستخدم مخصصة.
 */
public class AboutYourself extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Elements) ===
    private EditText etAge, etHeight, etWeight; // حقول إدخال العمر والطول والوزن
    private RadioGroup genderGroup;              // مجموعة اختيار الجنس
    private RadioButton radioMale, radioFemale, radioOther; // خيارات الجنس المتاحة
    private Button nextButton;                   // زر الانتقال للمرحلة التالية

    // === كائنات خدمات Firebase ===
    private FirebaseAuth auth;          // كائن للحصول على معلومات الحساب الحالي
    private DatabaseReference dbRef;    // مرجع للتعامل مع قاعدة البيانات السحابية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_about_yourself.xml بهذا الكود
        setContentView(R.layout.activity_about_yourself);

        // ضبط واجهة المستخدم لتتوافق مع حواف الشاشة (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- ربط العناصر البرمجية بالـ IDs من ملف الـ XML ---
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        genderGroup = findViewById(R.id.genderGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);
        nextButton = findViewById(R.id.nextButton);

        // تهيئة نظام مصادقة Firebase للحصول على UID الخاص بالمستخدم
        auth = FirebaseAuth.getInstance();

        // تهيئة مرجع قاعدة البيانات السحابية (النقطة الرئيسية للاتصال)
        dbRef = FirebaseDatabase.getInstance().getReference();

        // --- إعداد حدث النقر على زر "التالي" ---
        nextButton.setOnClickListener(v -> saveUserData());
    }

    /**
     * دالة saveUserData: تقوم بقراءة البيانات من الحقول، التحقق منها، ثم رفعها للـ Firebase.
     */
    private void saveUserData() {

        // استخراج النصوص من الحقول وحذف الفراغات
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        String gender = "";

        // تحديد الجنس المختار بناءً على الـ RadioButton المضغوط
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == radioMale.getId()) gender = "ذكر";
        else if (selectedId == radioFemale.getId()) gender = "أنثى";
        else if (selectedId == radioOther.getId()) gender = "غير ذلك";

        // --- 1. التحقق من أن جميع الحقول ممتلئة ---
        if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول المطلوبة", Toast.LENGTH_SHORT).show();
            return;
        }

        long age;
        double height, weight;

        // --- 2. محاولة تحويل النصوص إلى أرقام (للتأكد من صحة المدخلات) ---
        try {
            age = Long.parseLong(ageStr);
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "يرجى إدخال أرقام صحيحة فقط", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- 3. تجهيز البيانات في HashMap تمهيداً لإرسالها للـ Firebase ---
        HashMap<String, Object> profileData = new HashMap<>();
        profileData.put("age", age);
        profileData.put("height", height);
        profileData.put("weight", weight);
        profileData.put("gender", gender);

        // الحصول على المعرف الفريد للمستخدم الحالي (UID)
        if (auth.getCurrentUser() == null) return; // حماية في حال عدم وجود مستخدم
        String uid = auth.getCurrentUser().getUid();

        /**
         * حفظ البيانات في المسار التالي:
         * users -> [User_UID] -> profile
         * نستخدم updateChildren لتحديث الحقول المحددة دون مسح البيانات السابقة في نفس المسار.
         */
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(profileData)
                .addOnSuccessListener(aVoid -> {
                    // في حال نجاح عملية الحفظ في السحابة
                    Toast.makeText(this, "تم حفظ بياناتك بنجاح", Toast.LENGTH_SHORT).show();

                    // الانتقال لشاشة "تحديد الهدف" (YourGoal)
                    startActivity(new Intent(AboutYourself.this, YourGoal.class));
                    finish(); // إغلاق الشاشة الحالية
                })
                .addOnFailureListener(e -> {
                    // في حال حدوث خطأ في الاتصال أو الحفظ
                    Toast.makeText(this, "فشل الحفظ: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
