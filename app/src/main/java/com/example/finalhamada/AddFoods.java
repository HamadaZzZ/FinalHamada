package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * شاشة AddFoods: مسؤولة عن عرض الأطعمة، البحث، والتنقل بين الشاشات.
 */
public class AddFoods extends AppCompatActivity {

    private TextView tvTile;
    private EditText etSearchFood;
    private TextView tvTdGoal;
    private TextView tvCalrories;
    private ProgressBar progressCalories;
    private TextView tvCAL;
    private TextView tvRecent;
    private TextView tvApple;
    private TextView tvcalApple;
    private TextView tvBanana;
    private TextView tvcalBanana;
    private TextView tvOrange;
    private TextView tvcalOrange;
    private TextView tvBroccoli;
    private TextView tvcalBroccoli;
    private TextView tvHome;
    private TextView tvGoals;
    private TextView tvFood;
    private TextView tvExcercises;
    private TextView tvProgress;

    /**
     * تهيئة مكوّنات الشاشة وربطها بالإضافة إلى إعداد التنقل بين الصفحات.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foods);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // الربط مع عناصر الواجهة
        tvTile = findViewById(R.id.tvTile);
        etSearchFood = findViewById(R.id.etSearchFood);
        tvTdGoal = findViewById(R.id.tvTdGoal);
        tvCalrories = findViewById(R.id.tvCalrories);
        progressCalories = findViewById(R.id.progressCalories);
        tvCAL = findViewById(R.id.tvCAL);
        tvRecent = findViewById(R.id.tvRecent);
        tvApple = findViewById(R.id.tvApple);
        tvcalApple = findViewById(R.id.tvcalApple);
        tvBanana = findViewById(R.id.tvBanana);
        tvcalBanana = findViewById(R.id.tvcalBanana);
        tvOrange = findViewById(R.id.tvOrange);
        tvcalOrange = findViewById(R.id.tvcalOrange);
        tvBroccoli = findViewById(R.id.tvBroccoli);
        tvcalBroccoli = findViewById(R.id.tvcalBroccoli);

        tvHome = findViewById(R.id.tvHome);
        tvGoals = findViewById(R.id.tvGoals);
        tvFood = findViewById(R.id.tvFood);
        tvExcercises = findViewById(R.id.tvExcercises);
        tvProgress = findViewById(R.id.tvProgress);

        // التنقل بين الشاشات
        tvHome.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, DashboardActivity.class)));

        tvGoals.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, YourGoal.class)));

        tvExcercises.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, Exercises.class)));

        tvProgress.setOnClickListener(v ->
                startActivity(new Intent(AddFoods.this, Progress.class)));
    }
}
