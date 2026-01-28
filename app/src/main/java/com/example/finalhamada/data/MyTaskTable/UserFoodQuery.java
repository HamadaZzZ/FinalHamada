package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserFoodQuery {

    @Insert
    void insert(UserFood userFood);

    @Query("SELECT * FROM user_food_table ORDER BY id DESC")
    List<UserFood> getAll();

    @Query("DELETE FROM user_food_table WHERE id = :foodId")
    void deleteFoodById(int foodId);
}
