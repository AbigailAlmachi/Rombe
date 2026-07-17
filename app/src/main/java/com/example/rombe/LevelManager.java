package com.example.rombe;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    public static List<Brick> getLevel(int level) {
        List<Brick> bricks = new ArrayList<>();

        switch (level) {
            case 1:
                // Nivel 1: sencillo y colorido
                bricks.add(new Brick("Azul", 1, 10));
                bricks.add(new Brick("Verde", 2, 20));
                bricks.add(new Brick("Rojo", 3, 30));
                bricks.add(new Brick("Amarillo", 1, 50)); // especial
                break;

            case 2:
                // Nivel 2: más filas y ladrillos resistentes
                bricks.add(new Brick("Azul", 1, 10));
                bricks.add(new Brick("Verde", 2, 20));
                bricks.add(new Brick("Rojo", 3, 30));
                bricks.add(new Brick("Gris", 4, 40)); // muy resistente
                break;

            case 3:
                // Nivel 3: introduce ladrillos especiales
                bricks.add(new Brick("Azul", 1, 10));
                bricks.add(new Brick("Rojo", 3, 30));
                bricks.add(new Brick("Amarillo", 1, 50)); // puntos extra
                bricks.add(new Brick("Verde", 2, 20));
                bricks.add(new Brick("Morado", 2, 40)); // especial: vida extra
                break;

            case 4:
                // Nivel 4: patrón más complejo con huecos
                bricks.add(new Brick("Rojo", 3, 30));
                bricks.add(new Brick("Gris", 4, 40));
                bricks.add(new Brick("Azul", 1, 10));
                bricks.add(new Brick("Verde", 2, 20));
                bricks.add(new Brick("Negro", 5, 60)); // súper resistente
                break;

            case 5:
                // Nivel 5: mezcla de todos los tipos
                bricks.add(new Brick("Azul", 1, 10));
                bricks.add(new Brick("Verde", 2, 20));
                bricks.add(new Brick("Rojo", 3, 30));
                bricks.add(new Brick("Amarillo", 1, 50));
                bricks.add(new Brick("Gris", 4, 40));
                bricks.add(new Brick("Negro", 5, 60));
                bricks.add(new Brick("Morado", 2, 40)); // vida extra
                break;

            default:
                // Si no existe el nivel, devuelve vacío
                break;
        }

        return bricks;
    }
}
