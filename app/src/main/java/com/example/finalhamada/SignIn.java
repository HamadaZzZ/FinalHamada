package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * SignIn Activity
 * --------------------------------------------------
 * شاشة تسجيل الدخول باستخدام Firebase Authentication
 *
 * الوظائف:
 * 1️⃣ التحقق من صحة الإيميل وكلمة المرور
 * 2️⃣ تسجيل دخول المستخدم
 * 3️⃣ فحص إذا المستخدم مسجل دخول مسبقًا
 * 4️⃣ تحويل المستخدم للشاشة التالية
 */
public class SignIn extends AppCompatActivity {

    // حقل إدخال البريد الإلكتروني
    private EditText etEmail;

    // حقل إدخال كلمة المرور
    private EditText etPassword;

    // زر تسجيل الدخول
    private Button btnLogin;

    // نص للانتقال إلى شاشة التسجيل
    private TextView tvRegister, tvaccount;

    /**
     * كائن FirebaseAuth
     * --------------------------------------------------
     * هذا الكائن هو المسؤول عن:
     * - تسجيل الدخول
     * - تسجيل الخروج
     * - معرفة حالة المستخدم (مسجل دخول أو لا)
     */
    private FirebaseAuth auth;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة لأول مرة
     * نربط فيها الواجهة ونجهّز Firebase
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط ملف الواجهة activity_sign_in.xml
        setContentView(R.layout.activity_sign_in);

        // ربط عناصر الواجهة مع الجافا
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        /**
         * تهيئة FirebaseAuth
         * --------------------------------------------------
         * FirebaseAuth.getInstance():
         * - ينشئ اتصال مع Firebase Authentication
         * - يستخدم الجلسة المحفوظة تلقائيًا (إن وُجدت)
         */
        auth = FirebaseAuth.getInstance();

        // عند الضغط على "Register" ننتقل لشاشة SignUp
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(SignIn.this, SignUp.class)));

        /**
         * زر تسجيل الدخول
         * --------------------------------------------------
         * عند الضغط:
         * 1️⃣ نتحقق من صحة المدخلات
         * 2️⃣ نرسلها لـ Firebase
         */
        btnLogin.setOnClickListener(v -> {

            // التحقق من صحة الإيميل وكلمة المرور
            if (validateAndLogin()) {

                // أخذ القيم من الحقول
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * تسجيل الدخول باستخدام Firebase
                 * --------------------------------------------------
                 * signInWithEmailAndPassword:
                 * - يرسل الإيميل وكلمة المرور إلى Firebase
                 * - يفحصهم في السيرفر
                 * - يرجع نتيجة (نجاح / فشل)
                 */
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            // إذا نجحت عملية تسجيل الدخول
                            if (task.isSuccessful()) {

                                // رسالة نجاح
                                Toast.makeText(SignIn.this,
                                        "Login Successful!",
                                        Toast.LENGTH_SHORT).show();

                                // الانتقال إلى الشاشة التالية
                                startActivity(new Intent(SignIn.this, AboutYourself.class));

                                // إغلاق شاشة تسجيل الدخول
                                finish();

                            } else {

                                /**
                                 * في حال الفشل
                                 * --------------------------------------------------
                                 * task.getException():
                                 * - يحتوي سبب الخطأ (كلمة مرور غلط، مستخدم غير موجود، إلخ)
                                 */
                                Toast.makeText(SignIn.this,
                                        "Login failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();

                                // طباعة الخطأ في Logcat
                                Log.e("SignIn", task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * onStart
     * --------------------------------------------------
     * تُستدعى كل مرة تظهر الشاشة للمستخدم
     *
     * أهم دالة لفحص:
     * هل المستخدم مسجل دخول مسبقًا؟
     */
    @Override
    protected void onStart() {
        super.onStart();

        /**
         * الحصول على المستخدم الحالي
         * --------------------------------------------------
         * getCurrentUser():
         * - يرجع FirebaseUser إذا كان في جلسة محفوظة
         * - يرجع null إذا لم يكن المستخدم مسجل دخول
         */
        FirebaseUser currentUser = auth.getCurrentUser();

        // إذا المستخدم مسجل دخول مسبقًا
        if (currentUser != null) {

            // تحويل مباشر للشاشة التالية
            startActivity(new Intent(SignIn.this, AboutYourself.class));

            // إغلاق شاشة تسجيل الدخول
            finish();
        }
    }

    /**
     * validateAndLogin
     * --------------------------------------------------
     * تتحقق من:
     * - صحة البريد الإلكتروني
     * - طول كلمة المرور
     *
     * @return true إذا البيانات صحيحة
     */
    private boolean validateAndLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isValid = true;

        // فحص الإيميل
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            isValid = false;
        }

        // فحص كلمة المرور
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }
}
