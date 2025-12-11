package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyUserTable.MyUser;
import com.example.finalhamada.data.MyUserTable.MyUserQuery;

/**
 * شاشة SignUp (إنشاء حساب جديد)
 * ----------------------------------------------
 * مسؤولة عن:
 * - إدخال بيانات المستخدم (الاسم، البريد، كلمة المرور)
 * - التحقق من صحة البيانات
 * - التحقق من وجود البريد مسبقًا في قاعدة البيانات
 * - إنشاء مستخدم جديد وحفظه في قاعدة البيانات
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

    /** DAO المستخدم للتعامل مع قاعدة البيانات */
    MyUserQuery dao;

    /**
     * تهيئة عناصر الشاشة وربطها بالكود
     * وضبط أفعال زر التسجيل
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /** ربط DAO */
        dao = AppDataBase1.getDatabase(this).myUserQuery();

        /** ربط عناصر الواجهة */
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        /** عند الضغط على زر التسجيل */
        btnRegister.setOnClickListener(v -> {
            if (validateAndReadData()) {
                Intent intent = new Intent(SignUp.this, AboutYourself.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * التحقق من صحة البيانات المدخلة وإنشاء حساب جديد
     * - الاسم يجب أن يكون غير فارغ
     * - البريد يجب أن يكون صحيح
     * - كلمة المرور 6 أحرف فأكثر
     * - تطابق كلمة المرور مع تأكيدها
     * - التأكد من عدم وجود البريد مسبقًا في قاعدة البيانات
     * - إنشاء المستخدم الجديد وحفظه في قاعدة البيانات
     *
     * @return true إذا البيانات صحيحة وتم إنشاء الحساب، false إذا فيها خطأ
     */
    public boolean validateAndReadData() {
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

        if (!isValid) return false;

        /** التحقق من وجود البريد مسبقًا */
        MyUser existingUser = dao.getUserByEmail(email);
        if (existingUser != null) {
            Toast.makeText(this, "Email already exists ❌", Toast.LENGTH_SHORT).show();
            return false;
        }

        /** إنشاء مستخدم جديد وحفظه */
        MyUser user = new MyUser();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setNotificationsEnabled(true);
        user.setCreatedAt(String.valueOf(System.currentTimeMillis()));

        dao.insertUser(user);

        Toast.makeText(this, "Account Created Successfully ✔", Toast.LENGTH_SHORT).show();

        return true;
    }
}
