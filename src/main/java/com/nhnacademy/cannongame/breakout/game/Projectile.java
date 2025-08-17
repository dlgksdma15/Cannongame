package com.nhnacademy.cannongame.breakout.game;

import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.breakout.PhysicsBall;
import com.nhnacademy.cannongame.breakout.ProjectileType;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Projectile extends PhysicsBall {
    // 필드
    private final List<Point> trajectory; // 궤적 저장 리스트
    private final ProjectileType type;
    private boolean hasExploded;

    // 생성자
    public Projectile(double x, double y, double dx, double dy, ProjectileType type) {
        // PhysicsBall 생성자를 호출하여 초기화
        super(x, y, 8, type.getColor(), CollisionAction.BOUNCE, 0.8);
        this.type = type;
        this.trajectory = new ArrayList<>();
        this.hasExploded = false;

        // 타입에 따른 질량 설정
        // setMass(type.getMassMultiplier()); // PhysicsBall에 setMass 메서드가 있다면 사용
        this.setMass(getMass() * type.getMassMultiplier());
        this.setVelocity(new Vector2D(dx, dy));
    }

    public Projectile(double x, double y, double dx, double dy) {
        this(x, y, dx, dy, ProjectileType.NORMAL);
    }

    // update 메서드 오버라이딩 (PhysicsBall의 update를 확장)
    @Override
    public void update(double deltaTime) {
        // 현재 위치를 궤적에 추가
        trajectory.add(new Point(getX(), getY()));
        if (trajectory.size() > 100) {
            trajectory.remove(0); // 궤적 크기 제한
        }

        // 부모 클래스의 물리 업데이트 호출
        super.update(deltaTime);

        // 화면 밖 검사 및 제거 로직 (선택 사항)
    }

    // paint 메서드 오버라이딩
    @Override
    public void paint(GraphicsContext gc) {
        // 궤적 그리기 (반투명 선)
        gc.setStroke(Color.rgb(
                (int) (getColor().getRed() * 255),
                (int) (getColor().getGreen() * 255),
                (int) (getColor().getBlue() * 255),
                0.5
        ));
        gc.setLineWidth(2);
        for (int i = 1; i < trajectory.size(); i++) {
            Point prev = trajectory.get(i - 1);
            Point curr = trajectory.get(i);
            gc.strokeLine(prev.getX(), prev.getY(), curr.getX(), curr.getY());
        }

        // 포탄 본체 그리기
        super.paint(gc);

        // 폭발 효과 표시 (hasExploded가 true일 경우)
        if (hasExploded) {
            gc.setFill(Color.ORANGE);
            gc.fillOval(getX() - type.getExplosionRadius(), getY() - type.getExplosionRadius(), type.getExplosionRadius() * 2, type.getExplosionRadius() * 2);
        }
    }

    // handleCollision 메서드 오버라이딩
    @Override
    public void handleCollision(Collidable other) {
        if (hasExploded) return;

        switch (type) {
            case BOUNCY:
                // 바닥/벽 충돌 시 반사 (부모 클래스의 BOUNCE 로직을 사용)
                super.handleCollision(other);
                break;
            case SCATTER:
                // 산탄은 충돌 시 분열
                scatter();
                hasExploded = true;
                break;
            default:
                // 일반/무거운/폭발 포탄은 충돌 시 폭발
                explode();
                hasExploded = true;
                break;
        }
    }

    // 폭발 처리
    public void explode() {
        System.out.println("폭발! 반경: " + type.getExplosionRadius());
    }

    // 산탄 분열
    public List<Projectile> scatter() {
        List<Projectile> newProjectiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double randomAngle = Math.toRadians(Math.random() * 180 - 90);
            double randomSpeed = 200 + Math.random() * 100;
            double newDx = randomSpeed * Math.cos(randomAngle);
            double newDy = randomSpeed * Math.sin(randomAngle);
            newProjectiles.add(new Projectile(getX(), getY(), newDx, newDy, ProjectileType.NORMAL));
        }
        return newProjectiles;
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    // 궤적 저장을 위한 Point 클래스
    private static class Point {
        private final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        public double getX() { return x; }
        public double getY() { return y; }
    }
    public double getExplosionRadius() {
        return this.type.getExplosionRadius();
    }
}