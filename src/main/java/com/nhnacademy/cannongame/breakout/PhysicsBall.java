package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.paint.Color;

public class PhysicsBall extends Ball implements PhysicsObject {
    // 추가 필드
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;
    private final double restitution; // 반발 계수 (0~1)

    // 상수
    private static final double DENSITY = 1000.0; // 밀도 (kg/m^3)

    public PhysicsBall(double x, double y, double radius, Color color, CollisionAction action, double restitution) {
        super(x, y, radius, color, action);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        // 질량 = 밀도 * 부피 (2D에서는 면적)
        this.mass = DENSITY * Math.PI * radius * radius;
        this.restitution = restitution;
    }

    // PhysicsObject 인터페이스 구현
    @Override
    public Vector2D getPosition() {
        return new Vector2D(getX(), getY());
    }

    @Override
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public void applyForce(Vector2D force) {
        // F = ma -> a = F/m
        Vector2D a = new Vector2D(force.getX() / mass, force.getY() / mass);
        acceleration.add(a); // 누적된 가속도
    }

    @Override
    public void update(double deltaTime) {
        // 1. 속도 업데이트: v = v + a * Δt
        velocity.add(new Vector2D(acceleration.getX() * deltaTime, acceleration.getY() * deltaTime));

        // 2. 위치 업데이트: p = p + v * Δt
        setX(getX() + velocity.getX() * deltaTime);
        setY(getY() + velocity.getY() * deltaTime);

        // 3. 가속도 초기화 (매 프레임마다 새 힘을 계산하기 위함)
        acceleration.setX(0);
        acceleration.setY(0);
    }

    // 충돌 처리 (오버라이드)
    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof BreakoutPaddle) {
            super.handleCollision(other);
        } else if (other.getCollisionAction() == CollisionAction.BOUNCE) {
            // 바닥이나 벽에 충돌 시 반발 계수 적용
            if (this.isColliding(other)) {
                // v' = -v * e
                Vector2D newVelocity = new Vector2D(velocity.getX(), -velocity.getY() * restitution);
                // 속도가 매우 작으면 0으로 설정하여 튀지 않도록 함
                if (Math.abs(newVelocity.getY()) < 1.0) {
                    newVelocity.setY(0);
                }
                setVelocity(newVelocity);
            }
        } else {
            // 다른 충돌 액션은 부모 클래스에 위임
            super.handleCollision(other);
        }
    }
    public void setMass(double mass){
        this.mass = mass;
    }
}