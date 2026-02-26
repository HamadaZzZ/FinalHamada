/**
 * حزمة التطبيق الأساسية التي تحتوي هذا الكلاس.
 */
package com.example.finalhamada;

import android.content.Intent; // Intent: يستخدم للتنقل بين الـ Activities داخل التطبيق.
import android.os.Bundle; // Bundle: يحمل بيانات حالة الـ Activity عند إنشائها.
import android.util.Log; // Log: يستخدم لطباعة رسائل في Logcat لتتبع الأخطاء.
import android.util.Patterns; // Patterns: يحتوي أنماط جاهزة مثل التحقق من صحة البريد الإلكتروني.
import android.widget.Button; // Button: عنصر زر في واجهة المستخدم.
import android.widget.EditText; // EditText: حقل لإدخال النص من المستخدم.
import android.widget.TextView; // TextView: عنصر لعرض نص في الواجهة.
import android.widget.Toast; // Toast: رسالة قصيرة تظهر للمستخدم.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity مع دعم الميزات الحديثة.

import com.google.firebase.auth.FirebaseAuth; // FirebaseAuth: مسؤول عن عمليات تسجيل الدخول/الخروج.
import com.google.firebase.auth.FirebaseUser; // FirebaseUser: يمثل المستخدم الحالي في Firebase.

/**
 * شاشة تسجيل الدخول (SignIn Activity).
 * <p>
 * هذه الشاشة مسؤولة عن:
 * - استقبال البريد الإلكتروني وكلمة المرور من المستخدم.
 * - التحقق من صحة البيانات (Validation).
 * - إرسال طلب تسجيل الدخول إلى Firebase Authentication.
 * - تحويل المستخدم للشاشة التالية عند النجاح.
 * </p>
 */
public class SignIn extends AppCompatActivity {

    /** حقل إدخال البريد الإلكتروني */
    private EditText etEmail;

    /** حقل إدخال كلمة المرور */
    private EditText etPassword;

    /** زر تسجيل الدخول */
    private Button btnLogin;

    /** رابط الانتقال إلى شاشة التسجيل */
    private TextView tvRegister, tvaccount;

    /**
     * كائن FirebaseAuth المسؤول عن المصادقة.
     * يتم استخدامه لإجراء عمليات:
     * - signInWithEmailAndPassword
     * - getCurrentUser
     */
    private FirebaseAuth auth;

    /**
     * يتم استدعاؤها عند إنشاء الشاشة لأول مرة.
     * مسؤولة عن:
     * - ربط ملف XML
     * - تهيئة العناصر
     * - إعداد Click Listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط واجهة XML مع هذه الشاشة
        setContentView(R.layout.activity_sign_in);

        // ربط عناصر الواجهة بالمتغيرات
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        /**
         * تهيئة Firebase Authentication.
         * getInstance() ترجع نفس النسخة المستخدمة في كامل التطبيق (Singleton).
         */
        auth = FirebaseAuth.getInstance();

        /**
         * Click Listener لعنصر Register.
         * عند الضغط يتم فتح شاشة SignUp باستخدام Intent.
         */
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(SignIn.this, SignUp.class)));

        /**
         * Click Listener لزر تسجيل الدخول.
         * عند الضغط:
         * 1- يتم تنفيذ Validation.
         * 2- إذا البيانات صحيحة يتم إرسال طلب تسجيل الدخول إلى Firebase.
         */
        btnLogin.setOnClickListener(v -> {

            if (validateAndLogin()) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * Firebase Authentication - تسجيل الدخول.
                 *
                 * signInWithEmailAndPassword:
                 * - يرسل طلب إلى خوادم Firebase.
                 * - العملية غير متزامنة (Asynchronous).
                 * - ترجع Task<AuthResult>.
                 *
                 * addOnCompleteListener:
                 * - Listener يتم تنفيذه بعد انتهاء العملية (نجاح أو فشل).
                 */
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            /**
                             * إذا كانت عملية تسجيل الدخول ناجحة.
                             */
                            if (task.isSuccessful()) {

                                Toast.makeText(SignIn.this,
                                        "Login Successful!",
                                        Toast.LENGTH_SHORT).show();

                                // الانتقال إلى الشاشة التالية
                                startActivity(new Intent(SignIn.this, AboutYourself.class));

                                // إنهاء هذه الشاشة لمنع الرجوع إليها بزر Back
                                finish();

                            } else {

                                /**
                                 * في حال فشل تسجيل الدخول:
                                 * getException() ترجع سبب الخطأ القادم من Firebase.
                                 */
                                Toast.makeText(SignIn.this,
                                        "Login failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();

                                Log.e("SignIn", task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * يتم استدعاؤها عندما تصبح الشاشة مرئية للمستخدم.
     * نستخدمها للتحقق إذا كان هناك مستخدم مسجل دخول مسبقاً.
     */
    @Override
    protected void onStart() {
        super.onStart();

        /**
         * getCurrentUser():
         * - إذا كان هناك جلسة محفوظة (User Logged In) يرجع FirebaseUser.
         * - إذا لا يوجد يرجع null.
         */
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {

            // تحويل مباشر بدون الحاجة لتسجيل الدخول مرة أخرى
            startActivity(new Intent(SignIn.this, AboutYourself.class));
            finish();
        }
    }

    /**
     * دالة التحقق من صحة المدخلات (Validation).
     *
     * @return true إذا كانت البيانات صحيحة،
     *         false إذا يوجد خطأ.
     *
     * التحقق يشمل:
     * - صحة تنسيق البريد الإلكتروني باستخدام Patterns.
     * - أن كلمة المرور لا تقل عن 6 أحرف.
     */
    private boolean validateAndLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        /**
         * Validation للإيميل:
         * Patterns.EMAIL_ADDRESS:
         * - Regex جاهز من Android لفحص تنسيق البريد الإلكتروني.
         */
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            isValid = false;
        }

        /**
         * Validation لكلمة المرور:
         * Firebase يتطلب 6 أحرف على الأقل.
         */
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }
}