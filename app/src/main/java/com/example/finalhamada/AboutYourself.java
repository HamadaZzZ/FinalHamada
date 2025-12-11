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
 * شاشة AboutYourself
 * تجمع المعلومات الأساسية عن المستخدم
 * (العمر، الطول، الوزن، الجنس)
 */
public class AboutYourself extends AppCompatActivity {

    /** نص يوضح خطوة التسجيل الحالية */
    private TextView tvstepText;

    /** عنوان الشاشة */
    private TextView tvheading;

    /** إدخال عمر المستخدم */
    private EditText etAge;

    /** إدخال طول المستخدم */
    private EditText etHeight;

    /** إدخال وزن المستخدم */
    private EditText etWeight;

    /** نص عنوان اختيار الجنس */
    private TextView tvgender;

    /** مجموعة أزرار اختيار الجنس */
    private RadioGroup genderGroup;

    /** شريط يوضح تقدم المستخدم */
    private ProgressBar progressBar;

    /** خيار الجنس ذكر */
    private RadioButton radioMale;

    /** خيار الجنس أنثى */
    private RadioButton radioFemale;

    /** خيار جنس آخر */
    private RadioButton radioOther;

    /** زر الانتقال للشاشة التالية */
    private Button nextButton;

    /**
     * تهيئة الشاشة وربط عناصر الواجهة
     * والتعامل مع زر "التالي"
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself);

        /** تطبيق عرض Edge-to-Edge */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** ربط عناصر الواجهة مع الكود */
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

        /** عند الضغط على زر التالي يتم الانتقال لشاشة الأهداف */
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(AboutYourself.this, YourGoal.class);
            startActivity(intent);
            finish();
        });
    }
}
