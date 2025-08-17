package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import javafx.scene.paint.Color;

public class PowerUpBrick extends SimpleBrick implements PowerUpProvider {

    public PowerUpBrick(double x, double y, double width, double height) {
        super(x, y, width, height, BrickType.SIMPLE);
        super.setColor(Color.PURPLE);
    }

    @Override
    public void handleCollision(Collidable other) {
        // 충돌 시 SimpleBrick의 로직을 그대로 사용 (파괴)
        super.handleCollision(other);
    }

    @Override
    public void hit() {
        super.hit();
        // 파괴될 때 파워업을 드롭
        if (isDestroyed()) {
            // dropPowerUp 로직을 여기에 구현
        }
    }

    @Override
    public PowerUp dropPowerUp() {
        // 파워업 아이템을 생성하여 반환하는 로직
        // 예: return new SpeedUpPowerUp(getX(), getY());
        return null;
    }
}