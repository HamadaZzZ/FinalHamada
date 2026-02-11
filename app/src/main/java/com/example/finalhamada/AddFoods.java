package com.example.finalhamada;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.UserFood;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * ============================================================
 * AddFoods Activity
 * ============================================================
 * شاشة إضافة طعام جديد.
 *
 * الوظائف:
 * 1️⃣ إدخال بيانات الطعام: الاسم، السعرات الحرارية، البروتين، الكربوهيدرات، الدهون
 * 2️⃣ التحقق من صحة البيانات المدخلة (Food Name و Calories على الأقل)
 * 3️⃣ تحويل القيم النصية إلى أرقام باستخدام try/catch
 * 4️⃣ حفظ بيانات الطعام في قاعدة بيانات محلية (Room Database)
 * 5️⃣ حفظ بيانات الطعام أيضًا على Firebase Realtime Database
 * 6️⃣ حفظ التاريخ الحالي مع كل طعام مضاف
 * 7️⃣ عرض رسائل Toast عند النجاح أو الخطأ
 *
 * ملاحظات تقنية:
 * - TextInputEditText: حقل إدخال نصي من مكتبة Material Design
 * - try/catch: لمعالجة الأخطاء عند تحويل النصوص لأرقام
 * - AppDataBase1: قاعدة بيانات Room
 * - UserFood: كائن يمثل طعام المستخدم
 * - Firebase Realtime Database: تخزين البيانات على الإنترنت بصيغة JSON
 * ============================================================
 */
public class AddFoods extends AppCompatActivity {

    /** حقول إدخال بيانات الطعام */
    private TextInputEditText etFoodName, etCalories, etProtein, etCarbs, etFat;

    /** زر حفظ الطعام */
    private Button btnSaveFood;

    /** Firebase Realtime Database */
    private DatabaseReference dbRef;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة لأول مرة.
     * تقوم بـ:
     * 1️⃣ ربط عناصر الواجهة بالكود
     * 2️⃣ تهيئة Firebase
     * 3️⃣ التعامل مع زر الحفظ
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foods);

        // ربط عناصر الواجهة
        etFoodName = findViewById(R.id.editTextFoodName);
        etCalories = findViewById(R.id.editTextCalories);
        etProtein = findViewById(R.id.editTextProtein);
        etCarbs = findViewById(R.id.editTextCarbs);
        etFat = findViewById(R.id.editTextFat);
        btnSaveFood = findViewById(R.id.buttonSaveFood);

        // تهيئة Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference();

        // التعامل مع الضغط على زر الحفظ
        btnSaveFood.setOnClickListener(v -> saveFood());
    }

    /**
     * saveFood
     * --------------------------------------------------
     * تتحقق من صحة البيانات المدخلة، تحول النصوص لأرقام،
     * ثم تحفظ الطعام في:
     * 1️⃣ قاعدة بيانات Room المحلية
     * 2️⃣ Firebase Realtime Database على الإنترنت
     * مع حفظ التاريخ الحالي.
     */
    private void saveFood() {
        // قراءة القيم من الحقول
        String foodName = etFoodName.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();
        String proteinStr = etProtein.getText().toString().trim();
        String carbsStr = etCarbs.getText().toString().trim();
        String fatStr = etFat.getText().toString().trim();

        // التحقق من البيانات الأساسية
        if (foodName.isEmpty() || caloriesStr.isEmpty()) {
            Toast.makeText(this, "Please fill in at least Food Name and Calories", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // تحويل النصوص إلى أرقام
            int calories = Integer.parseInt(caloriesStr);
            double protein = proteinStr.isEmpty() ? 0 : Double.parseDouble(proteinStr);
            double carbs = carbsStr.isEmpty() ? 0 : Double.parseDouble(carbsStr);
            double fat = fatStr.isEmpty() ? 0 : Double.parseDouble(fatStr);

            // إنشاء كائن UserFood
            UserFood userFood = new UserFood(foodName, calories, protein, carbs, fat);

            // حفظ التاريخ الحالي
            userFood.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

            // حفظ الطعام في قاعدة البيانات المحلية (Room)
            AppDataBase1 db = AppDataBase1.getDatabase(getApplicationContext());
            db.userFoodQuery().insert(userFood);

            // حفظ الطعام في Firebase Realtime Database
            saveFoodToFirebase(userFood);

            Toast.makeText(this, "Food saved successfully", Toast.LENGTH_SHORT).show();
            finish(); // إغلاق الشاشة بعد الحفظ

        } catch (NumberFormatException e) {
            // التعامل مع الأخطاء عند تحويل النصوص لأرقام
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * saveFoodToFirebase
     * --------------------------------------------------
     * تحفظ بيانات الطعام على Firebase Realtime Database بصيغة JSON.
     *
     * @param userFood كائن يمثل الطعام الذي أضيف
     */
    private void saveFoodToFirebase(UserFood userFood) {
        // تحويل كائن UserFood إلى HashMap لتخزينه في Firebase
        HashMap<String, Object> foodData = new HashMap<>();
        foodData.put("foodName", userFood.getFoodName());
        foodData.put("calories", userFood.getCalories());
        foodData.put("protein", userFood.getProtein());
        foodData.put("carbs", userFood.getCarbs());
        foodData.put("fat", userFood.getFat());
        foodData.put("date", userFood.getDate());

        // uid: يمكن استخدام FirebaseAuth للمستخدم الحالي، حالياً قيمة افتراضية
        String uid = "default"; // أو FirebaseAuth.getInstance().getCurrentUser().getUid();

        // حفظ البيانات داخل المسار: users -> uid -> foods -> push()
        dbRef.child("users")
                .child(uid)
                .child("foods")
                .push()
                .updateChildren(foodData)
                .addOnSuccessListener(aVoid -> {
                    // يمكن إضافة Toast إضافي إذا أردت
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save to Firebase: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}
