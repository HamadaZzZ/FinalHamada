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
/**
 * Method: insertFitTrack
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param fitTrack - description
 */
    void insertFitTrack(FitTrack fitTrack);

    /** تحديث سجل موجود */
    @Update
/**
 * Method: updateFitTrack
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param fitTrack - description
 */
    void updateFitTrack(FitTrack fitTrack);

    /** حذف سجل */
    @Delete
/**
 * Method: deleteFitTrack
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param fitTrack - description
 */
    void deleteFitTrack(FitTrack fitTrack);

    /** حذف كل السجلات */
    @Query("DELETE FROM FitTrack")
/**
 * Method: deleteAll
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 */
    void deleteAll();

    /** جلب كل السجلات */
    @Query("SELECT * FROM FitTrack")
/**
 * Method: getAllFitTracks
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=[TypeArgument(pattern_type=None, type=ReferenceType(arguments=None, dimensions=[], name=FitTrack, sub_type=None))], dimensions=[], name=List, sub_type=None) - description
 */
    List<FitTrack> getAllFitTracks();

    /** جلب كل السجلات لمستخدم معيّن */
    @Query("SELECT * FROM FitTrack WHERE userId = :userId")
/**
 * Method: getFitTracksByUserId
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param userId - description
 *
 * @return ReferenceType(arguments=[TypeArgument(pattern_type=None, type=ReferenceType(arguments=None, dimensions=[], name=FitTrack, sub_type=None))], dimensions=[], name=List, sub_type=None) - description
 */
    List<FitTrack> getFitTracksByUserId(long userId);

    /** جلب سجل واحد حسب المعرّف */
    @Query("SELECT * FROM FitTrack WHERE id = :id")
/**
 * Method: getFitTrackById
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param id - description
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=FitTrack, sub_type=None) - description
 */
    FitTrack getFitTrackById(long id);
}
