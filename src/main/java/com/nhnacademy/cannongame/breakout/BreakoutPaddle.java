package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Box;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.paint.Color;

public class BreakoutPaddle extends Box {
    private double targetX;
    // 패들 추적 속도 조절 변수
    private static final double TRACKING_SPEED = 0.5; // 이 값을 조절하여 속도를 변경

    public BreakoutPaddle(double x, double y, double width, double height, Color color, CollisionAction collisionAction) {
        super(x, y, width, height, color, collisionAction);
        this.targetX = x + width / 2;
    }

    @Override
    public void move(double deltaTime) {
        double center = this.x + this.width / 2;
        double dxDifference = this.targetX - center;

        // 이동 속도를 dxDifference에 비례하게 설정하고 TRACKING_SPEED를 곱해 속도 조절
        this.dx = dxDifference * TRACKING_SPEED;

        // 부모 클래스의 move() 메소드를 호출하여 실제 위치를 업데이트
        super.move(deltaTime);
    }

    public void setTargetX(double mouseX) {
        this.targetX = mouseX;
    }

    public void expand(double factor) {
        this.width *= factor;
    }

    public void shrink(double factor) {
        this.width /= factor;
    }
    // 공 반사 각도 계산 및 설정
    public void calculateBounce(Ball ball) {
        // 공이 패들의 어느 지점에 부딪혔는지 계산 (0.0 ~ 1.0)
        double hitPosition = (ball.getX() - this.x) / this.width;

        // 반사 각도 계산 (-π/3 ~ +π/3, 즉 -60° ~ +60°)
        double angle = (hitPosition - 0.5) * (Math.PI / 3);

        // 계산된 각도에 따라 공의 속도 벡터를 재설정
        double ballSpeed = Math.sqrt(Math.pow(ball.getDx(), 2) + Math.pow(ball.getDy(), 2));
        ball.setDx(ballSpeed * Math.sin(angle));
        ball.setDy(-Math.abs(ballSpeed * Math.cos(angle))); // 항상 위로
    }
}
