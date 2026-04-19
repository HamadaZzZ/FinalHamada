package com.example.finalhamada;

import android.annotation.SuppressLint;
import android.os.Bundle; // كلاس Bundle يستخدم لتمرير البيانات وحفظ حالة الشاشة عند إنشائها.
import android.widget.ArrayAdapter; // محول لربط مصفوفة من البيانات بـ Spinner.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.EditText; // تمثيل لحقل إدخال النص القابل للتعديل.
import android.widget.ImageView; // تمثيل لعنصر عرض الصور.
import android.widget.Spinner; // عنصر واجهة لعرض قائمة منسدلة من الخيارات.
import android.widget.TextView; // تمثيل لعنصر عرض النصوص الثابتة.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة أسفل الشاشة.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.
import androidx.core.graphics.Insets; // للتعامل مع أبعاد حواف النظام (System Bars).
import androidx.core.view.ViewCompat; // توفير ميزات التوافق لعناصر الواجهة.
import androidx.core.view.WindowInsetsCompat; // للتعامل مع مسافات النظام (أشرطة الحالة والتنقل).

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // كلاس قاعدة البيانات المحلية (Room).
import com.example.finalhamada.data.MyTaskTable.UserExercise; // كلاس النموذج (Model) لتمثيل التمرين الرياضي.
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery; // واجهة الاستعلامات (DAO) للتمارين.
import com.google.firebase.database.DatabaseReference; // مرجع للوصول لمكان محدد في قاعدة البيانات السحابية.
import com.google.firebase.database.FirebaseDatabase; // الوصول لقاعدة بيانات Firebase Realtime السحابية.

import java.util.HashMap; // بنية بيانات (مفتاح -> قيمة) لتسهيل إرسال البيانات للـ Firebase.

/**
 * AddNewExercise Activity: شاشة إضافة أو تعديل تمرين رياضي.
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدم:
 * 1. إدخال بيانات تمرين جديد (الاسم، الفئة، التكرارات، المجموعات، الوزن، المدة، السعرات، ملاحظات).
 * 2. تعديل بيانات تمرين موجود مسبقاً في قاعدة البيانات.
 * 3. حفظ البيانات محلياً في Room وسحابياً في Firebase Realtime Database.
 */
