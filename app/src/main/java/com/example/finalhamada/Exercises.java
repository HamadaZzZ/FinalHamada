package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.ExerciseAdapter;
import com.example.finalhamada.data.MyTaskTable.UserExercise;
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery;

import java.util.List;

public class Exercises extends AppCompatActivity {

    private ImageView btnClose;
    private Button btnAddNewExercise;
    private RecyclerView rvExercises;
    private ExerciseAdapter exerciseAdapter;
    private List<UserExercise> exerciseList;

    private AppDataBase1 db;
    private UserExerciseQuery exerciseQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // ====== الربط مع الواجهة ======
        btnClose = findViewById(R.id.btnClose);
        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);
        rvExercises = findViewById(R.id.rvExercises);

        // ====== ربط قاعدة البيانات ======
        db = AppDataBase1.getDatabase(this);
        exerciseQuery = db.userExerciseQuery();

        // ====== جلب كل التمارين من قاعدة البيانات ======
        exerciseList = exerciseQuery.getAllExercises();

        // ====== إعداد RecyclerView ======
        exerciseAdapter = new ExerciseAdapter(exerciseList, new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                UserExercise exercise = exerciseList.get(position);
                // TODO: فتح شاشة تعديل التمرين
                // يمكن إرسال بيانات التمرين للشاشة التالية عبر Intent
            }

            @Override
            public void onDeleteClick(int position) {
                UserExercise exercise = exerciseList.get(position);
                // حذف من قاعدة البيانات
                exerciseQuery.delete(exercise);
                // حذف من القائمة وتحديث RecyclerView
                exerciseList.remove(position);
                exerciseAdapter.notifyItemRemoved(position);
            }
        });

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(exerciseAdapter);

        // ====== زر الإغلاق ======
        btnClose.setOnClickListener(v -> finish());

        // ====== زر إضافة تمرين جديد ======
        btnAddNewExercise.setOnClickListener(v -> {
            Intent intent = new Intent(Exercises.this, AddNewExercise.class);
            startActivity(intent);
        });

        // ====== Quick Add ======
        findViewById(R.id.tvRunning).setOnClickListener(v -> addQuickExercise("Running", 150));
        findViewById(R.id.tvWeightlifting).setOnClickListener(v -> addQuickExercise("Weightlifting", 200));
    }

    // دالة لإضافة تمرين سريع مع حفظه في قاعدة البيانات
    private void addQuickExercise(String name, int calories) {
        UserExercise newExercise = new UserExercise(
                name, "Quick Add", 0, 0, 0, 0, calories, "", R.drawable.ic_launcher_foreground
        );
        // حفظ في قاعدة البيانات
        exerciseQuery.insert(newExercise);
        // إعادة جلب كل التمارين لتحديث القائمة
        exerciseList.clear();
        exerciseList.addAll(exerciseQuery.getAllExercises());
        exerciseAdapter.notifyDataSetChanged();
        rvExercises.scrollToPosition(exerciseList.size() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // إعادة تحميل التمارين عند العودة للشاشة (مثلاً بعد إضافة تمرين جديد من شاشة أخرى)
        exerciseList.clear();
        exerciseList.addAll(exerciseQuery.getAllExercises());
        exerciseAdapter.notifyDataSetChanged();
    }
}
