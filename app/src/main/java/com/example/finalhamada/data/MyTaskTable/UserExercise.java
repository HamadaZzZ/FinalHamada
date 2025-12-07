package com.example.finalhamada.data.MyTaskTable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_exercises")
public class UserExercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String category;
    private int reps;
    private int sets;
    private int weight;
    private int duration;
    private int calories;
    private String note;
    private int imageRes;

    // ====== Constructor ======
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

    // ====== toString() ======
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
