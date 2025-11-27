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
/**
 * Method: insert
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void insert(MyTask user);

    // تحديث بيانات المستخدم
    @Update
/**
 * Method: update
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void update(MyTask user);

    // حذف مستخدم
    @Delete
/**
 * Method: delete
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void delete(MyTask user);

    // جلب كل المستخدمين
    @Query("SELECT * FROM MyUser")
/**
 * Method: getAllUsers
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=[TypeArgument(pattern_type=None, type=ReferenceType(arguments=None, dimensions=[], name=MyTask, sub_type=None))], dimensions=[], name=List, sub_type=None) - description
 */
    List<MyTask> getAllUsers();

    // جلب مستخدم عبر البريد الإلكتروني
    @Query("SELECT * FROM MyUser WHERE email = :email LIMIT 1")
/**
 * Method: getUserByEmail
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param email - description
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=MyTask, sub_type=None) - description
 */
    MyTask getUserByEmail(String email);

    // التحقق من تسجيل الدخول (بريد + كلمة مرور)
    @Query("SELECT * FROM MyUser WHERE email = :email AND password = :password LIMIT 1")
/**
 * Method: login
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param email - description
 * @param password - description
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=MyTask, sub_type=None) - description
 */
    MyTask login(String email, String password);

    // حذف جميع المستخدمين (إعادة ضبط الجدول)
    @Query("DELETE FROM MyTask")
/**
 * Method: deleteAllUsers
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 */
    void deleteAllUsers();

    // حساب عدد المستخدمين
    @Query("SELECT COUNT(*) FROM MyTask")
/**
 * Method: getUserCount
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=int) - description
 */
    int getUserCount();
}
