package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * واجهة للتعامل مع جدول المستخدمين في قاعدة البيانات
 * تحتوي على العمليات الأساسية (إضافة، تحديث، حذف، جلب)
 */
@Dao
public interface MyTaskQuery {

    // إدخال مستخدم جديد
    @Insert
    void insert(MyTask user);

    // تحديث بيانات المستخدم
    @Update
    void update(MyTask user);

    // حذف مستخدم
    @Delete
    void delete(MyTask user);

    // جلب كل المستخدمين
    @Query("SELECT * FROM MyUser")
    List<MyTask> getAllUsers();

    // جلب مستخدم عبر البريد الإلكتروني
    @Query("SELECT * FROM MyUser WHERE email = :email LIMIT 1")
    MyTask getUserByEmail(String email);

    // التحقق من تسجيل الدخول (بريد + كلمة مرور)
    @Query("SELECT * FROM MyUser WHERE email = :email AND password = :password LIMIT 1")
    MyTask login(String email, String password);

    // حذف جميع المستخدمين (إعادة ضبط الجدول)
    @Query("DELETE FROM MyTask")
    void deleteAllUsers();

    // حساب عدد المستخدمين
    @Query("SELECT COUNT(*) FROM MyTask")
    int getUserCount();
}
