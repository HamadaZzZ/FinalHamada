package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.ExerciseAdapter;
import com.example.finalhamada.data.MyTaskTable.UserExercise;
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery;

import java.util.List;

/**
 * شاشة Exercises
 * ----------------------------------------------
 * مسؤولة عن:
 * - عرض جميع التمارين المخزنة في قاعدة البيانات
 * - إضافة تمرين جديد
 * - حذف أو تعديل التمارين
 * - عرض التمارين الجاهزة حسب الفئة (Cardio, Strength, Yoga, Cycling)
 * - إضافة تمارين سريعة (Quick Add)
 */
public class Exercises extends AppCompatActivity {

    /** زر إغلاق الشاشة */
    private ImageView btnClose;

    /** أزرار الفئات */
    private LinearLayout linearCardio, linearStrength, linearYoga, linearCycling;

    /** زر إضافة تمرين جديد */
    private Button btnAddNewExercise;

    /** RecyclerView لعرض التمارين */
    private RecyclerView rvExercises;

    /** Adapter للتمارين */
    private ExerciseAdapter exerciseAdapter;

    /** قائمة التمارين */
    private List<UserExercise> exerciseList;

    /** قاعدة البيانات و DAO */
    private AppDataBase1 db;
    private UserExerciseQuery exerciseQuery;

    /**
     * onCreate: ربط عناصر الواجهة، إعداد RecyclerView،
     * ضبط أزرار الفئات وزر الإغلاق وزر إضافة التمرين
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        /** الربط مع الواجهة */
        btnClose = findViewById(R.id.btnClose);
        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);
        rvExercises = findViewById(R.id.rvExercises);
        linearCardio = findViewById(R.id.linearCardio);
        linearStrength = findViewById(R.id.linearStrength);
        linearYoga = findViewById(R.id.linearYoga);
        linearCycling = findViewById(R.id.linearCycling);

        /** ربط قاعدة البيانات */
        db = AppDataBase1.getDatabase(this);
        exerciseQuery = db.userExerciseQuery();

        /** جلب كل التمارين */
        exerciseList = exerciseQuery.getAllExercises();

        /** إعداد RecyclerView مع Adapter */
        exerciseAdapter = new ExerciseAdapter(exerciseList, new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // TODO: فتح شاشة تعديل التمرين
            }

            @Override
            public void onDeleteClick(int position) {
                UserExercise exercise = exerciseList.get(position);
                exerciseQuery.delete(exercise);
                exerciseList.remove(position);
                exerciseAdapter.notifyItemRemoved(position);
            }
        });
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(exerciseAdapter);

        /** أزرار الفئات */
        linearCardio.setOnClickListener(v -> showReadyExercises("Cardio"));
        linearStrength.setOnClickListener(v -> showReadyExercises("Strength"));
        linearYoga.setOnClickListener(v -> showReadyExercises("Yoga"));
        linearCycling.setOnClickListener(v -> showReadyExercises("Cycling"));

        /** زر الإغلاق */
        btnClose.setOnClickListener(v -> finish());

        /** زر إضافة تمرين جديد */
        btnAddNewExercise.setOnClickListener(v -> {
            startActivity(new Intent(Exercises.this, AddNewExercise.class));
        });

        /** Quick Add تمارين سريعة */
        findViewById(R.id.tvRunning).setOnClickListener(v -> addQuickExercise("Running", 150));
        findViewById(R.id.tvWeightlifting).setOnClickListener(v -> addQuickExercise("Weightlifting", 200));
    }

    /** إضافة تمرين سريع وحفظه في قاعدة البيانات */
    private void addQuickExercise(String name, int calories) {
        UserExercise newExercise = new UserExercise(
                name, "Quick Add", 0, 0, 0, 0, calories, "", R.drawable.ic_launcher_foreground
        );
        exerciseQuery.insert(newExercise);
        exerciseList.clear();
        exerciseList.addAll(exerciseQuery.getAllExercises());
        exerciseAdapter.notifyDataSetChanged();
        rvExercises.scrollToPosition(exerciseList.size() - 1);
    }

    /** عرض التمارين الجاهزة حسب الفئة */
    private void showReadyExercises(String category) {
        exerciseList.clear();

        switch (category) {
            case "Cardio":
                exerciseList.add(new UserExercise("Running", "Cardio", 0,0,0,30,200,"Warm up first", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Jump Rope", "Cardio", 0,0,0,20,150,"Use proper rope", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Cycling", "Cardio", 0,0,0,40,300,"Moderate pace", R.drawable.ic_launcher_foreground));
                break;
            case "Strength":
                exerciseList.add(new UserExercise("Bench Press", "Strength", 12,3,50,0,250,"Focus on form", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Squats", "Strength", 15,4,60,0,300,"Keep back straight", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Deadlift", "Strength", 10,3,70,0,350,"Warm up first", R.drawable.ic_launcher_foreground));
                break;
            case "Yoga":
                exerciseList.add(new UserExercise("Sun Salutation", "Yoga", 0,0,0,30,100,"Relax and breathe", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Tree Pose", "Yoga", 0,0,0,20,50,"Balance carefully", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Warrior II", "Yoga", 0,0,0,25,80,"Focus on posture", R.drawable.ic_launcher_foreground));
                break;
            case "Cycling":
                exerciseList.add(new UserExercise("Stationary Bike", "Cycling", 0,0,0,45,400,"Keep steady pace", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Outdoor Cycling", "Cycling", 0,0,0,60,500,"Wear helmet", R.drawable.ic_launcher_foreground));
                exerciseList.add(new UserExercise("Hill Sprints", "Cycling", 0,0,0,30,350,"High intensity", R.drawable.ic_launcher_foreground));
                break;
        }

        exerciseAdapter.notifyDataSetChanged();
        rvExercises.scrollToPosition(0);
    }

    /** تحديث قائمة التمارين عند العودة للشاشة */
    @Override
    protected void onResume() {
        super.onResume();
        exerciseList.clear();
        exerciseList.addAll(exerciseQuery.getAllExercises());
        exerciseAdapter.notifyDataSetChanged();
    }
}
