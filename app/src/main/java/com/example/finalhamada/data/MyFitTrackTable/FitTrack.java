package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * فئة FitTrack / MyUser
 * ----------------------------------------------
 * تمثل بيانات المستخدم الأساسية داخل التطبيق.
 * تشمل:
 * - بيانات تسجيل الدخول (Email, Password)
 * - معلومات شخصية (Name, Username, Age, Height, Weight, Gender)
 * - أي ملاحظات إضافية
 * يمكن تخزينها في قاعدة بيانات محلية باستخدام Room.
 */
@Entity(tableName = "users")
public class FitTrack {

    /** رقم المستخدم (Primary Key) */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /** البريد الإلكتروني للمستخدم */
    private String email;

    /** الاسم الكامل */
    private String name;

    /** اسم المستخدم */
    private String username;

    /** كلمة المرور (يمكن تشفيرها لاحقًا) */
    private String password;

    /** العمر */
    private long age;

    /** الطول (سم) */
    private double height;

    /** الوزن (كغ) */
    private double weight;

    /** الجنس (Male / Female / Other) */
    private String gender;

    /** تاريخ إنشاء الحساب */
    private long createdAt;

    /** ملاحظات إضافية */
    private String note;

    // =======================
    // Getters & Setters
    // =======================

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public long getAge() { return age; }
    public void setAge(long age) { this.age = age; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "FitTrack{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", createdAt=" + createdAt +
                ", note='" + note + '\'' +
                '}';
    }
}
