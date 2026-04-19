package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات البرمجية (Activities).
import android.os.Bundle; // كلاس Bundle يستخدم لتمرير البيانات وحفظ حالة الشاشة عند إنشائها.
import android.util.Log; // كلاس Log لطباعة الرسائل البرمجية في نافذة الـ Logcat لتسهيل عملية التصحيح.
import android.util.Patterns; // كلاس Patterns يوفر أنماطاً جاهزة للتحقق من النصوص مثل (الإيميل، أرقام الهواتف).
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.EditText; // تمثيل لحقل إدخال النص القابل للتعديل من قِبل المستخدم.
import android.widget.TextView; // تمثيل لعنصر عرض النص الثابت أو المتغير في الواجهة.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة أسفل الشاشة.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي الذي يجب أن ترث منه أي شاشة لضمان التوافقية.

import com.google.firebase.auth.FirebaseAuth; // الكلاس الرئيسي للتعامل مع نظام المصادقة في Firebase.
import com.google.firebase.auth.FirebaseUser; // كلاس يمثل المستخدم الحالي المسجل دخوله في النظام.

/**
 * SignIn Activity: شاشة تسجيل الدخول.
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدمين المسجلين مسبقاً الدخول إلى حساباتهم
 * باستخدام البريد الإلكتروني وكلمة المرور عبر خدمة Firebase Authentication.
 */
public class SignIn extends AppCompatActivity {

    // === تعريف متغيرات عناصر واجهة المستخدم (UI Elements) ===
    
    // حقول إدخال البيانات
    private EditText etEmail; // حقل البريد الإلكتروني
    private EditText etPassword; // حقل كلمة المرور
    
    // زر تنفيذ عملية الدخول
    private Button btnLogin;
    
    // نصوص قابلة للنقر للانتقال لعمليات أخرى (التسجيل أو المساعدة)
    private TextView tvRegister, tvaccount;

    // === كائن نظام المصادقة (Firebase Auth) ===
    private FirebaseAuth auth;

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_sign_in.xml بهذا الكود البرمجي
        setContentView(R.layout.activity_sign_in);

        // --- ربط المتغيرات بالمعرفات (IDs) الموجودة في ملف الـ XML ---
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        // الحصول على نسخة (Instance) من نظام مصادقة Firebase
        auth = FirebaseAuth.getInstance();

        // --- إعداد المستمعات للأحداث (Event Listeners) ---

        /**
         * عند الضغط على نص "Register": يتم نقله لشاشة إنشاء حساب جديد (SignUp).
         */
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(SignIn.this, SignUp.class)));

        /**
         * عند الضغط على زر "Login": تبدأ عملية التحقق من البيانات ثم تسجيل الدخول.
         */
        btnLogin.setOnClickListener(v -> {

            // استدعاء دالة التحقق من صحة المدخلات (Validation)
            if (validateAndLogin()) {

                // استخراج النصوص من الحقول وحذف المسافات الزائدة
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 * استخدام Firebase لمحاولة تسجيل الدخول بالبريد وكلمة المرور.
                 * يتم إرسال طلب لخوادم Firebase والانتظار حتى انتهاء المهمة (Task).
                 */
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            // التحقق مما إذا كانت المهمة قد تمت بنجاح
                            if (task.isSuccessful()) {

                                // عرض رسالة نجاح للمستخدم
                                Toast.makeText(SignIn.this, "تم تسجيل الدخول بنجاح!", Toast.LENGTH_SHORT).show();

                                // الانتقال إلى شاشة "AboutYourself" لبدء إعداد بيانات الجسم
                                startActivity(new Intent(SignIn.this, AboutYourself.class));

                                // إنهاء شاشة تسجيل الدخول حتى لا يعود إليها المستخدم عند الضغط على زر الرجوع
                                finish();

                            } else {

                                // في حال الفشل: عرض سبب الخطأ القادم من خادم Firebase
                                Toast.makeText(SignIn.this, "فشل الدخول: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                // طباعة تفاصيل الخطأ في الـ Logcat للمطور
                                Log.e("SignIn_Error", task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * دالة onStart: يتم استدعاؤها عندما تصبح الشاشة مرئية.
     * نستخدمها للتحقق من "الجلسة النشطة" (Auto Login).
     */
    @Override
    protected void onStart() {
        super.onStart();

        // الحصول على المستخدم الحالي (إذا كان مسجلاً دخوله مسبقاً)
        FirebaseUser currentUser = auth.getCurrentUser();

        // إذا وُجد مستخدم مسجل مسبقاً، يتم نقله تلقائياً لتخطي شاشة الدخول
        if (currentUser != null) {
            startActivity(new Intent(SignIn.this, AboutYourself.class));
            finish();
        }
    }

    /**
     * دالة validateAndLogin: للتحقق من صحة البيانات المدخلة قبل إرسال الطلب للخادم.
     * @return true إذا كانت البيانات مطابقة للشروط، false خلاف ذلك.
     */
    private boolean validateAndLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        // 1. التحقق من أن حقل الإيميل ليس فارغاً ويتبع التنسيق الصحيح (name@domain.com)
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("يرجى إدخال بريد إلكتروني صحيح"); // عرض علامة خطأ داخل الحقل
            isValid = false;
        }

        // 2. التحقق من أن كلمة المرور ليست فارغة ولا تقل عن 6 خانات (شرط Firebase)
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("يجب أن تكون كلمة المرور 6 خانات على الأقل");
            isValid = false;
        }

        return isValid; // إرجاع النتيجة النهائية للفحص
    }
}
