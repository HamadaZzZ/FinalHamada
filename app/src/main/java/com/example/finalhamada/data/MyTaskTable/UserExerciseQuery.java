package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * UserExerciseQuery:
 * ----------------------------------------------
 * واجهة DAO للتعامل مع جدول تمارين المستخدم (user_exercises) في قاعدة البيانات.
 * توفر الدوال الأساسية للإضافة، التعديل، الحذف، والاستعلام عن التمارين.
 */
@Dao
public interface UserExerciseQuery {

    /**
     * insert(UserExercise userExercise)
     * ----------------------------------------------
     * EN: Inserts a new exercise into the database.
     * AR: يضيف تمرين جديد إلى قاعدة البيانات.
     *
     * @param userExercise التمرين المراد إضافته
     * @return long قيمة ID المولدة تلقائيًا للتمرين
     */
    @Insert
    long insert(UserExercise userExercise);

    /**
     * update(UserExercise userExercise)
     * ----------------------------------------------
     * EN: Updates an existing exercise in the database.
     * AR: يعدل تمرين موجود في قاعدة البيانات.
     *
     * @param userExercise التمرين المراد تحديثه
     */
    @Update
    void update(UserExercise userExercise);

    /**
     * delete(UserExercise userExercise)
     * ----------------------------------------------
     * EN: Deletes an exercise from the database.
     * AR: يحذف تمرين من قاعدة البيانات.
     *
     * @param userExercise التمرين المراد حذفه
     */
    @Delete
    void delete(UserExercise userExercise);

    /**
     * getAllExercises()
     * ----------------------------------------------
     * EN: Retrieves all exercises from the database.
     * AR: يجلب كل التمارين الموجودة في قاعدة البيانات.
     *
     * @return List<UserExercise> قائمة كل التمارين
     */
    @Query("SELECT * FROM user_exercises")
    List<UserExercise> getAllExercises();

    /**
     * getExerciseById(int id)
     * ----------------------------------------------
     * EN: Retrieves a single exercise by its ID.
     * AR: يجلب تمرين واحد باستخدام معرفه.
     *
     * @param id معرف التمرين
     * @return UserExercise التمرين المطلوب
     */
    @Query("SELECT * FROM user_exercises WHERE id = :id")
    UserExercise getExerciseById(int id);

    /**
     * deleteAllExercises()
     * ----------------------------------------------
     * EN: Deletes all exercises from the table.
     * AR: يحذف كل التمارين من الجدول.
     */
    @Query("DELETE FROM user_exercises")
    void deleteAllExercises();

    /**
     * getExercisesByCategory(String category)
     * ----------------------------------------------
     * EN: Retrieves all exercises of a specific category.
     * AR: يجلب كل التمارين الموجودة ضمن فئة معينة.
     *
     * @param category اسم الفئة (مثال: Cardio, Strength)
     * @return List<UserExercise> قائمة التمارين التابعة للفئة
     */
    @Query("SELECT * FROM user_exercises WHERE category = :category")
    List<UserExercise> getExercisesByCategory(String category);

}
