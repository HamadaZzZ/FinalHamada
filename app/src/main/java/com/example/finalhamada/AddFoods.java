/**
 * تعريف الحزمة التي يوجد بداخلها هذا الكلاس.
 * كل كلاس داخل هذا المسار ينتمي لنفس الـ package.
 */
package com.example.finalhamada;

import android.os.Bundle;
// Bundle: يُستخدم لنقل بيانات حالة الـ Activity عند إنشائها.

import android.widget.Button;
// Button: عنصر زر في واجهة المستخدم.

import android.widget.Toast;
// Toast: رسالة قصيرة تظهر للمستخدم لفترة مؤقتة.

import androidx.appcompat.app.AppCompatActivity;
// AppCompatActivity: الكلاس الأساسي لأي شاشة (Activity).

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
// AppDataBase1: كلاس يمثل قاعدة بيانات Room المحلية في التطبيق.

import com.example.finalhamada.data.MyTaskTable.UserFood;
// UserFood: Model يمثل كائن الطعام الذي سيدخل إلى قاعدة البيانات.

import com.google.android.material.textfield.TextInputEditText;
// TextInputEditText: حقل إدخال من مكتبة Material Design.

import com.google.firebase.database.DatabaseReference;
// DatabaseReference: مرجع يشير لمسار معين داخل Firebase Realtime Database.

import com.google.firebase.database.FirebaseDatabase;
// FirebaseDatabase: نقطة الدخول إلى Firebase Realtime Database.

import java.text.SimpleDateFormat;
// SimpleDateFormat: لتنسيق التاريخ كنص.

import java.util.Date;
// Date: يمثل التاريخ والوقت الحالي.

import java.util.HashMap;
// HashMap: بنية بيانات (Key → Value) لتجميع البيانات قبل إرسالها لـ Firebase.

import java.util.Locale;
// Locale: لتحديد لغة/تنسيق النظام عند تنسيق التاريخ.

/**
 * ============================================================
 * AddFoods Activity
 * ============================================================
 * شاشة إضافة طعام جديد وحفظه:
 * - في قاعدة بيانات محلية (Room)
 * - وفي Firebase Realtime Database
 * ============================================================
 */
public class AddFoods extends AppCompatActivity {

    /** حقول إدخال بيانات الطعام */
    private TextInputEditText etFoodName, etCalories, etProtein, etCarbs, etFat;
    // متغيرات تمثل عناصر إدخال النص من الواجهة.

    /** زر حفظ الطعام */
    private Button btnSaveFood;
    // زر عند الضغط عليه يتم تنفيذ عملية الحفظ.

    /** مرجع Firebase Realtime Database */
    private DatabaseReference dbRef;
    // يستخدم للوصول إلى مسار معين داخل قاعدة البيانات السحابية.

