package com.example.rombe;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Ranking.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract RankingDao rankingDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "rombe_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // ⚠️ para pruebas, mejor usar en hilo aparte
                    .build();
        }
        return instance;
    }
}