public class AddNewExercise extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Elements) ===
    private TextView tvTitle; // عنوان الشاشة (إضافة أو تعديل)
    private Button btnSaveExercise; // زر الحفظ أو التحديث
    private EditText etExerciseName, etReps, etSets, etWeight, etDuration, etCalories, etNote; // حقول إدخال بيانات التمرين
    private Spinner spCategory; // القائمة المنسدلة لاختيار فئة التمرين
    private ImageView ivExerciseImage; // أيقونة أو صورة التمرين

    // === أدوات البيانات (Database Tools) ===
    private DatabaseReference dbRef; // مرجع قاعدة البيانات السحابية
    private UserExerciseQuery exerciseQuery; // واجهة الوصول للبيانات المحلية
    private UserExercise existingExercise; // كائن لتخزين بيانات التمرين في حال وضع "التعديل"
    private boolean isEditMode = false;   // متغير لتحديد نوع العملية (إضافة = false, تعديل = true)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_add_new_exercise.xml بهذا الكود البرمجي
        setContentView(R.layout.activity_add_new_exercise);

        // ضبط واجهة المستخدم لتتوافق مع حواف الشاشة (Edge-to-Edge) وتجنب تداخل العناصر مع شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- 1. ربط العناصر البرمجية بالمعرفات (IDs) من ملف الـ XML ---
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

        // --- 2. إعداد القائمة المنسدلة (Spinner) لفئات التمارين ---
        String[] categories = {"Cardio", "Strength", "Yoga", "Cycling"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // --- 3. تهيئة قواعد البيانات (Room & Firebase) ---
        dbRef = FirebaseDatabase.getInstance().getReference();
        exerciseQuery = AppDataBase1.getDatabase(this).userExerciseQuery();

        // --- 4. التحقق مما إذا كان الهدف من فتح الشاشة هو "تعديل" تمرين موجود ---
        if (getIntent().hasExtra("exercise_id")) {
            isEditMode = true; // تفعيل وضع التعديل
            int exerciseId = getIntent().getIntExtra("exercise_id", -1); // الحصول على ID التمرين المختار
            loadExerciseData(exerciseId); // جلب البيانات القديمة وعرضها في الحقول
            tvTitle.setText("تعديل التمرين"); // تحديث العنوان ليناسب العملية
            btnSaveExercise.setText("تحديث البيانات"); // تحديث نص الزر
        }

        // --- 5. إعداد حدث النقر على زر الحفظ/التحديث ---
        btnSaveExercise.setOnClickListener(v -> saveExercise());
    }

    /**
     * دالة loadExerciseData: تجلب بيانات تمرين محدد من قاعدة البيانات المحلية وتعرضها في الواجهة.
     * @param id معرف التمرين الفريد في Room.
     */
    private void loadExerciseData(int id) {
        // تشغيل المهمة في خيط منفصل (Background Thread) لتجنب تجميد الواجهة
        new Thread(() -> {
            existingExercise = exerciseQuery.getExerciseById(id); // الاستعلام عن التمرين
            if (existingExercise != null) {
                // تحديث عناصر الواجهة يجب أن يتم في الخيط الرئيسي (Main Thread)
                runOnUiThread(() -> {
                    etExerciseName.setText(existingExercise.getName());
                    etReps.setText(String.valueOf(existingExercise.getReps()));
                    etSets.setText(String.valueOf(existingExercise.getSets()));
                    etWeight.setText(String.valueOf(existingExercise.getWeight()));
                    etDuration.setText(String.valueOf(existingExercise.getDuration()));
                    etCalories.setText(String.valueOf(existingExercise.getCalories()));
                    etNote.setText(existingExercise.getNote());
                    
                    // تحديد الفئة الصحيحة في القائمة المنسدلة بناءً على البيانات المسترجعة
                    for (int i = 0; i < spCategory.getCount(); i++) {
                        if (spCategory.getItemAtPosition(i).toString().equalsIgnoreCase(existingExercise.getCategory())) {
                            spCategory.setSelection(i);
                            break;
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * دالة saveExercise: تقرأ البيانات من الحقول وتنفذ عملية الحفظ (إما إدراج جديد أو تحديث).
     */
    private void saveExercise() {
        // استخراج القيم من الحقول مع تنظيف المسافات الزائدة
        String name = etExerciseName.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();
        int reps = parseOrZero(etReps.getText().toString());
        int sets = parseOrZero(etSets.getText().toString());
        int weight = parseOrZero(etWeight.getText().toString());
        int duration = parseOrZero(etDuration.getText().toString());
        int calories = parseOrZero(etCalories.getText().toString());
        String note = etNote.getText().toString().trim();

        // التحقق من إدخال اسم التمرين (حقل إلزامي)
        if (name.isEmpty()) {
            etExerciseName.setError("يرجى إدخال اسم التمرين");
            return;
        }

        if (isEditMode && existingExercise != null) {
            // --- وضع التعديل: تحديث بيانات الكائن الحالي ---
            existingExercise.setName(name);
            existingExercise.setCategory(category);
            existingExercise.setReps(reps);
            existingExercise.setSets(sets);
            existingExercise.setWeight(weight);
            existingExercise.setDuration(duration);
            existingExercise.setCalories(calories);
            existingExercise.setNote(note);

            new Thread(() -> {
                exerciseQuery.update(existingExercise); // تحديث في قاعدة البيانات المحلية
                saveToFirebase(existingExercise);      // تحديث (أو إضافة) في السحابة
                runOnUiThread(() -> {
                    Toast.makeText(this, "تم تحديث التمرين بنجاح!", Toast.LENGTH_SHORT).show();
                    finish(); // العودة للشاشة السابقة
                });
            }).start();

        } else {
            // --- وضع الإضافة: إنشاء تمرين جديد بالكامل ---
            UserExercise newExercise = new UserExercise(name, category, reps, sets, weight, duration, calories, note, R.drawable.ic_exercise);
            new Thread(() -> {
                exerciseQuery.insert(newExercise); // إدراج جديد في Room
                saveToFirebase(newExercise);      // إدراج جديد في Firebase
                runOnUiThread(() -> {
                    Toast.makeText(this, "تم حفظ التمرين الجديد!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        }
    }

    /**
     * دالة saveToFirebase: لحفظ بيانات التمرين في قاعدة البيانات السحابية (Realtime Database).
     * @param exercise كائن التمرين المراد حفظه.
     */
    private void saveToFirebase(UserExercise exercise) {
        // تجهيز خريطة البيانات (Key-Value) لتخزينها في JSON السحابي
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", exercise.getName());
        map.put("category", exercise.getCategory());
        map.put("reps", exercise.getReps());
        map.put("sets", exercise.getSets());
        map.put("weight", exercise.getWeight());
        map.put("duration", exercise.getDuration());
        map.put("calories", exercise.getCalories());
        map.put("note", exercise.getNote());

        // في تطبيق فعلي يجب استخدام UID المستخدم من FirebaseAuth.getInstance().getCurrentUser().getUid()
        String uid = "default_user"; 
        dbRef.child("users").child(uid).child("exercises").push().setValue(map);
    }

    /**
     * دالة مساعدة parseOrZero: تحويل النص إلى رقم صحيح بشكل آمن مع التعامل مع النصوص الفارغة.
     * @param value النص المراد تحويله.
     * @return الرقم الصحيح المقابل أو 0 في حال حدوث خطأ أو كان النص فارغاً.
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
