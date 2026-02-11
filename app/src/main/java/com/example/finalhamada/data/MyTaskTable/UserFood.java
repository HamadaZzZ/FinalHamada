package com.example.finalhamada.data.MyTaskTable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * ============================================================
 * UserFood
 * ============================================================
 * كائن يمثل طعام المستخدم لتخزينه في قاعدة بيانات Room.
 *
 * الخصائص:
 * - id: معرف فريد لكل طعام (Primary Key, autoGenerate)
 * - foodName: اسم الطعام
 * - calories: السعرات الحرارية
 * - protein: البروتين بالجرام
 * - carbs: الكربوهيدرات بالجرام
 * - fat: الدهون بالجرام
 * - date: تاريخ إضافة الطعام (yyyy-MM-dd)
 *
 * مزايا تقنية:
 * 1️⃣ Room Database: @Entity, @PrimaryKey, @Ignore
 * 2️⃣ Parcelable: لتمرير الكائن بين الأنشطة (Activities)
 *
 * استخدامات:
 * - حفظ الطعام في جدول user_food_table
 * - تمرير كائن UserFood بين Activities عبر Intent
 * ============================================================
 */
@Entity(tableName = "user_food_table")
public class UserFood implements Parcelable {

    /** المعرف الفريد للطعام (Primary Key) */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /** اسم الطعام */
    private String foodName;

    /** السعرات الحرارية */
    private int calories;

    /** البروتين بالجرام */
    private double protein;

    /** الكربوهيدرات بالجرام */
    private double carbs;

    /** الدهون بالجرام */
    private double fat;

    /** تاريخ إضافة الطعام */
    private String date;

    /**
     * Constructor كامل لجميع الخصائص
     *
     * @param id المعرف الفريد للطعام
     * @param foodName اسم الطعام
     * @param calories السعرات الحرارية
     * @param protein البروتين بالجرام
     * @param carbs الكربوهيدرات بالجرام
     * @param fat الدهون بالجرام
     * @param date تاريخ الإضافة
     */
    public UserFood(int id, String foodName, int calories, double protein, double carbs, double fat, String date) {
        this.id = id;
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.date = date;
    }

    /**
     * Constructor لتسهيل إنشاء كائن بدون id وبدون تاريخ
     * @param foodName اسم الطعام
     * @param calories السعرات الحرارية
     * @param protein البروتين
     * @param carbs الكربوهيدرات
     * @param fat الدهون
     */
    @Ignore
    public UserFood(String foodName, int calories, double protein, double carbs, double fat) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    // ======================= Getter & Setter =======================
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

    // ======================= Parcelable =======================

    /**
     * Constructor لإنشاء كائن UserFood من Parcel
     * @param in Parcel يحتوي على بيانات UserFood
     */
    protected UserFood(Parcel in) {
        id = in.readInt();
        foodName = in.readString();
        calories = in.readInt();
        protein = in.readDouble();
        carbs = in.readDouble();
        fat = in.readDouble();
        date = in.readString();
    }

    /**
     * كتابة بيانات UserFood إلى Parcel
     * @param dest Parcel الوجهة
     * @param flags خيارات خاصة بالكتابة (عادة 0)
     */
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

    /** وصف محتويات Parcelable (عادة 0) */
    @Override
    public int describeContents() { return 0; }

    /**
     * CREATOR لتمرير UserFood بين الأنشطة باستخدام Intent
     */
    public static final Creator<UserFood> CREATOR = new Creator<UserFood>() {
        @Override
        public UserFood createFromParcel(Parcel in) { return new UserFood(in); }
        @Override
        public UserFood[] newArray(int size) { return new UserFood[size]; }
    };
}
