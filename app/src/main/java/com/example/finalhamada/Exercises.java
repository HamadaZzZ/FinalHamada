package com.example.finalhamada; // تعريف الحزمة التي ينتمي لها هذا الكلاس داخل المشروع

import android.content.Intent; // Intent يستخدم للانتقال بين الشاشات
import android.os.Bundle; // Bundle يخزن بيانات حالة الشاشة
import android.widget.Button; // زر في واجهة المستخدم
import android.widget.ImageView; // عنصر عرض صورة
import android.widget.LinearLayout; // حاوية Layout يمكن الضغط عليها
import android.widget.Toast; // رسالة قصيرة تظهر للمستخدم

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي لأي Activity
import androidx.recyclerview.widget.LinearLayoutManager; // لتحديد شكل عرض العناصر في RecyclerView (عمودي)
import androidx.recyclerview.widget.RecyclerView; // لعرض قائمة عناصر قابلة للتمرير

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // قاعدة بيانات Room المحلية
import com.example.finalhamada.data.MyTaskTable.ExerciseAdapter; // Adapter لربط البيانات بالـ RecyclerView
import com.example.finalhamada.data.MyTaskTable.UserExercise; // Model يمثل التمرين
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery; // DAO للتعامل مع جدول التمارين
import com.google.firebase.database.DatabaseReference; // مرجع داخل Firebase
import com.google.firebase.database.FirebaseDatabase; // نقطة الدخول إلى Firebase Database

import java.util.HashMap; // لتجميع البيانات Key -> Value قبل إرسالها لـ Firebase
import java.util.List; // تمثل قائمة من العناصر

public class Exercises extends AppCompatActivity { // تعريف Activity باسم Exercises

    private ImageView btnClose; // زر إغلاق الشاشة
    private LinearLayout linearCardio, linearStrength, linearYoga, linearCycling; // أزرار الفئات
    private Button btnAddNewExercise; // زر إضافة تمرين جديد
    private RecyclerView rvExercises; // RecyclerView لعرض التمارين
    private ExerciseAdapter exerciseAdapter; // Adapter لربط البيانات بالعرض
    private List<UserExercise> exerciseList; // قائمة تحتوي التمارين
    private AppDataBase1 db; // كائن قاعدة البيانات
    private UserExerciseQuery exerciseQuery; // DAO للتعامل مع جدول التمارين
    private DatabaseReference dbRef; // مرجع Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) { // دالة تُستدعى عند إنشاء الشاشة
        super.onCreate(savedInstanceState); // استدعاء دالة الأب
        setContentView(R.layout.activity_exercises); // ربط ملف XML بهذه الشاشة

        btnClose = findViewById(R.id.btnClose); // ربط زر الإغلاق
        btnAddNewExercise = findViewById(R.id.btnAddNewExercise); // ربط زر إضافة تمرين
        rvExercises = findViewById(R.id.rvExercises); // ربط RecyclerView
        linearCardio = findViewById(R.id.linearCardio); // ربط زر Cardio
        linearStrength = findViewById(R.id.linearStrength); // ربط زر Strength
        linearYoga = findViewById(R.id.linearYoga); // ربط زر Yoga
        linearCycling = findViewById(R.id.linearCycling); // ربط زر Cycling

        db = AppDataBase1.getDatabase(this); // جلب instance من قاعدة بيانات Room (Singleton)
        exerciseQuery = db.userExerciseQuery(); // الحصول على DAO للتعامل مع التمارين

        dbRef = FirebaseDatabase.getInstance().getReference(); // جلب مرجع الجذر من Firebase

        exerciseList = exerciseQuery.getAllExercises(); // جلب جميع التمارين المخزنة محليًا في Room

        exerciseAdapter = new ExerciseAdapter(exerciseList, new ExerciseAdapter.OnItemClickListener() { // إنشاء Adapter مع Listener
            @Override
            public void onEditClick(int position) { // عند الضغط على تعديل
                // TODO: فتح شاشة تعديل التمرين
            }

            @Override
            public void onDeleteClick(int position) { // عند الضغط على حذف
                UserExercise exercise = exerciseList.get(position); // جلب التمرين حسب الموقع
                exerciseQuery.delete(exercise); // حذف التمرين من Room
                exerciseList.remove(position); // إزالة العنصر من القائمة
                exerciseAdapter.notifyItemRemoved(position); // تحديث RecyclerView لإزالة العنصر بصريًا
            }
        });

        rvExercises.setLayoutManager(new LinearLayoutManager(this)); // تحديد طريقة عرض العناصر عموديًا
        rvExercises.setAdapter(exerciseAdapter); // ربط RecyclerView بالـ Adapter

