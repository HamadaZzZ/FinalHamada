package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.UserExercise;

public class AddNewExercise extends AppCompatActivity {

    private TextView tvTitle;
    private Button btnSaveExercise;
    private EditText etExerciseName, etReps, etSets, etWeight, etDuration, etCalories, etNote;
    private Spinner spCategory;
    private ImageView ivExerciseImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        // Padding للـ system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط الـ Views
        tvTitle = findViewById(R.id.tvTitle);
        btnSaveExercise = findViewById(R.id.btnSaveExercise);
        etExerciseName = findViewById(R.id.etExerciseName);
        etReps = findViewById(R.id.etReps);
        etSets = findViewById(R.id.etSets);
        etWeight = findViewById(R.id.etWeight);
        etDuration = findViewById(R.id.etDuration);
        etCalories = findViewById(R.id.etCalories);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        ivExerciseImage = findViewById(R.id.ivExerciseImage);

        // إعداد Spinner للقيم
        String[] categories = {"Cardio", "Strength", "Yoga", "Cycling"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // زر الحفظ
        btnSaveExercise.setOnClickListener(v -> {
            // قراءة القيم من الـ EditTexts و Spinner
            String name = etExerciseName.getText().toString();
            String category = spCategory.getSelectedItem().toString();
            int reps = etReps.getText().toString().isEmpty() ? 0 : Integer.parseInt(etReps.getText().toString());
            int sets = etSets.getText().toString().isEmpty() ? 0 : Integer.parseInt(etSets.getText().toString());
            int weight = etWeight.getText().toString().isEmpty() ? 0 : Integer.parseInt(etWeight.getText().toString());
            int duration = etDuration.getText().toString().isEmpty() ? 0 : Integer.parseInt(etDuration.getText().toString());
            int calories = etCalories.getText().toString().isEmpty() ? 0 : Integer.parseInt(etCalories.getText().toString());
            String note = etNote.getText().toString();

            // ====== حفظ التمرين في قاعدة البيانات ======
            AppDataBase1 db = AppDataBase1.getDatabase(AddNewExercise.this);
            db.userExerciseQuery().insert(new UserExercise(
                    name, category, reps, sets, weight, duration, calories, note, R.drawable.ic_launcher_foreground
            ));

            // العودة لشاشة التمارين
            finish();
        });
    }
}