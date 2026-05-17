package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة عند إنشائها.
import android.util.Log; // كلاس Log لطباعة الرسائل البرمجية في نافذة الـ Logcat لتسهيل عملية التتبع والتصحيح.
import android.util.Patterns; // كلاس Patterns يوفر أنماطاً جاهزة للتحقق من النصوص مثل صحة البريد الإلكتروني.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم (UI).
import android.widget.EditText; // تمثيل لحقل إدخال النص الذي يمكن للمستخدم الكتابة فيه.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة أسفل الشاشة لإعلام المستخدم بحدث ما.

import androidx.annotation.NonNull; // وسام للتأكيد برمجياً على أن القيمة المرجعة أو المعامل لا يمكن أن يكون فارغاً (null).
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي الذي يجب أن ترث منه الشاشات لضمان التوافقية مع الإصدارات القديمة.

import com.example.finalhamada.data.MyFitTrackTable.FitTrack; // كلاس النموذج (Model) لتمثيل وحمل بيانات المستخدم الجسدية.
import com.google.firebase.auth.FirebaseAuth; // الكلاس الرئيسي للتعامل مع نظام المصادقة (Authentication) في Firebase.
import com.google.firebase.auth.FirebaseUser; // كلاس يمثل المستخدم الذي سجل دخوله حالياً في نظام Firebase.
import com.google.firebase.database.DatabaseReference; // مرجع يشير لمكان محدد في قاعدة البيانات السحابية لإجراء عمليات القراءة والكتابة.
import com.google.firebase.database.FirebaseDatabase; // نقطة الدخول الرئيسية للتعامل مع قاعدة بيانات Firebase Realtime السحابية.

/**
 * SignUp Activity: شاشة إنشاء حساب مستخدم جديد.
 * ---------------------------------------------------------
 * تقوم هذه الشاشة بتمكين المستخدم من:
 * 1. إدخال بياناته (الاسم، البريد الإلكتروني، كلمة المرور، وتأكيدها).
 * 2. التحقق من صحة هذه البيانات ومطابقتها للشروط البرمجية (Validation).
 * 3. إنشاء حساب جديد في نظام Firebase Authentication للمصادقة.
 * 4. تخزين الاسم والبيانات الأولية في قاعدة بيانات Firebase Realtime السحابية.
 */
public class SignUp extends AppCompatActivity {

    // علامة ثابتة تستخدم عند طباعة السجلات (Logs) لتمييز رسائل هذه الشاشة في الـ Logcat.
    private static final String TAG = "SignUpActivity";

    // === عناصر واجهة المستخدم (UI Elements) ===
    
    // حقول إدخال النص للبيانات المطلوبة
    private EditText etName;           // حقل إدخال الاسم الكامل للمستخدم
    private EditText etEmail;          // حقل إدخال البريد الإلكتروني (Email)
    private EditText etPassword;       // حقل إدخال كلمة المرور (Password)
    private EditText etConfirmPassword; // حقل لإعادة كتابة كلمة المرور للتأكد من تطابقها
    
    // زر تنفيذ عملية التسجيل وإرسال البيانات
    private Button btnRegister;

    // === كائنات خدمات Firebase السحابية ===
    
    // كائن نظام المصادقة للتعامل مع عمليات إنشاء الحسابات
    private FirebaseAuth auth;              
    
    // مرجع قاعدة البيانات السحابية للوصول لمسار حفظ بيانات المستخدمين
    private DatabaseReference realtime_db;   

