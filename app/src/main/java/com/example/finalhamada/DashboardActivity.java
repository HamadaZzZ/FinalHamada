package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView tvTitle, tvDailyS, tvCalories, tvkcal, tvWater, tvGlasses, tvMins, tvWorkout, tvActions;
    private ImageView imageView, imageView1, imageView2, imageView3;
    private TextView tvAddFood, tvAddExercise, tvViewProgress, tvProfile;
    private ProgressBar progressBar, progressBar2, progressBar3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard1); // الملف الجديد مع DrawerLayout

        // EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط Toolbar + Drawer
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation item click
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            // اضف هنا الـ actions لأي items في drawer_menu
            drawerLayout.closeDrawers();
            return true;
        });

        // ربط الـ Dashboard content القديم
        tvTitle = findViewById(R.id.tvTitle);
        tvDailyS = findViewById(R.id.tvDailyS);
        tvCalories = findViewById(R.id.tvCalories);
        progressBar = findViewById(R.id.progressBar);
        tvkcal = findViewById(R.id.tvkcal);
        tvWater = findViewById(R.id.tvWater);
        progressBar2 = findViewById(R.id.progressBar2);
        tvGlasses = findViewById(R.id.tvGlasses);
        tvMins = findViewById(R.id.tvMins);
        progressBar3 = findViewById(R.id.progressBar3);
        tvWorkout = findViewById(R.id.tvWorkout);
        tvActions = findViewById(R.id.tvActions);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.ImageView2);
        imageView3 = findViewById(R.id.ImageView3);
        tvAddFood = findViewById(R.id.tvAddFood);
        tvAddExercise = findViewById(R.id.tvAddExercise);
        tvViewProgress = findViewById(R.id.tvViewProgress);
        tvProfile = findViewById(R.id.tvProfile);

        // Click listeners
        tvAddFood.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, AddFoods.class)));
        tvAddExercise.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, Exercises.class)));
        tvViewProgress.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, Progress.class)));
        tvProfile.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, Profile.class)));
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
