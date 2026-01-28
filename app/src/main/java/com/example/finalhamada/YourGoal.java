package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * YourGoal Activity
 *
 * EN: Displays the "Your Goal" screen with three options for the user to choose from.
 *     Ensures only one option is selected at a time, handles LinearLayout clicks, and
 *     navigates to Dashboard.
 *
 * AR: شاشة "هدفك" تعرض ثلاثة خيارات للمستخدم لاختيار هدفه الرئيسي.
 *     تضمن اختيار خيار واحد فقط، وتتعامل مع الضغط على أي خيار للتحديد والانتقال للـ Dashboard.
 */
public class YourGoal extends AppCompatActivity {

    // ====== UI Components ======
    private TextView tvYourGoal;
    private TextView tvaim;
    private TextView tvLoseWeight;
    private RadioButton rbLose;
    private TextView tvmainWeight;
    private RadioButton rbMaintain;
    private TextView tvGainWeight;
    private RadioButton rbGain;
    private Button btnContinue;

    private LinearLayout LLloseWeight;
    private LinearLayout LLmaintainWeight;
    private LinearLayout LLgainWeight;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_goal);

        // ====== Edge-to-edge padding ======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ====== Bind UI elements ======
        tvYourGoal = findViewById(R.id.tvYourGoal);
        tvaim = findViewById(R.id.tvaim);

        rbLose = findViewById(R.id.rbLose);
        rbMaintain = findViewById(R.id.rbMaintain);
        rbGain = findViewById(R.id.rbGain);

        tvLoseWeight = findViewById(R.id.tvLoseWeight);
        tvmainWeight = findViewById(R.id.tvmainWeight);
        tvGainWeight = findViewById(R.id.tvGainWeight);

        LLloseWeight = findViewById(R.id.LLloseWeight);
        LLmaintainWeight = findViewById(R.id.LLmaintainWeight);
        LLgainWeight = findViewById(R.id.LLgainWeight);

        btnContinue = findViewById(R.id.btnContinue);

        radioGroup = findViewById(R.id.radioGroupGoals);

        // ====== Make LinearLayouts clickable and update RadioGroup ======
        LLloseWeight.setOnClickListener(v -> radioGroup.check(rbLose.getId()));
        LLmaintainWeight.setOnClickListener(v -> radioGroup.check(rbMaintain.getId()));
        LLgainWeight.setOnClickListener(v -> radioGroup.check(rbGain.getId()));

        // ====== Continue button ======
        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(YourGoal.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
