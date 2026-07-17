package com.example.rombe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RankingDao {
    @Insert
    void insert(Ranking ranking);

    // Obtener todos los registros ordenados por puntaje descendente
    @Query("SELECT * FROM ranking ORDER BY puntaje DESC")
    List<Ranking> getAllOrderedByScore();

    // Si quieres también obtener todos sin ordenar
    @Query("SELECT * FROM ranking")
    List<Ranking> getAll();
}
