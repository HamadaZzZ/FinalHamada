package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_yourself);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        Intent intent = new Intent(AboutYourself.this, YourGoal.class);
        startActivity(intent);
    }
}