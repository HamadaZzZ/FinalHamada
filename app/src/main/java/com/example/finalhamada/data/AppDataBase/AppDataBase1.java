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
 * قاعدة البيانات الرئيسية للتطبيق باستخدام Room
 * تجمع كل الـ Entities وتوفر الوصول إلى الـ DAO
 */
@Database(
        entities = {
                MyUser.class,
                UserExercise.class,
                FitTrack.class
        },
        version = 3,
        exportSchema = false
)
public abstract class AppDataBase1 extends RoomDatabase {

    /** DAO للتعامل مع جدول المستخدم */
    public abstract MyUserQuery myUserQuery();

    /** DAO للتعامل مع جدول التمارين */
    public abstract UserExerciseQuery userExerciseQuery();

    /** DAO للتعامل مع جدول تتبع اللياقة */
    public abstract FitTrackQuery fitTrackQuery();

    /** نسخة واحدة من قاعدة البيانات (Singleton) */
    private static volatile AppDataBase1 INSTANCE;

    /**
     * إنشاء أو إرجاع نسخة قاعدة البيانات
     *
     * @param context سياق التطبيق
     * @return كائن AppDataBase1
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
                            /** يعيد بناء القاعدة عند تغيير البنية */
                            .fallbackToDestructiveMigration()

                            /** يسمح بالاستعلام على الـ Main Thread */
                            .allowMainThreadQueries()

                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
