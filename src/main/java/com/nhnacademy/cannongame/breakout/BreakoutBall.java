package com.nhnacademy.cannongame.breakout;


import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BreakoutBall extends Ball {
    // 필드
    private boolean isLost = false;

    // 생성자: Ball 클래스 생성자를 호출하여 초기화
    public BreakoutBall(double x, double y, double radius, Color color, CollisionAction collisionAction) {
        super(x, y, radius, color, collisionAction);
    }

    public BreakoutBall(double x, double y, double radius) {
        super(x, y, radius);
    }

    // handleCollision 메서드를 오버라이드하여 패들과의 충돌 로직을 추가
    @Override
    public void handleCollision(Collidable other) {
        // BreakoutPaddle과의 충돌 처리
        if (other instanceof BreakoutPaddle) {
            BreakoutPaddle paddle = (BreakoutPaddle) other;
            paddle.calculateBounce(this); // 패들의 반사 각도 계산 로직을 호출
            return; // 패들과 충돌 시 일반적인 Ball 충돌 로직을 건너뜁니다.
        }

        // 그 외의 경우, 부모 클래스의 충돌 로직을 사용
        super.handleCollision(other);
    }

    // 공이 화면 아래로 떨어졌는지 확인하는 메서드
    public boolean isLost(double worldHeight) {
        return getY() - getRadius() > worldHeight;
    }

    // 공의 상태를 초기화하는 메서드
    public void reset(double startX, double startY, double initialSpeed) {
        setX(startX);
        setY(startY);
        setDx(0);
        setDy(initialSpeed);
        this.isLost = false;
    }
}