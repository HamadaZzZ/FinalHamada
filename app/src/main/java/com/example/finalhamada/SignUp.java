package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyUserTable.MyUser;
import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyUserTable.MyUserQuery;

public class SignUp extends AppCompatActivity {
    private TextView tvCreateAccount;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    MyUserQuery dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        dao = AppDataBase1.getDatabase(this).myUserQuery();

        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            if (validateAndReadData()) {
                Intent intent = new Intent(SignUp.this, AboutYourself.class);
                startActivity(intent);
                finish();
            }
        });
    }

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

        MyUser existingUser = dao.getUserByEmail(email);
        if (existingUser != null) {
            Toast.makeText(this, "Email already exists ❌", Toast.LENGTH_SHORT).show();
            return false;
        }
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
