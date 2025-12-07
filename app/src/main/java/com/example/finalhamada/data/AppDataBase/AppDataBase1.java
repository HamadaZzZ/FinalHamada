package com.example.finalhamada.data.AppDataBase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.finalhamada.data.MyUserTable.MyUser;
import com.example.finalhamada.data.MyUserTable.MyUserQuery;
import com.example.finalhamada.data.MyTaskTable.MyTask;
import com.example.finalhamada.data.MyTaskTable.MyTaskQuery;
import com.example.finalhamada.data.MyFitTrackTable.FitTrack;
import com.example.finalhamada.data.MyFitTrackTable.FitTrackQuery;

/**
 * قاعدة البيانات الرئيسية للتطبيق
 * تحتوي على جميع الجداول (Entities) وجميع الـ DAO interfaces
 */
@Database(
        entities = {
                MyUser.class,
                MyTask.class,
                FitTrack.class
        },
        version = 2,
        exportSchema = false
)
/**
 * Class: AppDataBase1
 * Purpose (EN): Auto-generated documentation for class AppDataBase1.
 * الهدف (AR): توثيق تلقائي للكلاس AppDataBase1.
 * TODO: Add more detailed description about class functionality
 */
public abstract class AppDataBase1 extends RoomDatabase {

    // روابط الوصول إلى واجهات DAO
/**
 * Method: myUserQuery
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=MyUserQuery, sub_type=None) - description
 */
    public abstract MyUserQuery myUserQuery();
/**
 * Method: myTaskQuery
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=MyTaskQuery, sub_type=None) - description
 */
    public abstract MyTaskQuery myTaskQuery();
/**
 * Method: fitTrackQuery
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=FitTrackQuery, sub_type=None) - description
 */
    public abstract FitTrackQuery fitTrackQuery();

    // كائن واحد من قاعدة البيانات (Singleton)
    private static volatile AppDataBase1 INSTANCE;

    /**
     * استدعاء قاعدة البيانات
     */
/**
 * Method: getDatabase
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param context - description
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=AppDataBase1, sub_type=None) - description
 */
    public static AppDataBase1 getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase1.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDataBase1.class,
                                    "FinalHamadaDB"
                            )
                            .fallbackToDestructiveMigration() // لإعادة البناء عند تغيير البنية
                            .allowMainThreadQueries() // (اختياري) للسماح بالاستعلام في الـ Main Thread
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
