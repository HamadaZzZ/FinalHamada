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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * شاشة SignUp (إنشاء حساب جديد)
 * ----------------------------------------------
 * مسؤولة عن:
 * - إدخال بيانات المستخدم (الاسم، البريد، كلمة المرور)
 * - التحقق من صحة البيانات
 * - إنشاء مستخدم جديد باستخدام Firebase Authentication
 * - حفظ بيانات المستخدم في Realtime Database
 * - الانتقال إلى شاشة AboutYourself بعد إنشاء الحساب
 */
public class SignUp extends AppCompatActivity {

    // لطباعة الأخطاء في Logcat
    private static final String TAG = "SignUpActivity";

    // حقول الإدخال
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    // زر التسجيل
    private Button btnRegister;

    // خدمات Firebase
    private FirebaseAuth auth;
    private DatabaseReference realtime_db; // لحفظ البيانات في Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // ربط عناصر الواجهة
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // تهيئة Firebase
        auth = FirebaseAuth.getInstance();
        // مؤشر لقاعدة البيانات Realtime Database
        realtime_db = FirebaseDatabase.getInstance().getReference();

        // عند الضغط على زر التسجيل
        btnRegister.setOnClickListener(v -> {
            if (validateAndReadData()) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // إنشاء مستخدم جديد في Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                if (firebaseUser != null) {
                                    // إنشاء كائن FitTrack لحفظه
                                    FitTrack userProfileData = new FitTrack();
                                    userProfileData.setName(name);
                                    // يمكنك إضافة أي بيانات أخرى تود حفظها هنا
                                    // userProfileData.setAge(...);

                                    // حفظ بيانات المستخدم في Realtime Database
                                    saveUser(firebaseUser.getUid(), userProfileData);
                                }
                            } else {
                                Toast.makeText(SignUp.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    /**
     * حفظ بيانات المستخدم في Firebase Realtime Database
     * @param uid المعرف الفريد للمستخدم من Firebase Auth
     * @param trackData كائن FitTrack الذي يحتوي على البيانات
     */
    public void saveUser(String uid, FitTrack trackData) {
        // مؤشر لجدول المستخدمين، واستخدام UID كمفتاح فريد
        realtime_db.child("users").child(uid).setValue(trackData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUp.this, "Succeeded to add User", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "تم حفظ المستخدم بنجاح: " + uid);

                        // الانتقال إلى الشاشة التالية بعد النجاح
                        startActivity(new Intent(SignUp.this, AboutYourself.class));
                        finish(); // إغلاق شاشة التسجيل
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // معالجة الأخطاء
                        Log.e(TAG, "خطأ في حفظ المستخدم: " + e.getMessage(), e);
                        Toast.makeText(SignUp.this, "Failed to add User data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * التحقق من صحة البيانات المدخلة قبل إنشاء الحساب
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
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
