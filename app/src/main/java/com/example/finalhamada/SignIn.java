package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyUserTable.MyUser;
import com.example.finalhamada.data.MyUserTable.MyUserQuery;

/**
 * شاشة تسجيل الدخول (SignIn)
 * ----------------------------------------------
 * مسؤولة عن:
 * - التحقق من البريد وكلمة المرور
 * - تسجيل دخول المستخدم إذا كان موجود في قاعدة البيانات
 * - الانتقال إلى شاشة AboutYourself بعد تسجيل الدخول
 * - الانتقال إلى شاشة التسجيل (SignUp) عند الضغط على Register
 */
public class SignIn extends AppCompatActivity {

    /** حقول البريد وكلمة المرور */
    private EditText etEmail, etPassword;

    /** زر تسجيل الدخول */
    private Button btnLogin;

    /** نص الانتقال لشاشة التسجيل */
    private TextView tvRegister, tvaccount;

    /** DAO المستخدم للتعامل مع قاعدة البيانات */
    private MyUserQuery dao;

    /**
     * تهيئة عناصر الواجهة وربطها بالكود
     * وضبط أفعال أزرار تسجيل الدخول والتسجيل
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        /** Edge-to-edge padding */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** ربط عناصر الواجهة */
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        /** ربط DAO المستخدم */
        dao = AppDataBase1.getDatabase(this).myUserQuery();

        /** الانتقال لشاشة التسجيل */
        tvRegister.setOnClickListener(v -> startActivity(new Intent(SignIn.this, SignUp.class)));

        /** محاولة تسجيل الدخول */
        btnLogin.setOnClickListener(v -> {
            if (validateAndLogin()) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                MyUser user = dao.login(email, password);

                if (user != null) {
                    Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, AboutYourself.class));
                    finish();
                } else {
                    Toast.makeText(SignIn.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * التحقق من صحة البريد وكلمة المرور قبل تسجيل الدخول
     * - البريد يجب أن يكون صالحاً
     * - كلمة المرور 6 أحرف على الأقل
     *
     * @return true إذا المدخلات صحيحة، false إذا فيها خطأ
     */
    private boolean validateAndLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }
}
