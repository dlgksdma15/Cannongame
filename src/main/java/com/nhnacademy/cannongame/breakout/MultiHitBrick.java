package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.paint.Color;

public class MultiHitBrick extends SimpleBrick {
    private int hitPoints;
    private final int maxHitPoints;

    // 생성자를 BrickType을 받도록 수정
    public MultiHitBrick(double x, double y, double width, double height, BrickType type) {
        // 부모 생성자를 호출하여 points 필드를 초기화
        super(x, y, width, height, type);

        // MultiHitBrick만의 고유한 속성 초기화
        this.maxHitPoints = type.getMaxHitPoints();
        this.hitPoints = this.maxHitPoints;
    }

    @Override
    public void hit() {
        if (hitPoints > 0) {
            this.hitPoints--;
            updateColor(); // hitPoints가 남아 있으면 색상 업데이트
        }
    }

    // 남은 체력에 따라 색상을 업데이트하는 메서드
    private void updateColor() {
        double ratio = (double) hitPoints / maxHitPoints;
        // 체력 비율에 따라 색상 밝기 조절
        Color newColor = Color.YELLOW.interpolate(Color.BLACK, 1.0 - ratio);
        this.setColor(newColor);
    }

    @Override
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

}