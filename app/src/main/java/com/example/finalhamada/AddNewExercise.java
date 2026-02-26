package com.example.finalhamada; // تعريف الـ Package: مكان هذا الكلاس داخل المشروع (تنظيم الملفات)

import android.annotation.SuppressLint; // SuppressLint: لإخفاء تحذيرات Lint محددة من Android Studio
import android.os.Bundle; // Bundle: يحمل بيانات حالة الـ Activity عند إنشائها/إعادة إنشائها
import android.widget.ArrayAdapter; // ArrayAdapter: يربط Array بيانات مع Spinner لعرض الخيارات
import android.widget.Button; // Button: عنصر زر في الواجهة
import android.widget.EditText; // EditText: حقل إدخال نص من المستخدم
import android.widget.ImageView; // ImageView: لعرض صورة داخل الواجهة
import android.widget.Spinner; // Spinner: قائمة منسدلة لاختيار عنصر واحد
import android.widget.TextView; // TextView: لعرض نص داخل الواجهة
import android.widget.Toast; // Toast: رسالة قصيرة تظهر للمستخدم

import androidx.appcompat.app.AppCompatActivity; // AppCompatActivity: كلاس أساسي لأي Activity مع دعم خصائص حديثة
import androidx.core.graphics.Insets; // Insets: يمثل هوامش النظام (Status/Nav bars) لاستخدام Edge-to-Edge
import androidx.core.view.ViewCompat; // ViewCompat: أدوات متوافقة للتعامل مع Views عبر نسخ أندرويد المختلفة
import androidx.core.view.WindowInsetsCompat; // WindowInsetsCompat: للتعامل مع Insets الخاصة بالنظام

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // AppDataBase1: قاعدة بيانات Room المحلية الخاصة بالتطبيق
import com.example.finalhamada.data.MyTaskTable.UserExercise; // UserExercise: Model يمثل بيانات تمرين المستخدم
import com.google.firebase.database.DatabaseReference; // DatabaseReference: مرجع لمسار داخل Firebase Realtime Database
import com.google.firebase.database.FirebaseDatabase; // FirebaseDatabase: الدخول إلى Realtime Database

import java.util.HashMap; // HashMap: تجميع البيانات Key->Value قبل إرسالها لـ Firebase (تتحول JSON تلقائياً)

/**
 * ============================================================
 * AddNewExercise Activity
 * ============================================================
 * شاشة إضافة تمرين جديد للمستخدم.
 */
public class AddNewExercise extends AppCompatActivity { // تعريف Activity جديدة باسم AddNewExercise وترث من AppCompatActivity

    private TextView tvTitle; // tvTitle: TextView لعرض عنوان الشاشة

    private Button btnSaveExercise; // btnSaveExercise: زر لحفظ التمرين

    private EditText etExerciseName, etReps, etSets, etWeight, etDuration, etCalories, etNote; // حقول إدخال بيانات التمرين

    private Spinner spCategory; // spCategory: Spinner لاختيار فئة التمرين

    private ImageView ivExerciseImage; // ivExerciseImage: ImageView لعرض صورة التمرين

    private DatabaseReference dbRef; // dbRef: مرجع Firebase لتحديد مكان حفظ البيانات داخل Realtime Database

