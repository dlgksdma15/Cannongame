package com.nhnacademy.cannongame.breakout;

import javafx.scene.paint.Color;

public enum BrickType {
    // 기본 벽돌: 체력 1, 점수 10, 밝은 회색
    SIMPLE(1, 10, Color.LIGHTGRAY),

    // 다중 타격 벽돌: 체력 3, 점수 30, 어두운 회색
    MULTI_HIT(3, 30, Color.DARKGRAY),

    // 깨지지 않는 벽돌: 무한 체력, 점수 0, 검은색
    UNBREAKABLE(Integer.MAX_VALUE, 0, Color.BLACK),

    // 폭발 벽돌: 체력 1, 점수 300, 빨강
    EXPLOSIVE(1, 300, Color.RED),

    // 파워업 벽돌: 체력 1, 점수 150, 보라색
    POWER_UP(1, 150, Color.PURPLE);  // 마지막 상수 뒤에 세미콜론을 추가하여 끝을 알립니다.

    // 필드, 생성자, 메소드...
    private final int maxHitPoints;
    private final int points;
    private final Color color;

    BrickType(int maxHitPoints, int points, Color color) {
        this.maxHitPoints = maxHitPoints;
        this.points = points;
        this.color = color;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }
}