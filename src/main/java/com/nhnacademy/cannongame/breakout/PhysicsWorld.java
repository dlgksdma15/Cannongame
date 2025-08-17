package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class PhysicsWorld {
    // 필드
    private final List<Vector2D> globalForces; // 모든 객체에 적용되는 전역 힘
    private final List<PhysicsObject> objects; // 물리 객체 리스트
    private final double airDensity; // 공기 밀도 (1.2 kg/m³)
    private final double width;
    private final double height;

    // 생성자
    public PhysicsWorld(double width, double height) {
        this.width = width;
        this.height = height;
        this.globalForces = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.airDensity = 1.2;
    }

    // 전역 힘 추가
    public void addGlobalForce(Vector2D force) {
        globalForces.add(force);
    }

    // 물리 객체 추가
    public void addObject(PhysicsObject obj) {
        objects.add(obj);
    }

    // 모든 객체 업데이트
    public void update(double deltaTime) {
        for (PhysicsObject obj : objects) {
            // 모든 전역 힘 적용
            for (Vector2D force : globalForces) {
                obj.applyForce(force);
            }
            // 공기 저항 계산 및 적용
            Vector2D airResistance = calculateAirResistance(obj);
            obj.applyForce(airResistance);

            // 객체 물리 업데이트
            obj.update(deltaTime);
        }
    }

    // 공기 저항 계산 (간단한 모델)
    private Vector2D calculateAirResistance(PhysicsObject obj) {
        // 공기 저항 공식: Fd = 0.5 * rho * v^2 * A * Cd
        // 여기서는 간단하게 속도에 비례하는 힘으로 구현
        double resistanceCoefficient = 0.5 * airDensity;
        Vector2D velocity = obj.getVelocity();
        double speed = velocity.magnitude();

        Vector2D resistanceForce = new Vector2D(-velocity.getX(), -velocity.getY());
        resistanceForce.normalize();
        resistanceForce.multiply(resistanceCoefficient * speed * speed);

        return resistanceForce;
    }
}