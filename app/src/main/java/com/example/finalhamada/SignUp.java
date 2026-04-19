package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ حالة الشاشة عند إنشائها.
import android.util.Log; // كلاس Log لطباعة الرسائل البرمجية في نافذة الـ Logcat.
import android.util.Patterns; // كلاس Patterns للتحقق من أنماط النصوص (مثل الإيميل).
import android.widget.Button; // تمثيل لزر الضغط في الواجهة.
import android.widget.EditText; // تمثيل لحقل إدخال النص.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة.

import androidx.annotation.NonNull; // وسام للتأكيد على أن القيمة لا يمكن أن تكون null.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.

import com.example.finalhamada.data.MyFitTrackTable.FitTrack; // كلاس النموذج (Model) لبيانات المستخدم.
import com.google.firebase.auth.FirebaseAuth; // نظام المصادقة في Firebase.
import com.google.firebase.auth.FirebaseUser; // يمثل المستخدم المسجل حالياً في Firebase.
import com.google.firebase.database.DatabaseReference; // مرجع للوصول لمكان محدد في قاعدة البيانات.
import com.google.firebase.database.FirebaseDatabase; // الوصول لقاعدة بيانات Firebase Realtime.

/**
 * SignUp Activity: شاشة إنشاء حساب جديد.
 * ---------------------------------------------------------
 * تقوم هذه الشاشة بتمكين المستخدم من:
 * 1. إنشاء حساب جديد باستخدام البريد الإلكتروني وكلمة المرور عبر Firebase Auth.
 * 2. تخزين بيانات إضافية (مثل الاسم) في قاعدة بيانات Firebase Realtime.
 * 3. التحقق من صحة المدخلات وتطابق كلمات المرور.
 */
public class SignUp extends AppCompatActivity {

    // علامة لتمييز سجلات النظام لهذه الشاشة
    private static final String TAG = "SignUpActivity";

    // === عناصر واجهة المستخدم (UI Elements) ===
    private EditText etName;           // حقل إدخال الاسم
    private EditText etEmail;          // حقل إدخال البريد الإلكتروني
    private EditText etPassword;       // حقل إدخال كلمة المرور
    private EditText etConfirmPassword; // حقل تأكيد كلمة المرور
    private Button btnRegister;        // زر تنفيذ عملية التسجيل

    // === كائنات Firebase ===
    private FirebaseAuth auth;              // كائن نظام المصادقة
    private DatabaseReference realtime_db;   // مرجع قاعدة البيانات السحابية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_sign_up.xml بهذا الكود
        setContentView(R.layout.activity_sign_up);

        // --- ربط المتغيرات بالمعرفات (IDs) من ملف الـ XML ---
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // تهيئة نظام مصادقة Firebase
        auth = FirebaseAuth.getInstance();

        // تهيئة الوصول لقاعدة البيانات السحابية (Realtime Database)
        realtime_db = FirebaseDatabase.getInstance().getReference();

        // --- ضبط حدث النقر على زر التسجيل ---
        btnRegister.setOnClickListener(v -> {

            // التحقق أولاً من صحة البيانات المدخلة في الحقول
            if (validateAndReadData()) {

                // استخراج النصوص من الحقول
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * محاولة إنشاء مستخدم جديد في Firebase Authentication.
                 */
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            // إذا تم إنشاء الحساب بنجاح في نظام المصادقة
                            if (task.isSuccessful()) {

                                // الحصول على كائن المستخدم الحالي المنشأ
                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                if (firebaseUser != null) {
                                    // إعداد كائن البيانات لحفظه في قاعدة البيانات
                                    FitTrack userProfileData = new FitTrack();
                                    userProfileData.setName(name);

                                    // استدعاء دالة حفظ البيانات في قاعدة البيانات السحابية
                                    saveUser(firebaseUser.getUid(), userProfileData);
                                }

                            } else {
                                // في حال فشل إنشاء الحساب (مثلاً: الإيميل مستخدم مسبقاً)
                                Toast.makeText(SignUp.this, "فشل التسجيل: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.e(TAG, "خطأ في المصادقة: " + task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * دالة saveUser: لحفظ بيانات المستخدم الإضافية في Firebase Realtime Database.
     * @param uid المعرف الفريد للمستخدم (Unique ID) من نظام المصادقة.
     * @param trackData كائن البيانات المراد حفظه.
     */
    public void saveUser(String uid, FitTrack trackData) {

        // التوجه إلى مسار "users" ثم المجلد الخاص بالمستخدم (UID)
        realtime_db.child("users")
                .child(uid)
                .setValue(trackData) // وضع البيانات داخل المسار
                .addOnSuccessListener(aVoid -> {
                    // في حال نجاح الحفظ في قاعدة البيانات
                    Toast.makeText(SignUp.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "تم حفظ بيانات المستخدم: " + uid);

                    // الانتقال لشاشة "AboutYourself" لإكمال إعداد الملف الشخصي
                    startActivity(new Intent(SignUp.this, AboutYourself.class));
                    finish(); // إغلاق شاشة التسجيل
                })
                .addOnFailureListener(e -> {
                    // في حال فشل الاتصال بقاعدة البيانات أو الحفظ
                    Log.e(TAG, "خطأ في قاعدة البيانات: " + e.getMessage());
                    Toast.makeText(SignUp.this, "فشل حفظ البيانات: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    /**
     * دالة validateAndReadData: للتحقق من صحة المدخلات في الحقول قبل الإرسال.
     * @return true إذا كانت البيانات سليمة، false خلاف ذلك.
     */
    private boolean validateAndReadData() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValid = true;

        // التحقق من الاسم
        if (name.isEmpty()) {
            etName.setError("الاسم مطلوب");
            etName.requestFocus();
            isValid = false;
        }

        // التحقق من تنسيق البريد الإلكتروني
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("أدخل بريد إلكتروني صحيح");
            etEmail.requestFocus();
            isValid = false;
        }

        // التحقق من طول كلمة المرور (6 خانات كحد أدنى)
        if (password.length() < 6) {
            etPassword.setError("كلمة المرور يجب أن تكون 6 خانات على الأقل");
            etPassword.requestFocus();
            isValid = false;
        }

        // التحقق من تطابق كلمة المرور مع حقل التأكيد
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("كلمات المرور غير متطابقة");
            etConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