    /**
     * دالة onCreate: نقطة البداية عند تشغيل الشاشة، يتم فيها تهيئة العناصر والخدمات.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط الكود البرمجي بملف التصميم activity_sign_up.xml
        setContentView(R.layout.activity_sign_up);

        // --- 1. ربط المتغيرات بالمعرفات (IDs) الموجودة في ملف الـ XML ---
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // --- 2. تهيئة خدمات Firebase ---
        
        // الحصول على نسخة (Instance) من نظام مصادقة Firebase
        auth = FirebaseAuth.getInstance();
        
        // الحصول على المرجع الرئيسي لقاعدة بيانات Firebase Realtime السحابية
        realtime_db = FirebaseDatabase.getInstance().getReference();

        // --- 3. إعداد حدث النقر على زر "التسجيل" (Register) ---
        btnRegister.setOnClickListener(v -> {

            // استدعاء دالة التحقق من صحة المدخلات قبل البدء في عملية التواصل مع الخادم
            if (validateAndReadData()) {

                // استخراج النصوص من الحقول وحذف المسافات الزائدة من البداية والنهاية
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                /**
                 createUserWithEmailAndPassword
                 تستخدم لإنشاء حساب مستخدم جديد
                 باستخدام البريد الإلكتروني وكلمة المرور
                 داخل Firebase Authentication.

                 إذا نجحت العملية،
                 تقوم Firebase بإنشاء مستخدم جديد
                 وإعطائه UID خاص به.

                 دالة saveUser
                 مسؤولة عن حفظ بيانات المستخدم
                 داخل Firebase Realtime Database
                 باستخدام UID الخاص بالمستخدم.

                 هذا يسمح بحفظ بيانات كل مستخدم
                 في مكان منفصل داخل قاعدة البيانات
                 حتى لا تختلط البيانات بين المستخدمين.
                 */
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            // التحقق مما إذا كانت عملية إنشاء الحساب قد تمت بنجاح
                            if (task.isSuccessful()) {

                                // الحصول على كائن المستخدم الحالي الذي تم إنشاؤه بنجاح
                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                if (firebaseUser != null) {
                                    // إعداد كائن البيانات (Model) لحفظه في قاعدة البيانات السحابية (Realtime Database)
                                    FitTrack userProfileData = new FitTrack();
                                    userProfileData.setName(name); // حفظ الاسم المدخل في حقل الاسم

                                    // استدعاء دالة مخصصة لحفظ البيانات في قاعدة البيانات السحابية باستخدام الـ UID
                                    saveUser(firebaseUser.getUid(), userProfileData);
                                }

                            } else {
                                // في حال فشل إنشاء الحساب (مثلاً: الإيميل موجود مسبقاً أو لا يوجد إنترنت)
                                Toast.makeText(SignUp.this, "فشل التسجيل: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                // طباعة تفاصيل الخطأ في الـ Logcat لتسهيل التصحيح
                                Log.e(TAG, "خطأ في عملية المصادقة: " + task.getException().getMessage());
                            }
                        });
            }
        });
    }

    /**
     * دالة saveUser: مسؤولة عن حفظ بيانات المستخدم الإضافية في Firebase Realtime Database.
     * @param uid المعرف الفريد للمستخدم (Unique ID) القادم من نظام المصادقة.
     * @param trackData كائن البيانات (FitTrack) الذي يحتوي على الاسم والبيانات المبدئية.
     */
    public void saveUser(String uid, FitTrack trackData) {

        /**
         * التوجه إلى مسار "users" في قاعدة البيانات، ثم إنشاء مجلد فرعي بالمعرف الفريد (UID) للمستخدم.
         * هذا يضمن أن لكل مستخدم مكاناً خاصاً ببياناته لا يتداخل مع الآخرين.
         */
        realtime_db.child("users")
                .child(uid)
                .setValue(trackData) // وضع كائن البيانات بالكامل داخل هذا المسار
                .addOnSuccessListener(aVoid -> {
                    // يتم تنفيذ هذا الجزء عند نجاح كتابة البيانات في السحابة
                    Toast.makeText(SignUp.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "تم بنجاح حفظ بيانات المستخدم تحت المعرف: " + uid);

                    // الانتقال التلقائي لشاشة "حدثنا عن نفسك" (AboutYourself) لإكمال الملف الشخصي
                    startActivity(new Intent(SignUp.this, AboutYourself.class));
                    
                    // إنهاء شاشة التسجيل لضمان عدم عودة المستخدم إليها عند ضغط زر الرجوع
                    finish(); 
                })
                .addOnFailureListener(e -> {
                    // يتم تنفيذ هذا الجزء في حال حدوث خطأ أثناء محاولة الكتابة في قاعدة البيانات
                    Log.e(TAG, "خطأ في قاعدة البيانات السحابية: " + e.getMessage());
                    Toast.makeText(SignUp.this, "فشل في حفظ البيانات: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    /**
     * دالة validateAndReadData: للتحقق من أن جميع الحقول ممتلئة بشكل صحيح وتتبع الشروط المطلوبة.
     * @return true إذا كانت جميع البيانات سليمة، false في حال وجود أي خطأ.
     */
    private boolean validateAndReadData() {

        // جلب النصوص الحالية من الحقول
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValid = true;

        // --- 1. التحقق من حقل الاسم ---
        if (name.isEmpty()) {
            etName.setError("الاسم مطلوب"); // إظهار رسالة خطأ داخل الحقل
            etName.requestFocus(); // وضع المؤشر على هذا الحقل لينتبه المستخدم
            isValid = false;
        }

        // --- 2. التحقق من تنسيق البريد الإلكتروني ---
        // نستخدم Patterns.EMAIL_ADDRESS للتأكد من وجود @ ونقطة وغيرها من رموز الإيميل
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("يرجى إدخال بريد إلكتروني صحيح");
            etEmail.requestFocus();
            isValid = false;
        }

        // --- 3. التحقق من طول كلمة المرور ---
        // نظام Firebase يتطلب أن تكون كلمة المرور 6 خانات على الأقل لضمان الأمان
        if (password.length() < 6) {
            etPassword.setError("يجب أن تتكون كلمة المرور من 6 خانات على الأقل");
            etPassword.requestFocus();
            isValid = false;
        }

        // --- 4. التحقق من تطابق كلمة المرور مع حقل التأكيد ---
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("كلمات المرور غير متطابقة");
            etConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid; // إرجاع الحالة النهائية لعملية الفحص
    }
}
