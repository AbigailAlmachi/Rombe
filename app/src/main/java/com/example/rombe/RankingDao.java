package com.example.rombe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RankingDao {

    // Consulta todo el ranking ordenado por puntaje descendente
    @Query("SELECT * FROM user ORDER BY puntaje DESC")
    LiveData<List<User>> getAllRanking();

    // Inserta una lista de usuarios en la tabla user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> usuarios);

    // (Opcional) Inserta un solo usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User usuario);

    // (Opcional) Borra todos los registros del ranking
    @Query("DELETE FROM user")
    void clearRanking();
}
