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

public class SignIn extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvaccount;
    private MyUserQuery dao;

    /**
     * onCreate()
     * --------------------------
     * EN: Initializes the SignIn screen, prepares UI elements,
     * connects to the database, and sets actions for login and register.
     *
     * AR: تهيئة شاشة تسجيل الدخول، تجهيز العناصر،
     * ربط قاعدة البيانات، وتحديد أفعال زر التسجيل وتسجيل الدخول.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI references
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        // Database DAO
        dao = AppDataBase1.getDatabase(this).myUserQuery();

        /**
         * EN: Open registration page when clicking "Register".
         * AR: فتح صفحة إنشاء حساب عند الضغط على Register.
         */
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
        });

        /**
         * EN: Try signing in when login button is clicked:
         * validate input → check user in DB → go to next page or show error.
         *
         * AR: عند الضغط على زر تسجيل الدخول:
         * التحقق من المدخلات → فحص المستخدم في قاعدة البيانات → الانتقال أو عرض خطأ.
         */
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
     * validateAndLogin()
     * --------------------------
     * EN: Validates email and password before login.
     * Email must be valid, password must be 6+ characters.
     *
     * AR: يتحقق من صحة البريد وكلمة المرور قبل تسجيل الدخول.
     * البريد يجب أن يكون صحيحًا، وكلمة المرور 6 أحرف فأكثر.
     *
     * @return true إذا المدخلات صحيحة، false إذا فيها خطأ.
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
