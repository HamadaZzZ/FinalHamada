package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * نسخة بدون Firebase.
 *
 * - المستخدم يملأ الحقول: العمر، الطول، الوزن، الجنس.
 * - عند الضغط على Next، تتحقق من صحة القيم فقط.
 * - إذا كل شيء صحيح، ينتقل مباشرة إلى YourGoal.
 */
public class AboutYourself extends AppCompatActivity {

    private TextView tvstepText, tvheading, tvgender;
    private EditText etAge, etHeight, etWeight;
    private RadioGroup genderGroup;
    private RadioButton radioMale, radioFemale, radioOther;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself);

        // Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط العناصر
        tvstepText = findViewById(R.id.tvstepText);
        tvheading = findViewById(R.id.tvheading);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvgender = findViewById(R.id.tvgender);
        genderGroup = findViewById(R.id.genderGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);
        nextButton = findViewById(R.id.nextButton);

        // زر Next
        nextButton.setOnClickListener(v -> saveUserData());
    }

    /**
     * التحقق من صحة المدخلات والانتقال مباشرة إلى YourGoal
     */
    private void saveUserData() {
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String gender = "";

        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == radioMale.getId()) gender = "Male";
        else if (selectedId == radioFemale.getId()) gender = "Female";
        else if (selectedId == radioOther.getId()) gender = "Other";

        if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long age;
        double height, weight;

        try {
            age = Long.parseLong(ageStr);
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        if (age < 5 || age > 120) {
            etAge.setError("Enter valid age (5-120)");
            return;
        }
        if (height < 50 || height > 250) {
            etHeight.setError("Enter valid height (50-250 cm)");
            return;
        }
        if (weight < 10 || weight > 300) {
            etWeight.setError("Enter valid weight (10-300 kg)");
            return;
        }

        // كل شيء صحيح → الانتقال مباشرة
        Toast.makeText(this, "Data valid, moving to YourGoal ✔", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AboutYourself.this, YourGoal.class));
        finish();
    }
}
