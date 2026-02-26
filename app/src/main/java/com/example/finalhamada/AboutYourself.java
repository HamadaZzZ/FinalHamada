/**
 * الحزمة الأساسية التي تحتوي هذا الكلاس.
 */
package com.example.finalhamada;

import android.content.Intent; // Intent: يستخدم للانتقال بين الشاشات (Activities).
import android.os.Bundle; // Bundle: يحتوي بيانات حالة الشاشة عند إنشائها.
import android.widget.Button; // Button: زر في واجهة المستخدم.
import android.widget.EditText; // EditText: حقل إدخال نص.
import android.widget.RadioButton; // RadioButton: خيار فردي داخل RadioGroup.
import android.widget.RadioGroup; // RadioGroup: مجموعة خيارات يُسمح باختيار واحد فقط منها.
import android.widget.Toast; // Toast: رسالة قصيرة تظهر للمستخدم.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity.
import androidx.core.graphics.Insets; // Insets: يمثل أبعاد الحواف للنظام (Status/Nav bar).
import androidx.core.view.ViewCompat; // ViewCompat: دعم خصائص متوافقة مع إصدارات مختلفة.
import androidx.core.view.WindowInsetsCompat; // WindowInsetsCompat: للتعامل مع هوامش النظام.

import com.google.firebase.auth.FirebaseAuth;
// FirebaseAuth: لإدارة المستخدم الحالي (الحصول على UID).

import com.google.firebase.database.DatabaseReference;
// DatabaseReference: مرجع يشير إلى مسار معين داخل Realtime Database.

import com.google.firebase.database.FirebaseDatabase;
// FirebaseDatabase: نقطة الدخول إلى Realtime Database.

import java.util.HashMap;
// HashMap: بنية بيانات (Key → Value) تُستخدم لتجميع البيانات قبل إرسالها.

/**
 * ============================================================
 * AboutYourself Activity
 * ============================================================
 * هذه الشاشة تجمع معلومات المستخدم الأساسية:
 * - العمر
 * - الطول
 * - الوزن
 * - الجنس
 *
 * ثم تقوم بحفظها داخل Firebase Realtime Database
 * تحت حساب المستخدم الحالي.
 * ============================================================
 */
public class AboutYourself extends AppCompatActivity {

    /** حقول إدخال البيانات */
    private EditText etAge, etHeight, etWeight;

    /** مجموعة اختيار الجنس */
    private RadioGroup genderGroup;

    /** أزرار اختيار الجنس */
    private RadioButton radioMale, radioFemale, radioOther;

    /** زر الانتقال للخطوة التالية */
    private Button nextButton;

    /** FirebaseAuth للحصول على المستخدم الحالي */
    private FirebaseAuth auth;

    /**
     * ============================================================
     * Firebase Realtime Database
     * ============================================================
     * - قاعدة بيانات NoSQL (ليست جداول مثل SQL).
     * - تخزن البيانات على شكل JSON Tree.
     * - تعمل Online + Realtime (أي تحديث يظهر فورًا).
     * - كل مستخدم يتم تخزين بياناته تحت UID خاص به.
     *
     * مثال شكل البيانات:
     *
     * users
     *   └── uid123
     *         └── profile
     *               ├── age: 22
     *               ├── height: 175
     *               ├── weight: 70
     *               └── gender: "Male"
     *
     * هنا نستخدمها لتخزين بيانات الملف الشخصي مرة واحدة
     * بعد التسجيل.
     * ============================================================
     */
    private DatabaseReference dbRef;

    /**
     * onCreate:
     * - ربط XML
     * - تجهيز Edge-to-Edge
     * - تهيئة Firebase
     * - إعداد ClickListener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself);

        /**
         * Edge-to-Edge Layout:
         * يجعل المحتوى يمتد خلف شريط الحالة وشريط التنقل
         * ويضيف padding تلقائي حسب حجم النظام.
         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط العناصر بالواجهة
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        genderGroup = findViewById(R.id.genderGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);
        nextButton = findViewById(R.id.nextButton);

        // تهيئة FirebaseAuth
        auth = FirebaseAuth.getInstance();

        /**
         * getReference():
         * يعطينا مرجع للجذر (Root) في قاعدة البيانات.
         * بعد ذلك نحدد المسار باستخدام child().
         */
        dbRef = FirebaseDatabase.getInstance().getReference();

        /**
         * ClickListener:
         * عند الضغط على Next
         * يتم حفظ البيانات في Firebase.
         */
        nextButton.setOnClickListener(v -> saveUserData());
    }

    /**
     * saveUserData:
     * ----------------------------------
     * 1- قراءة البيانات من الحقول.
     * 2- التحقق من صحتها.
     * 3- تحويلها لأرقام.
     * 4- حفظها في Realtime Database.
     */
    private void saveUserData() {

        // قراءة القيم كنصوص
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        String gender = "";

        // معرفة أي RadioButton تم اختياره
        int selectedId = genderGroup.getCheckedRadioButtonId();

        if (selectedId == radioMale.getId()) gender = "Male";
        else if (selectedId == radioFemale.getId()) gender = "Female";
        else if (selectedId == radioOther.getId()) gender = "Other";

        /**
         * Validation:
         * التأكد أن جميع الحقول ممتلئة.
         */
        if (ageStr.isEmpty() || heightStr.isEmpty() ||
                weightStr.isEmpty() || gender.isEmpty()) {

            Toast.makeText(this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        long age;
        double height, weight;

        /**
         * تحويل النصوص إلى أرقام.
         * في حال إدخال حروف بدلاً من أرقام
         * سيتم رمي NumberFormatException.
         */
        try {
            age = Long.parseLong(ageStr);
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    "Enter valid numbers",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * HashMap:
         * ----------------------------------
         * نستخدمها لتجميع البيانات على شكل:
         * Key → Value
         *
         * Firebase يحولها تلقائيًا إلى JSON عند الحفظ.
         */
        HashMap<String, Object> profileData = new HashMap<>();
        profileData.put("age", age);
        profileData.put("height", height);
        profileData.put("weight", weight);
        profileData.put("gender", gender);

        /**
         * الحصول على UID للمستخدم الحالي.
         * UID هو المفتاح الأساسي الذي يميز كل مستخدم.
         */
        String uid = auth.getCurrentUser().getUid();

        /**
         * updateChildren:
         * ----------------------------------
         * - يحدث القيم المحددة فقط.
         * - لا يحذف بيانات أخرى موجودة.
         *
         * المسار النهائي للحفظ:
         * users → uid → profile
         */
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(profileData)

                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(this,
                            "Data saved successfully",
                            Toast.LENGTH_SHORT).show();

                    // الانتقال للشاشة التالية
                    startActivity(new Intent(AboutYourself.this, YourGoal.class));
                    finish();
                })

                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
    }
}