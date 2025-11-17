package com.example.finalhamada.data.MyUserTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// هذا الكلاس يمثل جدول المستخدمين في قاعدة البيانات
@Entity
public class MyUser {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private int age;
    private float height;
    private float weight;
    private float goalWeight;
    private String goalType;
    private String activityLevel;
    private int dailyCaloriesTarget;
    private String profileImage;
    private String createdAt;
    private String lastLogin;
    private boolean notificationsEnabled;

    // ==========================
    //       Getters & Setters
    // ==========================

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getGoalWeight() {
        return goalWeight;
    }
    public void setGoalWeight(float goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getGoalType() {
        return goalType;
    }
    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getActivityLevel() {
        return activityLevel;
    }
    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public int getDailyCaloriesTarget() {
        return dailyCaloriesTarget;
    }
    public void setDailyCaloriesTarget(int dailyCaloriesTarget) {
        this.dailyCaloriesTarget = dailyCaloriesTarget;
    }

    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }
    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
