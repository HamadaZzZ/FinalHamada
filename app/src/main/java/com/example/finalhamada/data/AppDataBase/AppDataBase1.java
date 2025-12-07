package com.example.finalhamada.data.AppDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalhamada.data.MyUserTable.MyUser;
import com.example.finalhamada.data.MyUserTable.MyUserQuery;
import com.example.finalhamada.data.MyTaskTable.UserExercise;
import com.example.finalhamada.data.MyTaskTable.UserExerciseQuery;
import com.example.finalhamada.data.MyFitTrackTable.FitTrack;
import com.example.finalhamada.data.MyFitTrackTable.FitTrackQuery;

/**
 * قاعدة البيانات الرئيسية للتطبيق
 * تحتوي على جميع الجداول (Entities) وجميع الـ DAO interfaces
 */
@Database(
        entities = {
                MyUser.class,
                UserExercise.class,  // تم التغيير من MyTask إلى UserExercise
                FitTrack.class
        },
        version = 3, // زودنا النسخة لأنها تغيرت البنية
        exportSchema = false
)
public abstract class AppDataBase1 extends RoomDatabase {

    // روابط الوصول إلى واجهات DAO
    public abstract MyUserQuery myUserQuery();
    public abstract UserExerciseQuery userExerciseQuery();  // تم التغيير
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
                            .fallbackToDestructiveMigration() // إعادة البناء عند تغيير البنية
                            .allowMainThreadQueries() // للسماح بالاستعلام في الـ Main Thread
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
