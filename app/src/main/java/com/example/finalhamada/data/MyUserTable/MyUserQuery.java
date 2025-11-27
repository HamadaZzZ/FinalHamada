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
/**
 * Method: insertUser
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void insertUser(MyUser user);

    @Update
/**
 * Method: updateUser
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void updateUser(MyUser user);

    @Delete
/**
 * Method: deleteUser
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param user - description
 */
    void deleteUser(MyUser user);

    @Query("SELECT * FROM MyUser")
/**
 * Method: getAllUsers
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=[TypeArgument(pattern_type=None, type=ReferenceType(arguments=None, dimensions=[], name=MyUser, sub_type=None))], dimensions=[], name=List, sub_type=None) - description
 */
    List<MyUser> getAllUsers();

    @Query("SELECT * FROM MyUser WHERE email = :email LIMIT 1")
/**
 * Method: getUserByEmail
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param email - description
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=MyUser, sub_type=None) - description
 */
    MyUser getUserByEmail(String email);

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
 * @return ReferenceType(arguments=None, dimensions=[], name=MyUser, sub_type=None) - description
 */
    MyUser login(String email, String password);
}

