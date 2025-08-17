package com.nhnacademy.cannongame.breakout;


import com.nhnacademy.cannongame.Vector2D;

public class Gravity implements Force {
    private final double gravity; // 중력가속도 (g)
    private final Vector2D direction;

    public Gravity(double gravity) {
        this.gravity = gravity;
        // 중력 방향은 아래쪽 (y축 양의 방향)
        this.direction = new Vector2D(0, 1);
    }

    // F = m * g 공식을 사용하여 중력 힘을 계산하고 적용
    @Override
    public void apply(PhysicsObject obj, double deltaTime) {
        double mass = obj.getMass();
        // F = m * g
        Vector2D gravityForce = new Vector2D(
                direction.getX() * mass * gravity,
                direction.getY() * mass * gravity
        );
        obj.applyForce(gravityForce);
    }
}