        linearCardio.setOnClickListener(v -> showReadyExercises("Cardio")); // عند الضغط عرض تمارين Cardio
        linearStrength.setOnClickListener(v -> showReadyExercises("Strength")); // عرض Strength
        linearYoga.setOnClickListener(v -> showReadyExercises("Yoga")); // عرض Yoga
        linearCycling.setOnClickListener(v -> showReadyExercises("Cycling")); // عرض Cycling

        btnClose.setOnClickListener(v -> finish()); // إغلاق الشاشة عند الضغط

        btnAddNewExercise.setOnClickListener(v -> { // عند الضغط على إضافة تمرين جديد
            startActivity(new Intent(Exercises.this, AddNewExercise.class)); // فتح شاشة AddNewExercise
        });

        findViewById(R.id.tvRunning).setOnClickListener(v -> addQuickExercise("Running", "Quick Add", 150)); // Quick Add Running
        findViewById(R.id.tvWeightlifting).setOnClickListener(v -> addQuickExercise("Weightlifting", "Quick Add", 200)); // Quick Add Weightlifting
    }

    private void addQuickExercise(String name, String category, int calories) { // دالة إضافة تمرين سريع
        UserExercise newExercise = new UserExercise( // إنشاء تمرين جديد
                name, category, 0, 0, 0, 0, calories, "", R.drawable.ic_launcher_foreground
        );

        exerciseQuery.insert(newExercise); // حفظ التمرين في Room
        saveExerciseToFirebase(newExercise); // حفظ التمرين في Firebase

        exerciseList.clear(); // مسح القائمة الحالية
        exerciseList.addAll(exerciseQuery.getAllExercises()); // إعادة تحميل كل التمارين
        exerciseAdapter.notifyDataSetChanged(); // تحديث RecyclerView بالكامل
        rvExercises.scrollToPosition(exerciseList.size() - 1); // التمرير لآخر عنصر
        Toast.makeText(this, name + " added successfully", Toast.LENGTH_SHORT).show(); // رسالة نجاح
    }

    private void showReadyExercises(String category) { // عرض تمارين جاهزة حسب الفئة
        exerciseList.clear(); // مسح القائمة الحالية

        switch (category) { // التحقق من الفئة المختارة
            case "Cardio":
                exerciseList.add(new UserExercise("Running", "Cardio", 0,0,0,30,200,"Warm up first", R.drawable.ic_launcher_foreground)); // إضافة تمرين
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

        exerciseAdapter.notifyDataSetChanged(); // تحديث العرض بعد إضافة العناصر
        rvExercises.scrollToPosition(0); // التمرير لأول عنصر
    }

    private void saveExerciseToFirebase(UserExercise exercise) { // حفظ التمرين في Firebase
        HashMap<String, Object> exerciseData = new HashMap<>(); // إنشاء HashMap لتجميع البيانات

        exerciseData.put("name", exercise.getName()); // إضافة الاسم
        exerciseData.put("category", exercise.getCategory()); // إضافة الفئة
        exerciseData.put("reps", exercise.getReps()); // إضافة التكرارات
        exerciseData.put("sets", exercise.getSets()); // إضافة المجموعات
        exerciseData.put("weight", exercise.getWeight()); // إضافة الوزن
        exerciseData.put("duration", exercise.getDuration()); // إضافة المدة
        exerciseData.put("calories", exercise.getCalories()); // إضافة السعرات
        exerciseData.put("note", exercise.getNote()); // إضافة الملاحظة
        exerciseData.put("imageRes", exercise.getImageRes()); // إضافة الصورة

        String uid = "default"; // UID افتراضي (يفضل استخدام FirebaseAuth لاحقًا)

        dbRef.child("users") // الدخول إلى users
                .child(uid) // الدخول إلى المستخدم
                .child("exercises") // الدخول إلى exercises
                .push() // إنشاء ID عشوائي فريد
                .updateChildren(exerciseData) // تحديث البيانات بدون حذف الأخرى
                .addOnSuccessListener(aVoid -> {
                    // نجاح الحفظ
                })
                .addOnFailureListener(e -> Toast.makeText(this,
                        "Failed to save to Firebase: " + e.getMessage(),
                        Toast.LENGTH_LONG).show()); // عرض رسالة خطأ
    }

    @Override
    protected void onResume() { // تُستدعى عند العودة للشاشة
        super.onResume(); // استدعاء دالة الأب
        exerciseList.clear(); // مسح القائمة
        exerciseList.addAll(exerciseQuery.getAllExercises()); // إعادة تحميل البيانات من Room
        exerciseAdapter.notifyDataSetChanged(); // تحديث العرض
    }
}