    /**
     * onCreate:
     * يتم استدعاؤها أول مرة عند إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // استدعاء onCreate من الكلاس الأب (ضروري لتهيئة الشاشة).

        setContentView(R.layout.activity_add_foods);
        // ربط ملف XML بهذه الشاشة.

        // ربط عناصر الواجهة بالمتغيرات
        etFoodName = findViewById(R.id.editTextFoodName);
        etCalories = findViewById(R.id.editTextCalories);
        etProtein = findViewById(R.id.editTextProtein);
        etCarbs = findViewById(R.id.editTextCarbs);
        etFat = findViewById(R.id.editTextFat);
        btnSaveFood = findViewById(R.id.buttonSaveFood);

        // تهيئة Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference();
        // getInstance(): يرجع نسخة Firebase.
        // getReference(): يرجع مرجع الجذر Root في قاعدة البيانات.

        // عند الضغط على زر الحفظ يتم استدعاء saveFood()
        btnSaveFood.setOnClickListener(v -> saveFood());
        // setOnClickListener: Listener ينفذ الكود عند الضغط.
    }

    /**
     * saveFood:
     * تقرأ البيانات، تتحقق منها، ثم تحفظها.
     */
    private void saveFood() {

        // قراءة القيم من الحقول وتحويلها إلى String
        String foodName = etFoodName.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();
        String proteinStr = etProtein.getText().toString().trim();
        String carbsStr = etCarbs.getText().toString().trim();
        String fatStr = etFat.getText().toString().trim();
        // getText(): تجلب النص.
        // toString(): تحويله إلى String.
        // trim(): إزالة الفراغات الزائدة.

        // التحقق أن اسم الطعام والسعرات غير فارغين
        if (foodName.isEmpty() || caloriesStr.isEmpty()) {

            Toast.makeText(this,
                    "Please fill in at least Food Name and Calories",
                    Toast.LENGTH_SHORT).show();

            return;
            // إيقاف تنفيذ الدالة إذا البيانات ناقصة.
        }

        try {

            // تحويل السعرات إلى int
            int calories = Integer.parseInt(caloriesStr);
            // parseInt: تحويل String إلى رقم صحيح.

            // إذا الحقل فارغ نحط 0، غير ذلك نحوله لرقم
            double protein = proteinStr.isEmpty() ? 0 : Double.parseDouble(proteinStr);
            double carbs = carbsStr.isEmpty() ? 0 : Double.parseDouble(carbsStr);
            double fat = fatStr.isEmpty() ? 0 : Double.parseDouble(fatStr);
            // ?: هذا يسمى Ternary Operator.

            // إنشاء كائن يمثل الطعام
            UserFood userFood = new UserFood(foodName, calories, protein, carbs, fat);
            // هنا أنشأنا Object يحتوي كل بيانات الطعام.

            // حفظ التاريخ الحالي داخل الكائن
            userFood.setDate(
                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(new Date())
            );
            // new Date(): يجلب الوقت الحالي.
            // SimpleDateFormat: ينسق التاريخ.
            // format(): يحول التاريخ إلى String.

            // الحصول على نسخة من قاعدة البيانات المحلية Room
            AppDataBase1 db = AppDataBase1.getDatabase(getApplicationContext());
            // getDatabase(): ترجع Singleton لقاعدة البيانات.

            db.userFoodQuery().insert(userFood);
            // userFoodQuery(): DAO.
            // insert(): إدخال الكائن في قاعدة البيانات المحلية.

            // حفظ البيانات أيضاً على Firebase
            saveFoodToFirebase(userFood);

            Toast.makeText(this,
                    "Food saved successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
            // إغلاق الشاشة بعد الحفظ.

        } catch (NumberFormatException e) {
            // إذا فشل التحويل من نص إلى رقم.

            Toast.makeText(this,
                    "Please enter valid numbers",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * saveFoodToFirebase:
     * تحفظ الطعام داخل Firebase بصيغة JSON.
     */
    private void saveFoodToFirebase(UserFood userFood) {

        // إنشاء HashMap لتجميع البيانات
        HashMap<String, Object> foodData = new HashMap<>();

        foodData.put("foodName", userFood.getFoodName());
        foodData.put("calories", userFood.getCalories());
        foodData.put("protein", userFood.getProtein());
        foodData.put("carbs", userFood.getCarbs());
        foodData.put("fat", userFood.getFat());
        foodData.put("date", userFood.getDate());
        // put(): تخزين Key → Value.
        // Firebase سيحول HashMap إلى JSON تلقائياً.

        // UID افتراضي (يفضل استخدام FirebaseAuth للمستخدم الحقيقي)
        String uid = "default";

        /**
         * المسار النهائي:
         * users
         *   └── uid
         *         └── foods
         *               └── push()
         */
        dbRef.child("users")      // الدخول إلى عقدة users
                .child(uid)       // الدخول إلى المستخدم المحدد
                .child("foods")   // الدخول إلى foods
                .push()           // إنشاء ID عشوائي جديد
                .updateChildren(foodData)
                // updateChildren: يضيف البيانات بدون حذف الباقي

                .addOnSuccessListener(aVoid -> {
                    // يتم تنفيذ هذا الكود إذا نجح الحفظ
                })

                .addOnFailureListener(e -> {
                    // يتم تنفيذ هذا الكود إذا فشل الحفظ

                    Toast.makeText(this,
                            "Failed to save to Firebase: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}