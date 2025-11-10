package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class Exercises extends AppCompatActivity {

    private ViewGroup main;
    private ImageView btnClose;
    private TextView tvAddExercise;
    private TextView tvCategories;
    private TextView tvCardio;
    private TextView tvStrength;
    private TextView tvYoga;
    private TextView tvCycling;
    private TextView tvQuickAdd;
    private TextView tvRunning;
    private TextView tvN150Calories;
    private TextView tvWeightlifting;
    private TextView tvN200Calories;
    private Button btnAddNewExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        main = findViewById(R.id.main);
        btnClose = findViewById(R.id.btnClose);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvCategories = findViewById(R.id.tvCategories);
        tvCardio = findViewById(R.id.tvCardio);
        tvStrength = findViewById(R.id.tvStrength);
        tvYoga = findViewById(R.id.tvYoga);
        tvCycling = findViewById(R.id.tvCycling);
        tvQuickAdd = findViewById(R.id.tvQuickAdd);
        tvRunning = findViewById(R.id.tvRunning);
        tvN150Calories = findViewById(R.id.tvN150Calories);
        tvWeightlifting = findViewById(R.id.tvWeightlifting);
        tvN200Calories = findViewById(R.id.tvN200Calories);
        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);

        btnAddNewExercise.setOnClickListener(v -> {
            Intent intent = new Intent(Exercises.this, Exercises.class);
            startActivity(intent);
            finish();
        });
    }
}
