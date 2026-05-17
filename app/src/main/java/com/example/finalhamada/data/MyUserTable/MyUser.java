package com.example.finalhamada.data.MyUserTable; // مكان الكلاس داخل المشروع

import androidx.room.Entity; // يحول الكلاس إلى جدول داخل Room Database
import androidx.room.PrimaryKey; // يحدد المفتاح الأساسي داخل الجدول

/**
 * ============================================================
 * MyUser
 * ============================================================
 *
 * هذا الكلاس يمثل المستخدم
 * داخل التطبيق.
 *
 * استخدمته لتخزين جميع بيانات المستخدم
 * داخل Room Database.
 *
 * يحتوي على:
 * - المعلومات الشخصية
 * - معلومات الوزن والطول
 * - الهدف الرياضي
 * - النشاط اليومي
 * - صورة البروفايل
 * - الإشعارات
 *
 * أهمية هذا الكلاس:
 * يعتبر Entity داخل قاعدة البيانات،
 * وكل Object من MyUser
 * يمثل صف واحد داخل جدول المستخدمين.
 */
@Entity
public class MyUser {

    /**
     * ============================================================
     * id
     * ============================================================
     *
     * يمثل المعرف الفريد للمستخدم.
     *
     * PrimaryKey:
     * يعني أن هذا الحقل
     * هو المفتاح الأساسي.
     *
     * autoGenerate = true:
     * يعني أن Room
     * ينشئ id تلقائيًا.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * ============================================================
     * fullName
     * ============================================================
     *
     * يخزن الاسم الكامل للمستخدم.
     */
    private String fullName;

    /**
     * ============================================================
     * email
     * ============================================================
     *
     * يخزن البريد الإلكتروني للمستخدم.
     */
    private String email;

    /**
     * ============================================================
     * password
     * ============================================================
     *
     * يخزن كلمة المرور الخاصة بالمستخدم.
     */
    private String password;

    /**
     * ============================================================
     * phone
     * ============================================================
     *
     * يخزن رقم الهاتف الخاص بالمستخدم.
     */
    private String phone;

    /**
     * ============================================================
     * gender
     * ============================================================
     *
     * يخزن جنس المستخدم.
     */
    private String gender;

    /**
     * ============================================================
     * age
     * ============================================================
     *
     * يخزن عمر المستخدم.
     */
    private int age;

    /**
     * ============================================================
     * height
     * ============================================================
     *
     * يخزن طول المستخدم.
     */
    private float height;

    /**
     * ============================================================
     * weight
     * ============================================================
     *
     * يخزن وزن المستخدم الحالي.
     */
    private float weight;

    /**
     * ============================================================
     * goalWeight
     * ============================================================
     *
     * يخزن الوزن الهدف
     * الذي يريد المستخدم الوصول إليه.
     */
    private float goalWeight;

    /**
     * ============================================================
     * goalType
     * ============================================================
     *
     * يخزن نوع الهدف الرياضي.
     *
     * أمثلة:
     * - Lose Weight
     * - Gain Muscle
     */
    private String goalType;

    /**
     * ============================================================
     * activityLevel
     * ============================================================
     *
     * يخزن مستوى نشاط المستخدم.
     *
     * أمثلة:
     * - Beginner
     * - Intermediate
     * - Advanced
     */
    private String activityLevel;

    /**
     * ============================================================
     * dailyCaloriesTarget
     * ============================================================
     *
     * يخزن عدد السعرات اليومية
     * المستهدفة للمستخدم.
     */
    private int dailyCaloriesTarget;

    /**
     * ============================================================
     * profileImage
     * ============================================================
     *
     * يخزن صورة البروفايل
     * الخاصة بالمستخدم.
     *
     * غالبًا تكون Base64 أو رابط صورة.
     */
    private String profileImage;

    /**
     * ============================================================
     * createdAt
     * ============================================================
     *
     * يخزن تاريخ إنشاء الحساب.
     */
    private String createdAt;

    /**
     * ============================================================
     * lastLogin
     * ============================================================
     *
     * يخزن آخر وقت
     * قام المستخدم بتسجيل الدخول فيه.
     */
    private String lastLogin;

    /**
     * ============================================================
     * notificationsEnabled
     * ============================================================
     *
     * يحدد إذا كانت الإشعارات
     * مفعلة أو لا.
     *
     * true:
     * الإشعارات مفعلة.
     *
     * false:
     * الإشعارات متوقفة.
     */
    private boolean notificationsEnabled;

    // ============================================================
    // Getters & Setters
    // ============================================================

    /**
     * ============================================================
     * getId
     * ============================================================
     *
     * ترجع id الخاص بالمستخدم.
     *
     * @return id المستخدم
     */
    public int getId() {
        return id;
    }

    /**
     * ============================================================
     * setId
     * ============================================================
     *
     * تستخدم لتعديل id المستخدم.
     *
     * @param id القيمة الجديدة
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ترجع الاسم الكامل للمستخدم.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * تستخدم لتعديل الاسم الكامل.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * ترجع البريد الإلكتروني.
     */
    public String getEmail() {
        return email;
    }

    /**
     * تستخدم لتعديل البريد الإلكتروني.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * ترجع كلمة المرور.
     */
    public String getPassword() {
        return password;
    }

    /**
     * تستخدم لتعديل كلمة المرور.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ترجع رقم الهاتف.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * تستخدم لتعديل رقم الهاتف.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * ترجع جنس المستخدم.
     */
    public String getGender() {
        return gender;
    }

    /**
     * تستخدم لتعديل الجنس.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * ترجع عمر المستخدم.
     */
    public int getAge() {
        return age;
    }

    /**
     * تستخدم لتعديل العمر.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * ترجع طول المستخدم.
     */
    public float getHeight() {
        return height;
    }

    /**
     * تستخدم لتعديل الطول.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * ترجع وزن المستخدم.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * تستخدم لتعديل الوزن.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * ترجع الوزن الهدف.
     */
    public float getGoalWeight() {
        return goalWeight;
    }

    /**
     * تستخدم لتعديل الوزن الهدف.
     */
    public void setGoalWeight(float goalWeight) {
        this.goalWeight = goalWeight;
    }

    /**
     * ترجع نوع الهدف الرياضي.
     */
    public String getGoalType() {
        return goalType;
    }

    /**
     * تستخدم لتعديل نوع الهدف.
     */
    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    /**
     * ترجع مستوى النشاط.
     */
    public String getActivityLevel() {
        return activityLevel;
    }

    /**
     * تستخدم لتعديل مستوى النشاط.
     */
    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * ترجع عدد السعرات اليومية المستهدفة.
     */
    public int getDailyCaloriesTarget() {
        return dailyCaloriesTarget;
    }

    /**
     * تستخدم لتعديل السعرات اليومية.
     */
    public void setDailyCaloriesTarget(int dailyCaloriesTarget) {
        this.dailyCaloriesTarget = dailyCaloriesTarget;
    }

    /**
     * ترجع صورة البروفايل.
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * تستخدم لتعديل صورة البروفايل.
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * ترجع تاريخ إنشاء الحساب.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * تستخدم لتعديل تاريخ إنشاء الحساب.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * ترجع آخر تسجيل دخول.
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * تستخدم لتعديل آخر تسجيل دخول.
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * تتحقق إذا كانت الإشعارات مفعلة.
     *
     * @return true إذا كانت الإشعارات تعمل
     */
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    /**
     * تستخدم لتفعيل أو إيقاف الإشعارات.
     *
     * @param notificationsEnabled القيمة الجديدة
     */
    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}