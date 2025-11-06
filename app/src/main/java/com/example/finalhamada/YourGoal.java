package com.example.finalhamada;

import android.annotation.SuppressLint;
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
    private RadioGroup rgGoal;
    private RadioButton rbLose;
    private RadioButton rbGain;
    private RadioButton rbMaintain;;
    private LinearLayout LLloseWeight;
    private TextView tvLoseWeight;




    @SuppressLint("MissingInflatedId")
    @Override
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
        rgGoal = findViewById(R.id.rgGoal);
        rbLose = findViewById(R.id.rbLose);
        rbGain = findViewById(R.id.rbGain);
        rbMaintain = findViewById(R.id.rbMaintain);
        LLloseWeight = findViewById(R.id.LLloseWeight);
        tvLoseWeight = findViewById(R.id.tvLoseWeight);
        rgGoal.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLose) {
                LLloseWeight.setVisibility(View.VISIBLE);
            } else {
                LLloseWeight.setVisibility(View.GONE);
            }
        });
    }
}