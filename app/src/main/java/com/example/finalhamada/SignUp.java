/**
 * الحزمة الأساسية التي تحتوي هذا الكلاس.
 */
package com.example.finalhamada;

import android.content.Intent; // Intent: يستخدم للانتقال بين الـ Activities.
import android.os.Bundle; // Bundle: يحمل بيانات حالة الشاشة.
import android.util.Log; // Log: لطباعة رسائل Debug في Logcat.
import android.util.Patterns; // Patterns: يحتوي Regex جاهز للتحقق من صحة الإيميل.
import android.widget.Button; // Button: زر في الواجهة.
import android.widget.EditText; // EditText: حقل إدخال نص.
import android.widget.Toast; // Toast: رسالة قصيرة تظهر للمستخدم.

import androidx.annotation.NonNull; // @NonNull: تأكيد أن القيمة لا يمكن أن تكون null.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity.

import com.example.finalhamada.data.MyFitTrackTable.FitTrack;
// FitTrack: كلاس Model يمثل بيانات المستخدم التي سيتم تخزينها في قاعدة البيانات.

import com.google.firebase.auth.FirebaseAuth;
// FirebaseAuth: مسؤول عن إنشاء الحسابات وتسجيل الدخول.

import com.google.firebase.auth.FirebaseUser;
// FirebaseUser: يمثل المستخدم الحالي بعد تسجيله.

import com.google.firebase.database.DatabaseReference;
// DatabaseReference: مرجع يشير إلى موقع معين داخل Realtime Database.

import com.google.firebase.database.FirebaseDatabase;
// FirebaseDatabase: نقطة الدخول للتعامل مع Realtime Database.

/**
 * ============================================================
 * SignUp Activity
 * ============================================================
 * شاشة إنشاء حساب جديد باستخدام:
 * - Firebase Authentication (للمصادقة)
 * - Firebase Realtime Database (لتخزين بيانات المستخدم)
 *
 * الفكرة الأساسية:
 * 1- إنشاء الحساب في Authentication
 * 2- أخذ UID الخاص بالمستخدم
 * 3- تخزين بيانات إضافية في Realtime Database تحت نفس UID
 * ============================================================
 */
public class SignUp extends AppCompatActivity {

    /**
     * TAG يستخدم في Logcat لتمييز رسائل هذا الكلاس.
     */
    private static final String TAG = "SignUpActivity";

    // ==========================
    // عناصر واجهة المستخدم (UI)
    // ==========================

    /** إدخال اسم المستخدم */
    private EditText etName;

    /** إدخال البريد الإلكتروني */
    private EditText etEmail;

    /** إدخال كلمة المرور */
    private EditText etPassword;

    /** تأكيد كلمة المرور */
    private EditText etConfirmPassword;

    /** زر إنشاء الحساب */
    private Button btnRegister;

    // ==========================
    // خدمات Firebase
    // ==========================

    /**
     * FirebaseAuth:
     * مسؤول عن إنشاء الحساب في Firebase Authentication.
     */
    private FirebaseAuth auth;

    /**
     * DatabaseReference:
     * مرجع لقاعدة Realtime Database.
     * سنستخدمه لحفظ بيانات المستخدم.
     */
    private DatabaseReference realtime_db;

    /**
     * onCreate:
     * يتم استدعاؤها عند إنشاء الشاشة.
     * - ربط XML
     * - تهيئة Firebase
     * - إعداد ClickListener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط ملف XML مع هذه الشاشة
        setContentView(R.layout.activity_sign_up);

        // ربط عناصر الواجهة
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        /**
         * تهيئة Firebase Authentication.
         * getInstance() يرجع نسخة واحدة مشتركة (Singleton).
         */
        auth = FirebaseAuth.getInstance();

        /**
         * تهيئة Firebase Realtime Database.
         * getReference() يرجع مرجع للجذر (Root) في قاعدة البيانات.
         */
        realtime_db = FirebaseDatabase.getInstance().getReference();

        /**
         * ClickListener لزر Register.
         * عند الضغط:
         * 1- يتم التحقق من صحة البيانات (Validation)
         * 2- إنشاء الحساب في Firebase Authentication
         * 3- حفظ البيانات في Realtime Database
         */
        btnRegister.setOnClickListener(v -> {

            if (validateAndReadData()) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * createUserWithEmailAndPassword:
                 * ---------------------------------
                 * - ينشئ حساب جديد في Firebase Authentication.
                 * - العملية غير متزامنة (Asynchronous).
                 * - تعيد Task<AuthResult>.
                 */
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            if (task.isSuccessful()) {

                                /**
                                 * عند نجاح إنشاء الحساب:
                                 * نحصل على المستخدم الحالي.
                                 */
                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                if (firebaseUser != null) {

                                    /**
                                     * إنشاء كائن Model يحتوي بيانات المستخدم.
                                     * FitTrack هو كلاس يمثل جدول المستخدم.
                                     */
                                    FitTrack userProfileData = new FitTrack();
                                    userProfileData.setName(name);

                                    /**
                                     * حفظ البيانات في Realtime Database
                                     * باستخدام UID كمفتاح رئيسي.
                                     */
                                    saveUser(firebaseUser.getUid(), userProfileData);
                                }

                            } else {

                                /**
                                 * في حال فشل إنشاء الحساب:
                                 * getException() يعيد سبب الخطأ.
                                 */
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
     * saveUser:
     * ---------------------------------
     * يحفظ بيانات المستخدم في Realtime Database بالشكل التالي:
     *
     * users
     *   └── uid
     *         └── name: "..."
     *
     * @param uid المعرف الفريد للمستخدم من FirebaseAuth
     * @param trackData كائن يحتوي بيانات المستخدم
     */
    public void saveUser(String uid, FitTrack trackData) {

        realtime_db.child("users")   // الدخول إلى عقدة users
                .child(uid)          // إنشاء child باسم UID
                .setValue(trackData) // تخزين البيانات داخلها

                /**
                 * عند نجاح الحفظ.
                 */
                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(SignUp.this,
                            "User added successfully",
                            Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "User saved successfully: " + uid);

                    // الانتقال للشاشة التالية
                    startActivity(new Intent(SignUp.this, AboutYourself.class));
                    finish();
                })

                /**
                 * عند فشل الحفظ.
                 */
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
     * validateAndReadData:
     * ---------------------------------
     * تتحقق من:
     * - الاسم غير فارغ
     * - الإيميل صحيح باستخدام Patterns
     * - كلمة المرور ≥ 6 أحرف
     * - تطابق كلمة المرور مع التأكيد
     *
     * @return true إذا البيانات صحيحة،
     *         false إذا يوجد خطأ.
     */
    private boolean validateAndReadData() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValid = true;

        /**
         * التحقق من الاسم.
         */
        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            isValid = false;
        }

        /**
         * التحقق من صحة البريد الإلكتروني باستخدام Regex جاهز.
         */
        if (email.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            isValid = false;
        }

        /**
         * التحقق من طول كلمة المرور.
         * Firebase يتطلب 6 أحرف على الأقل.
         */
        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            isValid = false;
        }

        /**
         * التحقق من تطابق كلمة المرور مع التأكيد.
         */
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords don't match");
            etConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}