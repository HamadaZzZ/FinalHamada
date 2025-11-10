package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class Progress extends AppCompatActivity {

    private ViewGroup main;
    private ImageView btnMenu;
    private TextView tvProgress;
    private TextView tabWeight;
    private TextView tabCalories;
    private TextView tvWeightTrend;
    private TextView tvWeight;
    private TextView tvLast30Days2;
    private ImageView imgChart;
    private TextView tvWeeklySummary;
    private TextView tvYoureDoingGreat;
    private TextView tvYouveLost15LbsThisWeekKeepItUp;
    private TextView tvDashboard;
    private TextView tvAddFood;
    private TextView tvAddExercise;
    private TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        main = findViewById(R.id.main);
        btnMenu = findViewById(R.id.btnMenu);
        tvProgress = findViewById(R.id.tvProgress);
        tabWeight = findViewById(R.id.tabWeight);
        tabCalories = findViewById(R.id.tabCalories);
        tvWeightTrend = findViewById(R.id.tvWeightTrend);
        tvWeight = findViewById(R.id.tvWeight);
        tvLast30Days2 = findViewById(R.id.tvLast30Days2);
        imgChart = findViewById(R.id.imgChart);
        tvWeeklySummary = findViewById(R.id.tvWeeklySummary);
        tvYoureDoingGreat = findViewById(R.id.tvYoureDoingGreat);
        tvYouveLost15LbsThisWeekKeepItUp = findViewById(R.id.tvYouveLost15LbsThisWeekKeepItUp);
        tvDashboard = findViewById(R.id.tvDashboard);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvProfile = findViewById(R.id.tvProfile);
        tvDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(Progress.this, DashboardActivity.class);
            startActivity(intent);
        });
        tvAddFood.setOnClickListener(v -> {
            Intent intent = new Intent(Progress.this, AddFoods.class);
            startActivity(intent);
        });
        tvAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(Progress.this, Exercises.class);
            startActivity(intent);
        });
        tvProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Progress.this, Profile.class);
            startActivity(intent);
        });



    }
}
