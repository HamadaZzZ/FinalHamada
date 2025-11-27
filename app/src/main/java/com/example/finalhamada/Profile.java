package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Class: Profile
 * Purpose (EN): Auto-generated documentation for class Profile.
 * الهدف (AR): توثيق تلقائي للكلاس Profile.
 * TODO: Add more detailed description about class functionality
 */
public class Profile extends AppCompatActivity {

    private ViewGroup main;
    private TextView tvTitle;
    private ImageView btnSettings;
    private ImageView imgProfile;
    private TextView tvName;
    private TextView tvSubtitle;
    private TextView tvN57;
    private TextView tvHeight;
    private TextView tvN135Lbs;
    private TextView tvWeight;
    private TextView tvN225;
    private TextView tvBmi;
    private Button btnEditProfile;
    private Button btnLogout;
    private TextView tvDashboard;
    private TextView tvAddFood;
    private TextView tvAddExercise;
    private TextView tvProgress;
    private TextView tvProfile;

    @Override
/**
 * Method: onCreate
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param savedInstanceState - description
 */
    /**
 * دالة onCreate: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        main = findViewById(R.id.main);
        tvTitle = findViewById(R.id.tvTitle);
        btnSettings = findViewById(R.id.btnSettings);
        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvN57 = findViewById(R.id.tvN57);
        tvHeight = findViewById(R.id.tvHeight);
        tvN135Lbs = findViewById(R.id.tvN135Lbs);
        tvWeight = findViewById(R.id.tvWeight);
        tvN225 = findViewById(R.id.tvN225);
        tvBmi = findViewById(R.id.tvBmi);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        tvDashboard = findViewById(R.id.tvDashboard);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvProgress = findViewById(R.id.tvProgress);
        tvProfile = findViewById(R.id.tvProfile);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Profile.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, SplashScreen.class);
            startActivity(intent);
        });
    }
}
