package com.example.finalhamada.data.MyUserTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Dao = Data Access Object
@Dao
public interface MyUserQuery {

    // ✅ إدخال مستخدم جديد
    @Insert
    void insertUser(MyUser user);

    // ✅ تحديث بيانات المستخدم
    @Update
    void updateUser(MyUser user);

    // ✅ حذف مستخدم
    @Delete
    void deleteUser(MyUser user);

    // ✅ جلب جميع المستخدمين من الجدول
    @Query("SELECT * FROM MyUser")
    List<MyUser> getAllUsers();

    // ✅ البحث عن مستخدم بالإيميل (مفيد لتسجيل الدخول)
    @Query("SELECT * FROM MyUser WHERE email = :email LIMIT 1")
    MyUser getUserByEmail(String email);

    // ✅ التحقق من تسجيل الدخول (إيميل + كلمة مرور)
    @Query("SELECT * FROM MyUser WHERE email = :email AND password = :password LIMIT 1")
    MyUser login(String email, String password);

    // ✅ حذف جميع المستخدمين (اختياري)
    @Query("DELETE FROM MyUser")
    void deleteAllUsers();
}
