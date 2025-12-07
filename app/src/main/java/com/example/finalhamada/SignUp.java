package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUp extends AppCompatActivity {

    private TextView tvCreateAccount;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    MyUserQuery dao;

    /**
     * onCreate()
     * ----------------------------
     * EN: Initializes the SignUp screen, connects UI elements,
     * loads the database DAO, and sets the register button action.
     *
     * AR: تهيئة شاشة إنشاء الحساب، ربط عناصر الواجهة،
     * تجهيز DAO لقاعدة البيانات، وتحديد ما يحدث عند الضغط على زر التسجيل.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dao = AppDataBase1.getDatabase(this).myUserQuery();

        // Link UI elements
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        /**
         * EN: When register button is clicked, validate input and continue if valid.
         * AR: عند الضغط على زر التسجيل، يتم فحص البيانات ومتابعة العملية إذا كانت صحيحة.
         */
        btnRegister.setOnClickListener(v -> {
            if (validateAndReadData()) {
                Intent intent = new Intent(SignUp.this, AboutYourself.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * validateAndReadData()
     * ----------------------------
     * EN:
     * Validates user input:
     * - Name must not be empty.
     * - Email must be valid.
     * - Password must be 6+ characters.
     * - Passwords must match.
     * Checks if email already exists, then creates a new user.
     *
     * AR:
     * يتحقق من البيانات المدخلة:
     * - الاسم غير فارغ.
     * - البريد بصيغة صحيحة.
     * - كلمة المرور 6 أحرف أو أكثر.
     * - تطابق كلمة المرور مع التأكيد.
     * يفحص إذا كان البريد موجود مسبقًا ثم ينشئ مستخدم جديد.
     *
     * @return true إذا البيانات صحيحة وتم إنشاء الحساب، false إذا فيها خطأ.
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

        // Stop if any input is invalid
        if (!isValid) return false;

        // Check if email already exists in DB
        MyUser existingUser = dao.getUserByEmail(email);
        if (existingUser != null) {
            Toast.makeText(this, "Email already exists ❌", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Create new user
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
