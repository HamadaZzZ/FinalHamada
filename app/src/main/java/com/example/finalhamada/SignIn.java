package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * شاشة تسجيل الدخول (SignIn)
 * ----------------------------------------------
 * مسؤولة عن:
 * - التحقق من البريد وكلمة المرور
 * - تسجيل دخول المستخدم باستخدام Firebase Authentication
 * - الانتقال إلى شاشة AboutYourself بعد تسجيل الدخول
 */
public class SignIn extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvaccount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvaccount = findViewById(R.id.tvaccount);

        auth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(SignIn.this, SignUp.class)));

        btnLogin.setOnClickListener(v -> {
            if (validateAndLogin()) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this, AboutYourself.class));
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

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
