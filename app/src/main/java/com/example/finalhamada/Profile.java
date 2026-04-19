package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.ImageView; // تمثيل لعنصر عرض الصور.
import android.widget.TextView; // تمثيل لعنصر عرض النصوص.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.

import com.google.firebase.auth.FirebaseAuth; // نظام المصادقة في Firebase.
import com.google.firebase.database.DataSnapshot; // كائن يحتوي على "لقطة" من البيانات المسترجعة من Firebase.
import com.google.firebase.database.DatabaseError; // كائن يحتوي على تفاصيل الخطأ في حال فشل الاتصال بقاعدة البيانات.
import com.google.firebase.database.DatabaseReference; // مرجع للوصول لمسار معين في قاعدة البيانات السحابية.
import com.google.firebase.database.FirebaseDatabase; // الوصول لقاعدة بيانات Firebase Realtime السحابية.
import com.google.firebase.database.ValueEventListener; // مستمع للأحداث لقراءة البيانات من قاعدة البيانات.

import java.util.HashMap; // بنية بيانات (مفتاح -> قيمة).

/**
 * Profile Activity: شاشة الملف الشخصي للمستخدم.
 * ---------------------------------------------------------
 * تهدف هذه الشاشة إلى:
 * 1. عرض البيانات الشخصية والجسدية للمستخدم المسترجعة من Firebase Realtime Database.
 * 2. السماح للمستخدم بالانتقال لتعديل بياناته الشخصية.
 * 3. توفير خيار تسجيل الخروج (Logout) من الحساب الحالي.
 */
public class Profile extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Elements) ===
    private TextView tvName, tvSubtitle, tvHeightValue, tvWeightValue, tvAgeValue; // نصوص عرض البيانات
    private ImageView imgProfile; // صورة الملف الشخصي
    private Button btnEditProfile, btnLogout; // أزرار التعديل وتسجيل الخروج

    // === كائنات خدمات Firebase ===
    private FirebaseAuth auth;          // نظام المصادقة للحصول على هوية المستخدم
    private DatabaseReference dbRef;    // مرجع للتعامل مع قاعدة البيانات السحابية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_profile.xml بهذا الكود
        setContentView(R.layout.activity_profile);

        // --- ربط العناصر البرمجية بالمعرفات (IDs) من ملف الـ XML ---
        tvName = findViewById(R.id.tvName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvHeightValue = findViewById(R.id.tvHeightValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvAgeValue = findViewById(R.id.tvAgeValue);
        imgProfile = findViewById(R.id.imgProfile);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // تهيئة نظام مصادقة Firebase
        auth = FirebaseAuth.getInstance();
        
        // الحصول على المرجع الرئيسي لقاعدة بيانات التطبيق في Firebase
        dbRef = FirebaseDatabase.getInstance().getReference();

        // استدعاء دالة جلب البيانات من السحابة وعرضها
        loadUserData();

        // --- ضبط حدث النقر على زر "تعديل الملف الشخصي" ---
        btnEditProfile.setOnClickListener(v -> {
            // الانتقال لشاشة "AboutYourself" لتمكين المستخدم من تحديث بياناته
            startActivity(new Intent(Profile.this, AboutYourself.class));
        });

        // --- ضبط حدث النقر على زر "تسجيل الخروج" ---
        btnLogout.setOnClickListener(v -> {
            // تنفيذ عملية تسجيل الخروج برمجياً من Firebase
            auth.signOut();
            // الانتقال لشاشة تسجيل الدخول (SignIn)
            startActivity(new Intent(Profile.this, SignIn.class));
            // إنهاء الشاشة الحالية لضمان عدم عودة المستخدم إليها
            finish();
        });
    }

    /**
     * دالة loadUserData: تقوم بجلب البيانات من مسار المستخدم في Firebase Realtime Database.
     */
    private void loadUserData() {
        // التحقق من وجود مستخدم مسجل حالياً
        if (auth.getCurrentUser() == null) return;
        
        // الحصول على المعرف الفريد للمستخدم (UID)
        String uid = auth.getCurrentUser().getUid();

        /**
         * الدخول لمسار: users -> [User_UID] -> profile
         * واستخدام addListenerForSingleValueEvent لقراءة البيانات لمرة واحدة فقط.
         */
        dbRef.child("users").child(uid).child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    /**
                     * يتم استدعاؤها عند نجاح الوصول للبيانات.
                     * @param snapshot كائن يحتوي على البيانات المستلمة.
                     */
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // التأكد من أن المسار يحتوي على بيانات فعلاً
                        if (snapshot.exists()) {
                            // استخراج القيم من الـ Snapshot بناءً على أسماء الحقول في Firebase
                            String name = snapshot.child("name").getValue(String.class);
                            String gender = snapshot.child("gender").getValue(String.class);
                            Double height = snapshot.child("height").getValue(Double.class);
                            Double weight = snapshot.child("weight").getValue(Double.class);
                            Long age = snapshot.child("age").getValue(Long.class);

                            // تحديث نصوص الواجهة بالبيانات المستلمة (مع التحقق من عدم وجود قيم فارغة)
                            tvName.setText(name != null ? name : "اسم المستخدم");
                            tvSubtitle.setText(gender != null ? gender : "متحمس للياقة البدنية");
                            tvHeightValue.setText(height != null ? height + " سم" : "--");
                            tvWeightValue.setText(weight != null ? weight + " كجم" : "--");
                            tvAgeValue.setText(age != null ? age.toString() : "--");
                        }
                    }

                    /**
                     * يتم استدعاؤها في حال حدوث خطأ أثناء جلب البيانات (مثلاً مشكلة اتصال).
                     */
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // هنا يمكننا تسجيل الخطأ أو عرض رسالة تنبيه للمستخدم
                    }
                });
    }
}
