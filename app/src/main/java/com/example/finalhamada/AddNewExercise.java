package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.UserExercise;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * ============================================================
 * AddNewExercise Activity
 * ============================================================
 * شاشة إضافة تمرين جديد للمستخدم.
 *
 * الوظائف:
 * 1️⃣ تعبئة بيانات التمرين: الاسم، الفئة، عدد المرات، المجموعات، الوزن، المدة، السعرات، ملاحظة
 * 2️⃣ اختيار فئة التمرين من Spinner
 * 3️⃣ حفظ البيانات في قاعدة البيانات المحلية (Room)
 * 4️⃣ حفظ البيانات أيضًا على Firebase Realtime Database
 * 5️⃣ التحقق من صحة البيانات وإعطاء قيمة صفرية إذا كانت الأرقام غير صالحة
 * ============================================================
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

    /** Firebase Realtime Database */
    private DatabaseReference dbRef;

    /**
     * onCreate
     * --------------------------------------------------
     * تهيئة عناصر الواجهة وربطها بالكود، ضبط Spinner وزر الحفظ
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        /** Edge-to-Edge Layout */
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

        /** تهيئة Firebase Realtime Database */
        dbRef = FirebaseDatabase.getInstance().getReference();

        /** عند الضغط على زر الحفظ */
        btnSaveExercise.setOnClickListener(v -> saveExercise());
    }

    /**
     * saveExercise
     * --------------------------------------------------
     * تتحقق من صحة البيانات المدخلة، ثم تحفظ التمرين:
     * 1️⃣ في Room Database محلي
     * 2️⃣ في Firebase Realtime Database
     */
    private void saveExercise() {
        // قراءة القيم من الحقول
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

        // إنشاء كائن UserExercise
        UserExercise exercise = new UserExercise(
                name,
                category,
                reps,
                sets,
                weight,
                duration,
                calories,
                note,
                R.drawable.ic_info // صورة افتراضية
        );

        // حفظ التمرين في Room
        AppDataBase1 db = AppDataBase1.getDatabase(AddNewExercise.this);
        db.userExerciseQuery().insert(exercise);

        // حفظ التمرين في Firebase
        saveExerciseToFirebase(exercise);

        // إغلاق الشاشة بعد الحفظ
        Toast.makeText(this, "Exercise saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * saveExerciseToFirebase
     * --------------------------------------------------
     * تحفظ بيانات التمرين على Firebase Realtime Database بصيغة JSON
     *
     * @param exercise كائن يمثل التمرين
     */
    private void saveExerciseToFirebase(UserExercise exercise) {
        HashMap<String, Object> exerciseData = new HashMap<>();
        exerciseData.put("name", exercise.getName());
        exerciseData.put("category", exercise.getCategory());
        exerciseData.put("reps", exercise.getReps());
        exerciseData.put("sets", exercise.getSets());
        exerciseData.put("weight", exercise.getWeight());
        exerciseData.put("duration", exercise.getDuration());
        exerciseData.put("calories", exercise.getCalories());
        exerciseData.put("note", exercise.getNote());
        exerciseData.put("imageRes", exercise.getImageRes());

        // uid افتراضي، يمكن استخدام FirebaseAuth لاحقًا
        String uid = "default";

        dbRef.child("users")
                .child(uid)
                .child("exercises")
                .push()
                .updateChildren(exerciseData)
                .addOnSuccessListener(aVoid -> {
                    // نجاح الحفظ على Firebase
                })
                .addOnFailureListener(e -> Toast.makeText(this,
                        "Failed to save to Firebase: " + e.getMessage(),
                        Toast.LENGTH_LONG).show());
    }

    /**
     * parseOrZero
     * --------------------------------------------------
     * تحويل النص إلى رقم صحيح، أو صفر إذا كان فارغ أو غير صالح
     *
     * @param value النص المدخل
     * @return الرقم الصحيح أو صفر
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
