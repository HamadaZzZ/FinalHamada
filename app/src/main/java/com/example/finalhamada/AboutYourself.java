package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
 * AboutYourself Activity مع Firebase Realtime Database
 * ============================================================
 *
 * شاشة إدخال البيانات الشخصية للمستخدم وحفظها مباشرة في Realtime Database.
 *
 * الوظائف الرئيسية:
 * 1️⃣ إدخال العمر (Age)
 * 2️⃣ إدخال الطول (Height)
 * 3️⃣ إدخال الوزن (Weight)
 * 4️⃣ اختيار الجنس (Gender)
 * 5️⃣ التحقق من صحة القيم المدخلة
 * 6️⃣ حفظ البيانات في Firebase Realtime Database تحت node "users/{uid}/profile"
 * 7️⃣ الانتقال إلى شاشة YourGoal عند نجاح الحفظ
 *
 * ملاحظات:
 * - تم استخدام try/catch لمعالجة الأخطاء عند تحويل النصوص إلى أرقام.
 * - HashMap يُستخدم لتخزين البيانات كأزواج Key-Value قبل إرسالها إلى Firebase.
 */
public class AboutYourself extends AppCompatActivity {

    // ==========================
    // عناصر واجهة المستخدم (UI)
    // ==========================
    private TextView tvstepText, tvheading, tvgender;
    private EditText etAge, etHeight, etWeight;
    private RadioGroup genderGroup;
    private RadioButton radioMale, radioFemale, radioOther;
    private Button nextButton;

    // ==========================
    // Firebase
    // ==========================
    private FirebaseAuth auth;
    private DatabaseReference dbRef;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة
     * تقوم بـ:
     * 1️⃣ ربط عناصر الواجهة
     * 2️⃣ تهيئة Firebase
     * 3️⃣ تفعيل Edge-to-Edge padding
     * 4️⃣ إعداد زر Next للتحقق من البيانات وحفظها
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself);

        // Edge-to-Edge Layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط عناصر XML بالكود
        tvstepText = findViewById(R.id.tvstepText);
        tvheading = findViewById(R.id.tvheading);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvgender = findViewById(R.id.tvgender);
        genderGroup = findViewById(R.id.genderGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);
        nextButton = findViewById(R.id.nextButton);

        // تهيئة Firebase
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        // عند الضغط على Next
        nextButton.setOnClickListener(v -> saveUserData());
    }

    /**
     * saveUserData
     * --------------------------------------------------
     * تقوم بـ:
     * 1️⃣ قراءة القيم من الحقول
     * 2️⃣ التحقق من تعبئة جميع الحقول
     * 3️⃣ تحويل النصوص إلى أرقام (Age, Height, Weight)
     * 4️⃣ التحقق من النطاق المنطقي للقيم
     * 5️⃣ إنشاء HashMap وتخزين البيانات
     * 6️⃣ إرسال البيانات إلى Firebase Realtime Database
     * 7️⃣ الانتقال إلى شاشة YourGoal عند نجاح الحفظ
     *
     * @implNote
     * - try/catch لمعالجة أي خطأ عند تحويل النصوص إلى أرقام.
     * - HashMap<String, Object> يُستخدم كأزواج Key-Value:
     *      المفتاح = اسم الحقل في قاعدة البيانات
     *      القيمة = البيانات المدخلة من المستخدم
     */
    private void saveUserData() {
        // قراءة القيم كنصوص
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String gender = "";

        // تحديد الجنس المختار
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == radioMale.getId()) gender = "Male";
        else if (selectedId == radioFemale.getId()) gender = "Female";
        else if (selectedId == radioOther.getId()) gender = "Other";

        // التحقق من تعبئة جميع الحقول
        if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long age;
        double height, weight;

        try {
            age = Long.parseLong(ageStr);
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // التحقق من النطاق المنطقي
        if (age < 5 || age > 120) {
            etAge.setError("Enter valid age (5-120)");
            return;
        }
        if (height < 50 || height > 250) {
            etHeight.setError("Enter valid height (50-250 cm)");
            return;
        }
        if (weight < 10 || weight > 300) {
            etWeight.setError("Enter valid weight (10-300 kg)");
            return;
        }

        // ==========================
        // إنشاء HashMap لإرسال البيانات إلى Firebase
        // ==========================
        HashMap<String, Object> profileData = new HashMap<>();
        profileData.put("age", age);
        profileData.put("height", height);
        profileData.put("weight", weight);
        profileData.put("gender", gender);

        // UID للمستخدم الحالي
        String uid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "unknown";

        // حفظ البيانات في Realtime Database
        dbRef.child("users")
                .child(uid)
                .child("profile")
                .updateChildren(profileData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AboutYourself.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    // الانتقال إلى الشاشة التالية
                    startActivity(new Intent(AboutYourself.this, YourGoal.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(AboutYourself.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
