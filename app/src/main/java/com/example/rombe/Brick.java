package com.example.rombe;

public class Brick {
    public String color;
    public int points;
    public int resistance;
    public boolean isIndestructible;
    public int initialResistance;

    public Brick(String color, int points, int resistance) {
        this(color, points, resistance, false);
    }

    public Brick(String color, int points, int resistance, boolean isIndestructible) {
        this.color = color;
        this.points = points;
        this.resistance = resistance;
        this.isIndestructible = isIndestructible;
        this.initialResistance = resistance;
    }
}
