package com.nhnacademy.cannongame.breakout;

import javafx.scene.paint.Color;

public enum ProjectileType {
    NORMAL(Color.BLACK, 1.0, 50),
    HEAVY(Color.GRAY, 2.0, 70),
    EXPLOSIVE(Color.RED, 1.0, 100),
    SCATTER(Color.BLUE, 0.8, 30),
    BOUNCY(Color.GREEN, 1.0, 40);

    private final Color color;
    private final double massMultiplier;
    private final double explosionRadius;

    ProjectileType(Color color, double massMultiplier, double explosionRadius) {
        this.color = color;
        this.massMultiplier = massMultiplier;
        this.explosionRadius = explosionRadius;
    }

    public Color getColor() { return color; }
    public double getMassMultiplier() { return massMultiplier; }
    public double getExplosionRadius() { return explosionRadius; }
}