package com.example.finalhamada.data.MyUserTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyUserQuery {

    @Insert
    void insertUser(MyUser user);

    @Update
    void updateUser(MyUser user);

    @Delete
    void deleteUser(MyUser user);

    @Query("SELECT * FROM MyUser")
    List<MyUser> getAllUsers();

    @Query("SELECT * FROM MyUser WHERE email = :email LIMIT 1")
    MyUser getUserByEmail(String email);

    @Query("SELECT * FROM MyUser WHERE email = :email AND password = :password LIMIT 1")
    MyUser login(String email, String password);
}

