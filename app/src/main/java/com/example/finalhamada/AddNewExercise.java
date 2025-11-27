package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Class: AddNewExercise
 * Purpose (EN): Auto-generated documentation for class AddNewExercise.
 * الهدف (AR): توثيق تلقائي للكلاس AddNewExercise.
 * TODO: Add more detailed description about class functionality
 */
public class AddNewExercise extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvExerciseName;
    private TextView tvDuration;
    private TextView tvCalories;
    private Button btnSaveExercise;
    private EditText etExerciseName;
    private EditText etDuration;
    private EditText etCalories;

    @SuppressLint("MissingInflatedId")
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTitle = findViewById(R.id.tvTitle);
        tvExerciseName = findViewById(R.id.tvExerciseName);
        tvDuration = findViewById(R.id.tvDuration);
        tvCalories = findViewById(R.id.tvCalories);
        btnSaveExercise = findViewById(R.id.btnSaveExercise);
        etExerciseName = findViewById(R.id.etExerciseName);
        etDuration = findViewById(R.id.etDuration);
        etCalories = findViewById(R.id.etCalories);
        btnSaveExercise.setOnClickListener(v -> {
            String exerciseName = etExerciseName.getText().toString();
            String duration = etDuration.getText().toString();
            String calories = etCalories.getText().toString();
            Intent intent = new Intent(AddNewExercise.this, Exercises.class);
            intent.putExtra("exerciseName", exerciseName);
            intent.putExtra("duration", duration);
            intent.putExtra("calories", calories);
            startActivity(intent);
            finish();
        });
    }
}