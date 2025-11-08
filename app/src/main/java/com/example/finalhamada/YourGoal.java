package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class YourGoal extends AppCompatActivity {
    private TextView tvYourGoal;
    private TextView tvaim;
    private TextView tvLoseWeight;
    private RadioButton rbLose;
    private TextView tvmainWeight;
    private RadioButton rbMaintain;
    private TextView tvGainWeight;
    private RadioButton rbGain;
    private Button btnContinue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_goal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvYourGoal = findViewById(R.id.tvYourGoal);
        tvaim = findViewById(R.id.tvaim);
        rbLose = findViewById(R.id.rbLose);
        rbGain = findViewById(R.id.rbGain);
        rbMaintain = findViewById(R.id.rbMaintain);
        tvLoseWeight = findViewById(R.id.tvLoseWeight);
        tvmainWeight = findViewById(R.id.tvmainWeight);
        tvGainWeight = findViewById(R.id.tvGainWeight);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            // ممكن قبلها تحفظ قيمة الاختيار في SharedPreferences إذا حاب
            Intent intent = new Intent(YourGoal.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // يغلق YourGoal بعد الانتقال
        });
    }
}