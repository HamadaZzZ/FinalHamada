package com.example.finalhamada.data.MyTaskTable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * UserExercise:
 * ----------------------------------------------
 * يمثل تمرين للمستخدم يتم حفظه في قاعدة البيانات.
 * يحتوي على جميع بيانات التمرين مثل:
 * - الاسم
 * - الفئة
 * - عدد التكرارات والمجموعات
 * - الوزن المستخدم
 * - مدة التمرين
 * - السعرات المحروقة
 * - ملاحظات إضافية
 * - الصورة الممثلة للتمرين
 */
@Entity(tableName = "user_exercises")
public class UserExercise {

    /** معرف فريد لكل تمرين */
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;        // اسم التمرين
    private String category;    // فئة التمرين (Cardio, Strength, ...)
    private int reps;           // عدد التكرارات
    private int sets;           // عدد المجموعات
    private int weight;         // الوزن المستخدم
    private int duration;       // مدة التمرين بالدقائق
    private int calories;       // السعرات الحرارية المحروقة
    private String note;        // ملاحظات إضافية
    private int imageRes;       // صورة التمرين (Resource ID)

    /**
     * Constructor لإنشاء تمرين جديد
     * @param name اسم التمرين
     * @param category فئة التمرين
     * @param reps عدد التكرارات
     * @param sets عدد المجموعات
     * @param weight الوزن المستخدم
     * @param duration مدة التمرين
     * @param calories السعرات المحروقة
     * @param note ملاحظات إضافية
     * @param imageRes صورة التمرين
     */
    public UserExercise(String name, String category, int reps, int sets, int weight,
                        int duration, int calories, String note, int imageRes) {
        this.name = name;
        this.category = category;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.duration = duration;
        this.calories = calories;
        this.note = note;
        this.imageRes = imageRes;
    }

    // ====== Getters & Setters ======

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getImageRes() { return imageRes; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }

    /**
     * toString():
     * ----------------------------------------------
     * EN: Returns a string representation of the exercise.
     * AR: يعيد تمثيل النصي للتمرين لكل الاستخدامات مثل الطباعة أو السجلات.
     */
    @NonNull
    @Override
    public String toString() {
        return "UserExercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", reps=" + reps +
                ", sets=" + sets +
                ", weight=" + weight +
                ", duration=" + duration +
                ", calories=" + calories +
                ", note='" + note + '\'' +
                ", imageRes=" + imageRes +
                '}';
    }
}
