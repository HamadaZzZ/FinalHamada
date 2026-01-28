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

public class AddFoods extends AppCompatActivity {
    private TextInputEditText etFoodName, etCalories, etProtein, etCarbs, etFat;
    private Button btnSaveFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foods);

        etFoodName = findViewById(R.id.editTextFoodName);
        etCalories = findViewById(R.id.editTextCalories);
        etProtein = findViewById(R.id.editTextProtein);
        etCarbs = findViewById(R.id.editTextCarbs);
        etFat = findViewById(R.id.editTextFat);
        btnSaveFood = findViewById(R.id.buttonSaveFood);

        btnSaveFood.setOnClickListener(v -> {
            String foodName = etFoodName.getText().toString().trim();
            String caloriesStr = etCalories.getText().toString().trim();
            String proteinStr = etProtein.getText().toString().trim();
            String carbsStr = etCarbs.getText().toString().trim();
            String fatStr = etFat.getText().toString().trim();

            if (foodName.isEmpty() || caloriesStr.isEmpty()) {
                Toast.makeText(this, "Please fill in at least Food Name and Calories", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int calories = Integer.parseInt(caloriesStr);
                double protein = proteinStr.isEmpty() ? 0 : Double.parseDouble(proteinStr);
                double carbs = carbsStr.isEmpty() ? 0 : Double.parseDouble(carbsStr);
                double fat = fatStr.isEmpty() ? 0 : Double.parseDouble(fatStr);

                UserFood userFood = new UserFood(foodName, calories, protein, carbs, fat);
                userFood.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

                AppDataBase1 db = AppDataBase1.getDatabase(getApplicationContext());
                db.userFoodQuery().insert(userFood);

                Toast.makeText(this, "Food saved successfully", Toast.LENGTH_SHORT).show();
                finish();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
