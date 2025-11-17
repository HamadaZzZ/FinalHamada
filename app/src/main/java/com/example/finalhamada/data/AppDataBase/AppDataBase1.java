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
        version = 1,
        exportSchema = false
)
public abstract class AppDataBase1 extends RoomDatabase {

    // روابط الوصول إلى واجهات DAO
    public abstract MyUserQuery myUserQuery();
    public abstract MyTaskQuery myTaskQuery();
    public abstract FitTrackQuery fitTrackQuery();

    // كائن واحد من قاعدة البيانات (Singleton)
    private static volatile AppDataBase1 INSTANCE;

    /**
     * استدعاء قاعدة البيانات
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
