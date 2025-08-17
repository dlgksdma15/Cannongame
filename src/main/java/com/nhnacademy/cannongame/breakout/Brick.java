package com.nhnacademy.cannongame.breakout;


import javafx.scene.paint.Color;

public class Brick {
    private double x;
    private double y;
    private Color color;
    private BrickType type; // 벽돌 유형
    private boolean isDestroyed; // 파괴 여부
    private double points; // 파괴 시 획득 점수
}
