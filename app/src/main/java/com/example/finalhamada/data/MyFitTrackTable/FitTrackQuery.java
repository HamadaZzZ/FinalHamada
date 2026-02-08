package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

/**
 * FitTrackQuery (DAO)
 * ----------------------------------------------
 * واجهة الوصول لقاعدة البيانات الخاصة بالمستخدمين.
 * تتيح العمليات التالية على جدول users:
 * - إدخال مستخدم جديد
 * - جلب بيانات المستخدمين
 * - تحديث بيانات المستخدم
 * - حذف المستخدم
 * - تحديث الاسم
 */
@Dao
public interface FitTrackQuery {

    @Insert
    void insertUser(FitTrack user);

    @Update
    void updateUser(FitTrack user);

    @Delete
    void deleteUser(FitTrack user);

    @Query("SELECT * FROM users")
    List<FitTrack> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    FitTrack getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    FitTrack getUserByEmailAndPassword(String email, String password);

    // ===========================
    // وظائف خاصة بالاسم (name)
    // ===========================

    /**
     * تحديث اسم مستخدم بواسطة الايميل
     * @param email البريد الإلكتروني للمستخدم
     * @param name الاسم الجديد
     */
    @Query("UPDATE users SET username = :name WHERE email = :email")
    void updateUserNameByEmail(String email, String name);

    /**
     * جلب اسم المستخدم بواسطة الايميل
     * @param email البريد الإلكتروني
     * @return الاسم الحالي للمستخدم
     */
    @Query("SELECT username FROM users WHERE email = :email LIMIT 1")
    String getUserNameByEmail(String email);
}
