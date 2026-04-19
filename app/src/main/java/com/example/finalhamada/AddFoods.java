package com.example.finalhamada;

import android.os.Bundle; // كلاس Bundle يستخدم لتمرير البيانات وحفظ حالة الشاشة عند إنشائها.
import android.widget.Button; // تمثيل لزر الضغط في واجهة المستخدم.
import android.widget.Toast; // أداة لعرض رسائل نصية منبثقة قصيرة أسفل الشاشة.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي الذي يجب أن ترث منه أي شاشة لضمان التوافقية.

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // كلاس قاعدة البيانات المحلية (Room).
import com.example.finalhamada.data.MyTaskTable.UserFood; // كلاس النموذج (Model) لتمثيل وجبة الطعام.

import com.google.android.material.textfield.TextInputEditText; // حقل إدخال نص متطور من مكتبة Material Design.
import com.google.firebase.database.DatabaseReference; // مرجع للوصول لمكان محدد في قاعدة البيانات السحابية.
import com.google.firebase.database.FirebaseDatabase; // الوصول لقاعدة بيانات Firebase Realtime السحابية.

import java.text.SimpleDateFormat; // كلاس لتنسيق التاريخ والوقت كنص (مثلاً: 2023-10-25).
import java.util.Date; // كلاس يمثل التاريخ والوقت الحالي للنظام.
import java.util.HashMap; // بنية بيانات (مفتاح -> قيمة) لتسهيل إرسال البيانات للـ Firebase.
import java.util.Locale; // كلاس لتحديد اللغة أو المنطقة الجغرافية عند تنسيق النصوص.

/**
 * AddFoods Activity: شاشة إضافة وجبة طعام جديدة.
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدم إدخال تفاصيل وجبة تناولها (الاسم، السعرات، البروتين، الكربوهيدرات، الدهون)
 * ثم تقوم بحفظ هذه البيانات في مكانين:
 * 1. قاعدة البيانات المحلية (Room) للعمل بدون إنترنت.
 * 2. قاعدة البيانات السحابية (Firebase) للمزامنة والنسخ الاحتياطي.
 */
public class AddFoods extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Elements) ===
    private TextInputEditText etFoodName, etCalories, etProtein, etCarbs, etFat; // حقول إدخال بيانات الوجبة
    private Button btnSaveFood; // زر حفظ الوجبة

    // === كائنات خدمات Firebase السحابية ===
    private DatabaseReference dbRef; // مرجع للتعامل مع قاعدة البيانات السحابية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_add_foods.xml بهذا الكود البرمجي
        setContentView(R.layout.activity_add_foods);

        // --- ربط المتغيرات بالمعرفات (IDs) الموجودة في ملف الـ XML ---
        etFoodName = findViewById(R.id.editTextFoodName);
        etCalories = findViewById(R.id.editTextCalories);
        etProtein = findViewById(R.id.editTextProtein);
        etCarbs = findViewById(R.id.editTextCarbs);
        etFat = findViewById(R.id.editTextFat);
        btnSaveFood = findViewById(R.id.buttonSaveFood);

        // تهيئة الوصول لقاعدة البيانات السحابية (Realtime Database)
        dbRef = FirebaseDatabase.getInstance().getReference();

        // إعداد حدث النقر على زر الحفظ
        btnSaveFood.setOnClickListener(v -> saveFood());
    }

    /**
     * دالة saveFood: تقوم بقراءة البيانات من الحقول، التحقق منها، ثم حفظها محلياً وسحابياً.
     */
    private void saveFood() {

        // قراءة النصوص من الحقول وحذف المسافات الزائدة
        String foodName = etFoodName.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();
        String proteinStr = etProtein.getText().toString().trim();
        String carbsStr = etCarbs.getText().toString().trim();
        String fatStr = etFat.getText().toString().trim();

        // --- 1. التحقق من المدخلات الأساسية ---
        if (foodName.isEmpty() || caloriesStr.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال اسم الطعام والسعرات الحرارية على الأقل", Toast.LENGTH_SHORT).show();
            return; // توقف عن إكمال العملية
        }

        try {
            // تحويل النصوص إلى أرقام (الأرقام الصحيحة والعشرية)
            int calories = Integer.parseInt(caloriesStr);
            double protein = proteinStr.isEmpty() ? 0 : Double.parseDouble(proteinStr);
            double carbs = carbsStr.isEmpty() ? 0 : Double.parseDouble(carbsStr);
            double fat = fatStr.isEmpty() ? 0 : Double.parseDouble(fatStr);

            // --- 2. إنشاء كائن الوجبة (Model Object) ---
            UserFood userFood = new UserFood(foodName, calories, protein, carbs, fat);
            
            // تحديد تاريخ اليوم وتخزينه مع الوجبة
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            userFood.setDate(currentDate);

            // --- 3. الحفظ في قاعدة البيانات المحلية (Room) ---
            // الحصول على نسخة قاعدة البيانات المحلية
            AppDataBase1 db = AppDataBase1.getDatabase(getApplicationContext());
            // إدراج الوجبة في الجدول المخصص
            db.userFoodQuery().insert(userFood);

            // --- 4. الحفظ في قاعدة البيانات السحابية (Firebase) ---
            saveFoodToFirebase(userFood);

            // إظهار رسالة نجاح وإغلاق الشاشة للعودة للقائمة
            Toast.makeText(this, "تم حفظ الوجبة بنجاح", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            // في حال إدخال نصوص في حقول الأرقام
            Toast.makeText(this, "يرجى التأكد من إدخال أرقام صحيحة في حقول السعرات والعناصر الغذائية", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * دالة saveFoodToFirebase: لحفظ بيانات الوجبة في السحابة للمزامنة.
     * @param userFood كائن الوجبة المراد حفظه.
     */
    private void saveFoodToFirebase(UserFood userFood) {
        // تجهيز البيانات في HashMap (مفتاح -> قيمة) لتتوافق مع نظام JSON في Firebase
        HashMap<String, Object> foodData = new HashMap<>();
        foodData.put("foodName", userFood.getFoodName());
        foodData.put("calories", userFood.getCalories());
        foodData.put("protein", userFood.getProtein());
        foodData.put("carbs", userFood.getCarbs());
        foodData.put("fat", userFood.getFat());
        foodData.put("date", userFood.getDate());

        // استخدام معرف افتراضي (في تطبيق حقيقي يفضل استخدام UID المستخدم المسجل)
        String uid = "default_user";

        /**
         * المسار في السحابة: users -> [User_ID] -> foods
         * push() تنشئ معرفاً فريداً تلقائياً لكل وجبة تضاف.
         */
        dbRef.child("users")
                .child(uid)
                .child("foods")
                .push()
                .updateChildren(foodData)
                .addOnSuccessListener(aVoid -> {
                    // تم الحفظ بنجاح في السحابة
                })
                .addOnFailureListener(e -> {
                    // فشل الحفظ في السحابة (مثلاً بسبب انقطاع الإنترنت)
                    Toast.makeText(this, "فشل المزامنة مع السحابة: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
