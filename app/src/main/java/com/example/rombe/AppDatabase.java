package com.example.rombe;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Incluye todas las entidades que usarás en Room
@Database(entities = {User.class, Ranking.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    // DAOs disponibles
    public abstract UserDao userDao();
    public abstract RankingDao rankingDao();

    // Singleton para obtener la instancia de la base de datos
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "rombe_db"
                            )
                            .fallbackToDestructiveMigration() // elimina datos si cambia versión
                            .build();
                }
            }
        }
        return instance;
    }
}
