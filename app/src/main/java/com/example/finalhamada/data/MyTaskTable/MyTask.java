package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * فئة تمثل المستخدم في التطبيق
 * تحتوي على جميع صفات المستخدم، وتُستخدم كجدول داخل قاعدة البيانات
 */
@Entity
public class MyTask {

    @PrimaryKey(autoGenerate = true)
    /** رقم المستخدم (يتولّد تلقائياً) */
    private int id;

    /** الاسم الكامل للمستخدم */
    private String fullName;

    /** البريد الإلكتروني */
    private String email;

    /** كلمة المرور */
    private String password;

    /** رقم الهاتف */
    private String phone;

    /** الجنس (ذكر / أنثى) */
    private String gender;

    /** العمر */
    private int age;

    /** الطول بالسنتيمتر */
    private float height;

    /** الوزن الحالي بالكيلوغرام */
    private float weight;

    /** الوزن المستهدف */
    private float goalWeight;

    /** نوع الهدف (خسارة وزن / بناء عضلات / الحفاظ على الوزن) */
    private String goalType;

    /** مستوى النشاط (منخفض / متوسط / مرتفع) */
    private String activityLevel;

    /** عدد السعرات المستهدفة يومياً */
    private int dailyCaloriesTarget;

    /** رابط أو مسار صورة المستخدم */
    private String profileImage;

    /** تاريخ إنشاء الحساب */
    private String createdAt;

    /** آخر تسجيل دخول */
    private String lastLogin;

    /** هل التذكيرات مفعّلة */
    private boolean notificationsEnabled;

     public MyTask()
     {}



    // ===============================
    //       Getters & Setters
    // ===============================

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


    // ===============================
    //             toString
    // ===============================

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", goalWeight=" + goalWeight +
                ", goalType='" + goalType + '\'' +
                ", activityLevel='" + activityLevel + '\'' +
                ", dailyCaloriesTarget=" + dailyCaloriesTarget +
                ", profileImage='" + profileImage + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", notificationsEnabled=" + notificationsEnabled +
                '}';
    }
}
