package com.example.finalhamada;

import android.annotation.SuppressLint;
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

/**
 * شاشة AddNewExercise
 * ----------------------------------------------
 * مسؤولة عن إضافة تمرين جديد للمستخدم
 * - تعبئة بيانات التمرين (اسم، فئة، عدد المرات، المجموعات، الوزن، المدة، السعرات، ملاحظة)
 * - اختيار فئة التمرين من Spinner
 * - حفظ البيانات في قاعدة البيانات
 */
public class AddNewExercise extends AppCompatActivity {

    /** عنوان الشاشة */
    private TextView tvTitle;

    /** زر حفظ التمرين */
    private Button btnSaveExercise;

    /** حقول إدخال بيانات التمرين */
    private EditText etExerciseName, etReps, etSets, etWeight, etDuration, etCalories, etNote;

    /** Spinner لاختيار فئة التمرين */
    private Spinner spCategory;

    /** صورة التمرين */
    private ImageView ivExerciseImage;

    /**
     * تهيئة عناصر الواجهة وربطها بالكود
     * وضبط Spinner وزر الحفظ
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        /** تطبيق Edge-to-Edge */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** ربط الـ Views بالكود */
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

        /** إعداد Spinner للفئات */
        String[] categories = {"Cardio", "Strength", "Yoga", "Cycling"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        /** عند الضغط على زر الحفظ */
        btnSaveExercise.setOnClickListener(v -> {

            // قراءة بيانات التمرين
            String name = etExerciseName.getText().toString().trim();
            String category = spCategory.getSelectedItem().toString();
            int reps = parseOrZero(etReps.getText().toString());
            int sets = parseOrZero(etSets.getText().toString());
            int weight = parseOrZero(etWeight.getText().toString());
            int duration = parseOrZero(etDuration.getText().toString());
            int calories = parseOrZero(etCalories.getText().toString());
            String note = etNote.getText().toString().trim();

            // التحقق من الاسم
            if (name.isEmpty()) {
                etExerciseName.setError("Name is required");
                return;
            }

            // حفظ التمرين في قاعدة البيانات
            AppDataBase1 db = AppDataBase1.getDatabase(AddNewExercise.this);
            db.userExerciseQuery().insert(
                    new UserExercise(
                            name,
                            category,
                            reps,
                            sets,
                            weight,
                            duration,
                            calories,
                            note,
                            R.drawable.ic_info
                    )
            );

            // إغلاق الشاشة بعد الحفظ
            finish();
        });
    }

    /**
     * تحويل النص إلى رقم صحيح، أو صفر إذا كان فارغ أو غير صالح
     */
    private int parseOrZero(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
