package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * شاشة SignUp (إنشاء حساب جديد)
 * ----------------------------------------------
 * مسؤولة عن:
 * - إدخال بيانات المستخدم (الاسم، البريد، كلمة المرور)
 * - التحقق من صحة البيانات
 * - إنشاء مستخدم جديد باستخدام Firebase Authentication
 * - حفظ بيانات المستخدم في Firestore
 * - الانتقال إلى شاشة AboutYourself بعد إنشاء الحساب
 */
public class SignUp extends AppCompatActivity {

    /** عنوان الصفحة */
    private TextView tvCreateAccount;

    /** حقول الإدخال */
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    /** زر التسجيل */
    private Button btnRegister;

    /** FirebaseAuth لإنشاء مستخدم جديد */
    private FirebaseAuth auth;

    /** FirebaseFirestore لحفظ بيانات المستخدم */
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // ربط عناصر الواجهة
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // تهيئة Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

                                // بعد تسجيل المستخدم، حفظ بياناته الأساسية في Firestore
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("name", name);
                                userData.put("email", email);
                                userData.put("createdAt", System.currentTimeMillis());

                                db.collection("users")
                                        .document(auth.getCurrentUser().getUid()) // UID لكل مستخدم
                                        .set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(SignUp.this, "Account Created Successfully ✔", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this, AboutYourself.class));
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(SignUp.this, "Error saving data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        });

                            } else {
                                Toast.makeText(SignUp.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    /**
     * التحقق من صحة البيانات المدخلة قبل إنشاء الحساب
     * - الاسم يجب أن يكون غير فارغ
     * - البريد يجب أن يكون صحيح
     * - كلمة المرور 6 أحرف فأكثر
     * - تطابق كلمة المرور مع تأكيدها
     *
     * @return true إذا البيانات صحيحة، false إذا فيها خطأ
     */
    private boolean validateAndReadData() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValid = true;

        if (name.isEmpty()) {
            etName.setError("Name is required");
            isValid = false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            isValid = false;
        }
        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords don't match");
            isValid = false;
        }

        return isValid;
    }
}
