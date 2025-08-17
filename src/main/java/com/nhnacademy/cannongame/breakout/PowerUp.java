package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;

import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PowerUp extends Ball {
    // 필드
    private PowerUpType type;
    private boolean collected;

    // 상수
    private static final double POWERUP_RADIUS = 20.0;
    private static final double FALL_SPEED = 100.0; // 100 pixels/s

    public PowerUp(double x, double y, PowerUpType type) {
        // Ball 클래스의 생성자를 호출하여 초기화
        super(x, y, POWERUP_RADIUS, Color.YELLOW, CollisionAction.PASS);
        this.type = type;
        this.collected = false;

        // 파워업은 수직으로만 떨어지므로 dx는 0, dy는 FALL_SPEED로 설정
        this.setDx(0);
        this.setDy(FALL_SPEED);
    }

//    @Override
//    public void paint(GraphicsContext gc) {
//        if (collected) return;
//
//        // 원형 배경 그리기
//        gc.setFill(Color.YELLOW);
//        gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
//
//        // 테두리
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(1);
//        gc.strokeOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
//
//        // 심볼 텍스트 중앙 정렬
//        gc.setFill(Color.BLACK);
//        gc.setFont(new Font("Arial", getRadius()));
//        String symbol = type.getSymbol();
//        gc.fillText(symbol, getX() - getRadius() / 2, getY() + getRadius() / 2);
//    }

    @Override
    public void move(double deltaTime) {
        if (!collected) {
            super.move(deltaTime); // Ball의 move() 메소드를 호출하여 y좌표만 업데이트
        }
    }

    @Override
    public void handleCollision(Collidable other) {
        // 패들과 충돌했을 때만 수집 상태로 변경
        if (other instanceof BreakoutPaddle) {
            this.collected = true;
            // 여기서 파워업 효과를 적용하는 로직을 호출할 수 있습니다.
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public PowerUpType getType() {
        return type;
    }
}