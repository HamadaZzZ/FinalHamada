package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * واجهة للوصول إلى بيانات FitTrack
 */
@Dao
public interface FitTrackQuery {

    /** إدخال سجل جديد */
    @Insert
    void insertFitTrack(FitTrack fitTrack);

    /** تحديث سجل موجود */
    @Update
    void updateFitTrack(FitTrack fitTrack);

    /** حذف سجل */
    @Delete
    void deleteFitTrack(FitTrack fitTrack);

    /** حذف كل السجلات */
    @Query("DELETE FROM FitTrack")
    void deleteAll();

    /** جلب كل السجلات */
    @Query("SELECT * FROM FitTrack")
    List<FitTrack> getAllFitTracks();

    /** جلب كل السجلات لمستخدم معيّن */
    @Query("SELECT * FROM FitTrack WHERE userId = :userId")
    List<FitTrack> getFitTracksByUserId(long userId);

    /** جلب سجل واحد حسب المعرّف */
    @Query("SELECT * FROM FitTrack WHERE id = :id")
    FitTrack getFitTrackById(long id);
}
