package com.example.finalhamada.data.MyTaskTable;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_food_table")
public class UserFood implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String foodName;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private String date;

    public UserFood(int id, String foodName, int calories, double protein, double carbs, double fat, String date) {
        this.id = id;
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.date = date;
    }

    @Ignore
    public UserFood(String foodName, int calories, double protein, double carbs, double fat) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }
    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    protected UserFood(Parcel in) {
        id = in.readInt();
        foodName = in.readString();
        calories = in.readInt();
        protein = in.readDouble();
        carbs = in.readDouble();
        fat = in.readDouble();
        date = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(foodName);
        dest.writeInt(calories);
        dest.writeDouble(protein);
        dest.writeDouble(carbs);
        dest.writeDouble(fat);
        dest.writeString(date);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<UserFood> CREATOR = new Creator<UserFood>() {
        @Override
        public UserFood createFromParcel(Parcel in) { return new UserFood(in); }
        @Override
        public UserFood[] newArray(int size) { return new UserFood[size]; }
    };
}
