package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.ExerciseAdapter;
import com.example.finalhamada.data.MyTaskTable.UserExercise;
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery;
import com.example.finalhamada.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * شاشة التمارين الرياضية (Exercises Activity)
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدم:
 * 1. عرض قائمة التمارين الخاصة به من قاعدة البيانات المحلية (Room).
 * 2. تصفية التمارين حسب الفئة (Cardio, Strength, Yoga, Cycling).
 * 3. حذف التمارين مع نافذة تأكيد لضمان عدم الحذف بالخطأ.
 * 4. الانتقال لشاشة إضافة تمرين جديد أو تعديل تمرين موجود.
 */
public class Exercises extends AppCompatActivity {

    // === عناصر واجهة المستخدم (Views) ===
    
    // الحاوية الجذرية التي تدعم ميزات التصميم المتقدمة مثل الـ Snackbar
    private CoordinatorLayout rootCoordinator; 
    
    // أيقونة الإغلاق لإنهاء النشاط والعودة للخلف
    private ImageView btnClose; 
    
    // حاويات الفئات (تم تعريفها كـ View لتتوافق مع أي نوع Layout مستخدم في الـ XML)
    private View linearCardio, linearStrength, linearYoga, linearCycling; 
    
    // الزر الرئيسي لإضافة تمرين جديد يدوياً
    private Button btnAddNewExercise; 
    
    // عنصر القائمة القابل للتمرير لعرض التمارين
    private RecyclerView rvExercises; 
    
    // المحول المسؤل عن إدارة البيانات وربطها بالـ RecyclerView
    private ExerciseAdapter exerciseAdapter;

    // === إدارة البيانات وقاعدة البيانات ===
    
    // قائمة محلية لتخزين التمارين التي سيتم عرضها
    private List<UserExercise> exerciseList = new ArrayList<>(); 
    
    // مرجع لقاعدة بيانات التطبيق (Singleton)
    private AppDataBase1 db; 
    
    // واجهة الوصول للبيانات (DAO) الخاصة بالتمارين لإجراء العمليات البرمجية
    private UserExerciseQuery exerciseQuery;

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف الـ XML الخاص بالتصميم بهذه الشاشة
        setContentView(R.layout.activity_exercises);

