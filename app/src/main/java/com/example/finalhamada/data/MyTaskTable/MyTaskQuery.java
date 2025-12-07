package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO لعمليات جدول المهام MyTask
 * يحتوي على العمليات الأساسية (إضافة - تحديث - حذف - جلب)
 */
@Dao
public interface MyTaskQuery {

    // إدخال مهمة جديدة
    @Insert
    void insert(MyTask task);

    // تحديث مهمة
    @Update
    void update(MyTask task);

    // حذف مهمة
    @Delete
    void delete(MyTask task);

    // جلب جميع المهام
    @Query("SELECT * FROM MyTask")
    List<MyTask> getAllTasks();

    // جلب مهمة حسب الـ ID
    @Query("SELECT * FROM MyTask WHERE id = :id LIMIT 1")
    MyTask getTaskById(int id);

    // حذف جميع المهام
    @Query("DELETE FROM MyTask")
    void deleteAllTasks();

    // عدد المهام
    @Query("SELECT COUNT(*) FROM MyTask")
    int getTaskCount();
}