    @SuppressLint("MissingInflatedId") // إخفاء تحذير MissingInflatedId (عادةً بسبب اختلاف IDs بين layouts أو مشاكل تضخيم)
    @Override // Override: نعدل سلوك دالة من الكلاس الأب
    protected void onCreate(Bundle savedInstanceState) { // onCreate: تُستدعى عند إنشاء الشاشة أول مرة
        super.onCreate(savedInstanceState); // استدعاء onCreate للأب لتهيئة دورة حياة الـ Activity

        setContentView(R.layout.activity_add_new_exercise); // ربط ملف XML activity_add_new_exercise بهذه الشاشة

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // Listener لضبط padding حسب هوامش النظام (Edge-to-Edge)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // جلب هوامش شريط الحالة والتنقل
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // تطبيق padding حتى لا تختفي العناصر خلف شريط النظام
            return insets; // إرجاع insets بعد التعامل معها
        }); // نهاية listener

        tvTitle = findViewById(R.id.tvTitle); // ربط tvTitle بعنصر TextView من XML
        btnSaveExercise = findViewById(R.id.btnSaveExercise); // ربط زر الحفظ من XML
        etExerciseName = findViewById(R.id.etExerciseName); // ربط حقل اسم التمرين
        etReps = findViewById(R.id.etReps); // ربط حقل عدد التكرارات Reps
        etSets = findViewById(R.id.etSets); // ربط حقل عدد المجموعات Sets
        etWeight = findViewById(R.id.etWeight); // ربط حقل الوزن Weight
        etDuration = findViewById(R.id.etDuration); // ربط حقل المدة Duration
        etCalories = findViewById(R.id.etCalories); // ربط حقل السعرات Calories
        etNote = findViewById(R.id.etNote); // ربط حقل الملاحظة Note
        spCategory = findViewById(R.id.spCategory); // ربط Spinner الفئات
        ivExerciseImage = findViewById(R.id.ivExerciseImage); // ربط ImageView الخاصة بصورة التمرين

        String[] categories = {"Cardio", "Strength", "Yoga", "Cycling"}; // مصفوفة نصوص تمثل فئات التمارين التي ستظهر في Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, // إنشاء Adapter لربط الفئات بالـ Spinner
                android.R.layout.simple_spinner_item, categories); // simple_spinner_item: تصميم افتراضي لعنصر داخل spinner + categories: البيانات
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // تحديد تصميم العناصر عند فتح القائمة المنسدلة
        spCategory.setAdapter(adapter); // ربط الـ Spinner بالـ Adapter لعرض الفئات

        dbRef = FirebaseDatabase.getInstance().getReference(); // تهيئة Firebase: أخذ مرجع الجذر Root من Realtime Database

        btnSaveExercise.setOnClickListener(v -> saveExercise()); // ClickListener: عند الضغط على زر الحفظ استدعاء دالة saveExercise()
    } // نهاية onCreate

    private void saveExercise() { // saveExercise: تقرأ البيانات وتتحقق منها ثم تحفظها في Room و Firebase

        String name = etExerciseName.getText().toString().trim(); // قراءة اسم التمرين من EditText وتحويله String وإزالة الفراغات
        String category = spCategory.getSelectedItem().toString(); // قراءة الفئة المختارة من Spinner وتحويلها إلى String

        int reps = parseOrZero(etReps.getText().toString()); // تحويل reps إلى رقم صحيح أو 0 إذا غير صالح (باستخدام parseOrZero)
        int sets = parseOrZero(etSets.getText().toString()); // تحويل sets إلى رقم صحيح أو 0 إذا غير صالح
        int weight = parseOrZero(etWeight.getText().toString()); // تحويل weight إلى رقم صحيح أو 0 إذا غير صالح
        int duration = parseOrZero(etDuration.getText().toString()); // تحويل duration إلى رقم صحيح أو 0 إذا غير صالح
        int calories = parseOrZero(etCalories.getText().toString()); // تحويل calories إلى رقم صحيح أو 0 إذا غير صالح

        String note = etNote.getText().toString().trim(); // قراءة الملاحظة وإزالة الفراغات

        if (name.isEmpty()) { // Validation: إذا اسم التمرين فارغ
            etExerciseName.setError("Name is required"); // عرض خطأ على حقل الاسم
            return; // إيقاف تنفيذ الدالة لأنه لا يمكن الحفظ بدون اسم
        } // نهاية التحقق من الاسم

        UserExercise exercise = new UserExercise( // إنشاء كائن UserExercise لتجميع بيانات التمرين
                name, // تمرير اسم التمرين
                category, // تمرير الفئة المختارة
                reps, // تمرير عدد التكرارات
                sets, // تمرير عدد المجموعات
                weight, // تمرير الوزن
                duration, // تمرير المدة
                calories, // تمرير السعرات
                note, // تمرير الملاحظة
                R.drawable.ic_info // تمرير صورة افتراضية للتمرين (Resource ID)
        ); // نهاية إنشاء الكائن

        AppDataBase1 db = AppDataBase1.getDatabase(AddNewExercise.this); // جلب instance لقاعدة Room (Singleton) باستخدام Context
        db.userExerciseQuery().insert(exercise); // إدخال التمرين داخل جدول التمارين في Room عبر DAO

        saveExerciseToFirebase(exercise); // حفظ نفس بيانات التمرين أيضًا على Firebase

        Toast.makeText(this, "Exercise saved successfully", Toast.LENGTH_SHORT).show(); // عرض رسالة نجاح للمستخدم
        finish(); // إغلاق الشاشة والعودة للشاشة السابقة بعد الحفظ
    } // نهاية saveExercise

    private void saveExerciseToFirebase(UserExercise exercise) { // دالة لحفظ التمرين على Firebase

        HashMap<String, Object> exerciseData = new HashMap<>(); // إنشاء HashMap لتجميع البيانات كـ Key->Value قبل رفعها (Firebase يحولها JSON)
        // HashMap كيف بتشتغل: كل Key ينحسب له hashCode -> يحدد bucket داخل الذاكرة -> يخزن (key,value) -> الوصول سريع O(1) غالبًا

        exerciseData.put("name", exercise.getName()); // تخزين اسم التمرين داخل الخريطة بمفتاح "name"
        exerciseData.put("category", exercise.getCategory()); // تخزين الفئة بمفتاح "category"
        exerciseData.put("reps", exercise.getReps()); // تخزين reps بمفتاح "reps"
        exerciseData.put("sets", exercise.getSets()); // تخزين sets بمفتاح "sets"
        exerciseData.put("weight", exercise.getWeight()); // تخزين الوزن بمفتاح "weight"
        exerciseData.put("duration", exercise.getDuration()); // تخزين المدة بمفتاح "duration"
        exerciseData.put("calories", exercise.getCalories()); // تخزين السعرات بمفتاح "calories"
        exerciseData.put("note", exercise.getNote()); // تخزين الملاحظة بمفتاح "note"
        exerciseData.put("imageRes", exercise.getImageRes()); // تخزين رقم الصورة (Resource ID) بمفتاح "imageRes"

        String uid = "default"; // uid افتراضي الآن؛ لاحقًا الأفضل استخدام UID الحقيقي للمستخدم من FirebaseAuth

        dbRef.child("users") // اختيار/إنشاء عقدة users تحت Root
                .child(uid) // اختيار/إنشاء عقدة المستخدم المحدد uid
                .child("exercises") // اختيار/إنشاء عقدة exercises الخاصة بتمارين هذا المستخدم
                .push() // push(): ينشئ ID عشوائي فريد للعنصر الجديد حتى لا تتعارض العناصر (مثل -Nxyz123...)
                .updateChildren(exerciseData) // updateChildren: يكتب المفاتيح الموجودة داخل map بدون مسح باقي البيانات في نفس المسار
                .addOnSuccessListener(aVoid -> { // Listener: ينفذ عند نجاح رفع البيانات على Firebase
                    // نجاح الحفظ على Firebase (هنا تركتها فارغة)
                })
                .addOnFailureListener(e -> Toast.makeText(this, // Listener: ينفذ عند فشل رفع البيانات
                        "Failed to save to Firebase: " + e.getMessage(), // إظهار سبب الفشل القادم من Firebase
                        Toast.LENGTH_LONG).show()); // عرض الرسالة لمدة أطول
    } // نهاية saveExerciseToFirebase

    private int parseOrZero(String value) { // دالة مساعدة: تحول النص إلى رقم صحيح أو 0 إذا كان النص غير صالح
        if (value == null || value.trim().isEmpty()) return 0; // إذا القيمة null أو فاضية -> رجع 0 فورًا
        try { // محاولة التحويل إلى int
            return Integer.parseInt(value.trim()); // parseInt: تحويل String إلى int بعد trim
        } catch (Exception e) { // إذا صار خطأ مثل وجود حروف
            return 0; // رجع 0 بدل ما ينهار التطبيق
        } // نهاية catch
    } // نهاية parseOrZero
} // نهاية الكلاس