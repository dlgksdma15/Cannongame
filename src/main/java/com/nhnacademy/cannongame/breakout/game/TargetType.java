package com.nhnacademy.cannongame.breakout.game;

import javafx.scene.paint.Color;

public enum TargetType {
    WOODEN(50, Color.BROWN, 1.0),
    STONE(100, Color.GRAY, 2.0),
    METAL(200, Color.SILVER, 3.0),
    GLASS(75, Color.LIGHTBLUE, 0.5),
    TNT(300, Color.RED, 1.0);

    private final int points;
    private final Color color;
    private final double durability;

    TargetType(int points, Color color, double durability) {
        this.points = points;
        this.color = color;
        this.durability = durability;
    }

    public int getPoints() { return points; }
    public Color getColor() { return color; }
    public double getDurability() { return durability; }
}
