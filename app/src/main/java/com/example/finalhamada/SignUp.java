package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalhamada.data.MyFitTrackTable.FitTrack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * ============================================================
 * SignUp Activity
 * ============================================================
 * شاشة إنشاء حساب جديد باستخدام:
 * - Firebase Authentication
 * - Firebase Realtime Database
 *
 * الوظائف الرئيسية:
 * 1️⃣ إدخال بيانات المستخدم (الاسم - البريد - كلمة المرور)
 * 2️⃣ التحقق من صحة البيانات المدخلة
 * 3️⃣ إنشاء حساب جديد في Firebase Authentication
 * 4️⃣ حفظ بيانات المستخدم في Realtime Database
 * 5️⃣ الانتقال إلى شاشة AboutYourself بعد نجاح التسجيل
 *
 * ملاحظات:
 * - Authentication مسؤول عن المصادقة فقط
 * - Realtime Database مسؤول عن تخزين بيانات المستخدم
 * ============================================================
 */
public class SignUp extends AppCompatActivity {

    /**
     * TAG
     * --------------------------------------------------
     * يستخدم لعرض رسائل Debug داخل Logcat
     */
    private static final String TAG = "SignUpActivity";

    // ==========================
    // عناصر واجهة المستخدم (UI)
    // ==========================

    private EditText etName;              // إدخال الاسم
    private EditText etEmail;             // إدخال البريد الإلكتروني
    private EditText etPassword;          // إدخال كلمة المرور
    private EditText etConfirmPassword;   // تأكيد كلمة المرور
    private Button btnRegister;           // زر إنشاء الحساب

    // ==========================
    // خدمات Firebase
    // ==========================

    /**
     * FirebaseAuth:
     * مسؤول عن إنشاء الحسابات والمصادقة
     */
    private FirebaseAuth auth;

    /**
     * DatabaseReference:
     * مرجع لقاعدة Realtime Database
     */
    private DatabaseReference realtime_db;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة
     * يتم فيها:
     * - ربط عناصر الواجهة
     * - تهيئة Firebase
     * - إعداد زر التسجيل
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // ربط عناصر XML
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // تهيئة Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // تهيئة Realtime Database
        realtime_db = FirebaseDatabase.getInstance().getReference();

        /**
         * عند الضغط على Register:
         * 1️⃣ التحقق من البيانات
         * 2️⃣ إنشاء الحساب
         * 3️⃣ حفظ بيانات المستخدم
         */
        btnRegister.setOnClickListener(v -> {

            if (validateAndReadData()) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * createUserWithEmailAndPassword
                 * --------------------------------------------------
                 * ينشئ حساب جديد في Firebase
                 */
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                if (firebaseUser != null) {

                                    // إنشاء كائن بيانات المستخدم
                                    FitTrack userProfileData = new FitTrack();
                                    userProfileData.setName(name);

                                    // حفظ البيانات في Realtime Database
                                    saveUser(firebaseUser.getUid(), userProfileData);
                                }

                            } else {

                                Toast.makeText(SignUp.this,
                                        "Registration failed: "
                                                + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();

                                Log.e(TAG, "Auth Error: "
                                        + task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * saveUser
     * --------------------------------------------------
     * يحفظ بيانات المستخدم داخل:
     * Realtime Database → users → UID
     *
     * @param uid المعرف الفريد للمستخدم
     * @param trackData بيانات المستخدم
     */
    public void saveUser(String uid, FitTrack trackData) {

        realtime_db.child("users")
                .child(uid)
                .setValue(trackData)

                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(SignUp.this,
                            "User added successfully",
                            Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "User saved successfully: " + uid);

                    // الانتقال للشاشة التالية
                    startActivity(new Intent(SignUp.this, AboutYourself.class));
                    finish();
                })

                .addOnFailureListener(e -> {

                    Log.e(TAG, "Database Error: "
                            + e.getMessage(), e);

                    Toast.makeText(SignUp.this,
                            "Failed to save user data: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }

    /**
     * validateAndReadData
     * --------------------------------------------------
     * تتحقق من:
     * - الاسم غير فارغ
     * - البريد صحيح
     * - كلمة المرور 6 أحرف على الأقل
     * - تطابق كلمة المرور
     *
     * @return true إذا البيانات صحيحة
     */
    private boolean validateAndReadData() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValid = true;

        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            isValid = false;
        }

        if (email.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            isValid = false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords don't match");
            etConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
