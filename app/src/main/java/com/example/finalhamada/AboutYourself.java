package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * AboutYourself Activity
 * ----------------------------------------------
 * EN:
 * This screen collects basic personal information from the user
 * such as age, height, weight, and gender. It is the first step
 * toward building a personalized health or fitness profile.
 *
 * AR:
 * هذه الشاشة تجمع المعلومات الأساسية من المستخدم
 * مثل العمر والطول والوزن والجنس. وهي الخطوة الأولى
 * في إنشاء ملف شخصي صحي أو رياضي مخصص.
 */
public class AboutYourself extends AppCompatActivity {

    private TextView tvstepText;
    private TextView tvheading;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private TextView tvgender;
    private RadioGroup genderGroup;
    private ProgressBar progressBar;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioButton radioOther;
    private Button nextButton;

    /**
     * onCreate()
     * ----------------------------------------------
     * EN:
     * Initializes the UI components of the screen, applies
     * edge-to-edge layout styling, and sets the action for the
     * "Next" button to navigate to the YourGoal activity.
     *
     * AR:
     * تهيئة عناصر الشاشة، وتطبيق واجهة Edge-to-Edge،
     * وتحديد وظيفة زر "التالي" للانتقال إلى شاشة الأهداف YourGoal.
     *
     * @param savedInstanceState الحالة السابقة للنشاط (إن وُجدت)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI References
        tvstepText = findViewById(R.id.tvstepText);
        tvheading = findViewById(R.id.tvheading);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvgender = findViewById(R.id.tvgender);
        genderGroup = findViewById(R.id.genderGroup);
        progressBar = findViewById(R.id.progressBar);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOther);
        nextButton = findViewById(R.id.nextButton);

        // Handle "Next" button click
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(AboutYourself.this, YourGoal.class);
            startActivity(intent);
            finish();
        });
    }
}
