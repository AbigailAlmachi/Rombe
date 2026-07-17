package com.example.rombe;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ranking")
public class Ranking {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public int puntaje;

    public Ranking(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