        // --- ربط العناصر البرمجية بمعرفاتها في ملف الـ XML ---
        rootCoordinator = findViewById(R.id.rootCoordinator);
        btnClose = findViewById(R.id.btnClose);
        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);
        rvExercises = findViewById(R.id.rvExercises);
        
        // ربط بطاقات التصنيف
        linearCardio = findViewById(R.id.linearCardio);
        linearStrength = findViewById(R.id.linearStrength);
        linearYoga = findViewById(R.id.linearYoga);
        linearCycling = findViewById(R.id.linearCycling);

        // --- تهيئة قاعدة البيانات ---
        // الحصول على نسخة قاعدة البيانات
        db = AppDataBase1.getDatabase(this);
        // الحصول على واجهة الاستعلامات
        exerciseQuery = db.userExerciseQuery();

        // جلب كافة التمارين من قاعدة البيانات وتحويلها إلى ArrayList
        exerciseList = new ArrayList<>(exerciseQuery.getAllExercises());

        // --- إعداد الـ Adapter للـ RecyclerView ---
        exerciseAdapter = new ExerciseAdapter(exerciseList, new ExerciseAdapter.OnItemClickListener() {
            
            /**
             * الضغط على زر التعديل: يفتح شاشة الإضافة في وضع التعديل
             */
            @Override
            public void onEditClick(UserExercise exercise, int position) {
                // إنشاء Intent للانتقال لشاشة AddNewExercise
                Intent intent = new Intent(Exercises.this, AddNewExercise.class);
                
                // تمرير بيانات التمرين المختار عبر الـ Intent
                intent.putExtra("edit_mode", true); // تفعيل وضع التعديل
                intent.putExtra("exercise_name", exercise.getName());
                intent.putExtra("exercise_category", exercise.getCategory());
                intent.putExtra("exercise_reps", exercise.getReps());
                intent.putExtra("exercise_sets", exercise.getSets());
                intent.putExtra("exercise_weight", exercise.getWeight());
                intent.putExtra("exercise_duration", exercise.getDuration());
                intent.putExtra("exercise_calories", exercise.getCalories());
                intent.putExtra("exercise_note", exercise.getNote());
                intent.putExtra("exercise_id", exercise.getId());
                
                // تشغيل النشاط الجديد
                startActivity(intent);
            }

            /**
             * الضغط على زر الحذف: يظهر رسالة تأكيد للمستخدم
             */
            @Override
            public void onDeleteClick(UserExercise exercise, int position) {
                // إنشاء نافذة تنبيه (Dialog) للتأكيد
                new AlertDialog.Builder(Exercises.this)
                        .setTitle("تأكيد الحذف") // عنوان النافذة
                        .setMessage("هل أنت متأكد أنك تريد حذف هذا التمرين؟") // محتوى الرسالة
                        .setPositiveButton("حذف", (dialog, which) -> {
                            // إذا ضغط المستخدم على حذف:
                            // 1. الحذف من قاعدة البيانات الفعلية
                            exerciseQuery.delete(exercise);
                            // 2. إزالة العنصر من القائمة المعروضة فوراً
                            exerciseAdapter.removeAt(position);
                            // 3. إظهار Snackbar لتأكيد العملية
                            Snackbar.make(rootCoordinator, exercise.getName() + " تم الحذف بنجاح", Snackbar.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("إلغاء", null) // في حال الإلغاء، لا يتم فعل شيء
                        .show(); // عرض النافذة للمستخدم
            }
        });

        // ضبط مدير التخطيط للـ RecyclerView ليكون بشكل عمودي
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        // تعيين الـ Adapter للـ RecyclerView
        rvExercises.setAdapter(exerciseAdapter);

        // --- ضبط أحداث النقر للفئات ---
        // عند الضغط على فئة، نقوم بتصفية البيانات وعرض تمارين الفئة فقط
        linearCardio.setOnClickListener(v -> showReadyOrStoredExercises("Cardio"));
        linearStrength.setOnClickListener(v -> showReadyOrStoredExercises("Strength"));
        linearYoga.setOnClickListener(v -> showReadyOrStoredExercises("Yoga"));
        linearCycling.setOnClickListener(v -> showReadyOrStoredExercises("Cycling"));

        // زر الإغلاق لإنهاء الشاشة
        btnClose.setOnClickListener(v -> finish());

        // زر إضافة تمرين جديد لفتح شاشة الإضافة الفارغة
        btnAddNewExercise.setOnClickListener(v -> {
            startActivity(new Intent(Exercises.this, AddNewExercise.class));
        });
    }

    /**
     * دالة لعرض التمارين حسب فئة معينة.
     * إذا لم يكن هناك تمارين مخزنة في قاعدة البيانات لهذه الفئة، يتم عرض تمارين تجريبية.
     * @param category اسم الفئة (Cardio, Strength, Yoga, Cycling)
     */
    private void showReadyOrStoredExercises(String category) {
        // الاستعلام عن التمارين المنتمية للفئة المحددة
        List<UserExercise> byCategory = exerciseQuery.getExercisesByCategory(category);
        
        // مسح القائمة الحالية قبل الإضافة
        exerciseList.clear();
        
        if (byCategory == null || byCategory.isEmpty()) {
            // إضافة بيانات وهمية (Mock Data) لتحسين الشكل إذا كانت قاعدة البيانات فارغة
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
                    break;
                case "Cycling":
                    exerciseList.add(new UserExercise("Stationary Bike", "Cycling", 0,0,0,45,400,"Keep steady pace", R.drawable.ic_launcher_foreground));
                    exerciseList.add(new UserExercise("Outdoor Cycling", "Cycling", 0,0,0,60,500,"Wear helmet", R.drawable.ic_launcher_foreground));
                    break;
            }
        } else {
            // إذا وُجدت بيانات في قاعدة البيانات، نضيفها للقائمة
            exerciseList.addAll(byCategory);
        }
        
        // تحديث المحول بالبيانات الجديدة وتنبيه الـ RecyclerView بالتغيير
        exerciseAdapter.setExercises(new ArrayList<>(exerciseList));
        // العودة إلى أعلى القائمة عند التغيير
        rvExercises.scrollToPosition(0);
    }

    /**
     * دالة onResume: يتم استدعاؤها عندما تعود الشاشة للواجهة
     * نقوم هنا بتحديث البيانات لضمان عرض آخر التعديلات
     */
    @Override
    protected void onResume() {
        super.onResume();
        // إعادة جلب كل التمارين من قاعدة البيانات
        exerciseList = new ArrayList<>(exerciseQuery.getAllExercises());
        // تحديث الـ Adapter
        exerciseAdapter.setExercises(exerciseList);
    }
}
