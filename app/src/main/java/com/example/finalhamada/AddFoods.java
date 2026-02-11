package com.example.finalhamada;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.UserFood;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 5️⃣ حفظ التاريخ الحالي مع كل طعام مضاف
 * 6️⃣ عرض رسائل Toast عند النجاح أو الخطأ
 *
 * ملاحظات تقنية:
 * - TextInputEditText: حقل إدخال نصي من مكتبة Material Design
 * - try/catch: لمعالجة الأخطاء عند تحويل النصوص لأرقام
 * - AppDataBase1: قاعدة بيانات Room
 * - UserFood: كائن يمثل طعام المستخدم
 * ============================================================
 */
public class AddFoods extends AppCompatActivity {

    /** حقول إدخال بيانات الطعام */
    private TextInputEditText etFoodName, etCalories, etProtein, etCarbs, etFat;

    /** زر حفظ الطعام */
    private Button btnSaveFood;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة لأول مرة.
     * تقوم بـ:
     * 1️⃣ ربط عناصر الواجهة بالكود
     * 2️⃣ التعامل مع زر الحفظ
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

        // التعامل مع الضغط على زر الحفظ
        btnSaveFood.setOnClickListener(v -> saveFood());
    }

    /**
     * saveFood
     * --------------------------------------------------
     * تتحقق من صحة البيانات المدخلة، تحول النصوص لأرقام،
     * ثم تحفظ الطعام في قاعدة بيانات Room مع التاريخ الحالي.
     */
    private void saveFood() {
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

            // حفظ الطعام في قاعدة البيانات Room
            AppDataBase1 db = AppDataBase1.getDatabase(getApplicationContext());
            db.userFoodQuery().insert(userFood);

            Toast.makeText(this, "Food saved successfully", Toast.LENGTH_SHORT).show();
            finish(); // إغلاق الشاشة بعد الحفظ

        } catch (NumberFormatException e) {
            // التعامل مع الأخطاء عند تحويل النصوص لأرقام
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
