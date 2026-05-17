package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * FitTrack Entity
 * ----------------------------------------------
 * يمثل جدول المستخدمين في Room Database.
 * يحتوي على معلومات المستخدم الأساسية:
 * (Email, Name, Username, Password, Age, Height, Weight, Gender)
 */
@Entity(tableName = "users")
public class FitTrack {

    /** معرف المستخدم (Primary Key - auto) */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /** البريد الإلكتروني */
    private String email;

    /** الاسم الكامل */
    private String name;

    /** اسم المستخدم */
    private String username;

    /** كلمة المرور */
    private String password;

    /** العمر */
    private long age;

    /** الطول (cm) */
    private double height;

    /** الوزن (kg) */
    private double weight;

    /** الجنس */
    private String gender;

    /** وقت إنشاء الحساب */
    private long createdAt;

    /** ملاحظات إضافية */
    private String note;

    // =======================
    // Getters & Setters
    // =======================

    /** @return id المستخدم */
    public long getId() { return id; }

    /** @param id تعيين id */
    public void setId(long id) { this.id = id; }

    /** @return البريد الإلكتروني */
    public String getEmail() { return email; }

    /** @param email تعيين البريد */
    public void setEmail(String email) { this.email = email; }

    /** @return الاسم */
    public String getName() { return name; }

    /** @param name تعيين الاسم */
    public void setName(String name) { this.name = name; }

    /** @return اسم المستخدم */
    public String getUsername() { return username; }

    /** @param username تعيين username */
    public void setUsername(String username) { this.username = username; }

    /** @return كلمة المرور */
    public String getPassword() { return password; }

    /** @param password تعيين كلمة المرور */
    public void setPassword(String password) { this.password = password; }

    /** @return العمر */
    public long getAge() { return age; }

    /** @param age تعيين العمر */
    public void setAge(long age) { this.age = age; }

    /** @return الطول */
    public double getHeight() { return height; }

    /** @param height تعيين الطول */
    public void setHeight(double height) { this.height = height; }

    /** @return الوزن */
    public double getWeight() { return weight; }

    /** @param weight تعيين الوزن */
    public void setWeight(double weight) { this.weight = weight; }

    /** @return الجنس */
    public String getGender() { return gender; }

    /** @param gender تعيين الجنس */
    public void setGender(String gender) { this.gender = gender; }

    /** @return وقت إنشاء الحساب */
    public long getCreatedAt() { return createdAt; }

    /** @param createdAt تعيين الوقت */
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    /** @return الملاحظات */
    public String getNote() { return note; }

    /** @param note تعيين ملاحظات */
    public void setNote(String note) { this.note = note; }

    /**
     * تحويل الكائن إلى نص (لـ Debug)
     */
    @Override
    public String toString() {
        return "FitTrack{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }
}