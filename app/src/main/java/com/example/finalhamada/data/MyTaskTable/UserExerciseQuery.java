package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserExerciseQuery {

    // ====== Insert ======
    @Insert
    long insert(UserExercise userExercise);

    // ====== Update ======
    @Update
    void update(UserExercise userExercise);

    // ====== Delete ======
    @Delete
    void delete(UserExercise userExercise);

    // ====== Get all exercises ======
    @Query("SELECT * FROM user_exercises")
    List<UserExercise> getAllExercises();

    // ====== Get exercise by ID ======
    @Query("SELECT * FROM user_exercises WHERE id = :id")
    UserExercise getExerciseById(int id);

    // ====== Optional: Delete all ======
    @Query("DELETE FROM user_exercises")
    void deleteAllExercises();

    // ====== Get exercises by category ======
    @Query("SELECT * FROM user_exercises WHERE category = :category")
    List<UserExercise> getExercisesByCategory(String category);

}
