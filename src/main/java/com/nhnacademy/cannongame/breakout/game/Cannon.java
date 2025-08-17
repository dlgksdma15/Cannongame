package com.nhnacademy.cannongame.breakout.game;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import com.nhnacademy.cannongame.simpleworld.Boundable;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import com.nhnacademy.cannongame.simpleworld.Paintable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cannon implements Paintable, Collidable {
    // 필드
    private double x;
    private double y;
    private double angle; // 발사 각도 (라디안)
    private double power; // 발사 힘 (100 ~ 1000)
    private final double barrelLength;
    private final double barrelWidth;
    protected boolean isCharging;
    private long chargeStartTime;

    // 상수
    private static final double MAX_CHARGE_TIME = 2000; // 2초 (밀리초 단위)

    public Cannon(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = -Math.PI / 4; // 기본 각도 -45도
        this.power = 100; // 기본 파워
        this.barrelLength = 50;
        this.barrelWidth = 10;
        this.isCharging = false;
        this.chargeStartTime = 0;
    }

    // Paintable 인터페이스 구현
    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);

        // 포신 그리기
        gc.rotate(Math.toDegrees(angle));

        // 충전 상태에 따른 색상 변화
        double chargeLevel = getChargeLevel();
        Color barrelColor = Color.rgb(
                (int) (255 * chargeLevel),
                (int) (255 * (1 - chargeLevel)),
                0
        );
        gc.setFill(barrelColor);
        gc.fillRect(0, -barrelWidth / 2, barrelLength, barrelWidth);

        // 대포 본체 (원형)
        gc.restore();
        gc.setFill(Color.DARKGRAY);
        gc.fillOval(x - 20, y - 20, 40, 40);

        // 조준선과 파워 게이지는 별도 메소드로 구현
        //...
    }

    // 충전 시스템
    public void startCharging() {
        if (!isCharging) {
            isCharging = true;
            chargeStartTime = System.currentTimeMillis();
        }
    }

    public void stopCharging() {
        if (isCharging) {
            isCharging = false;
            long elapsedTime = System.currentTimeMillis() - chargeStartTime;
            double chargeLevel = Math.min(1.0, (double) elapsedTime / MAX_CHARGE_TIME);
            this.power = 100 + (900 * chargeLevel); // 100 ~ 1000 범위로 변환
        }
    }

    public double getChargeLevel() {
        if (isCharging) {
            long elapsedTime = System.currentTimeMillis() - chargeStartTime;
            return Math.min(1.0, (double) elapsedTime / MAX_CHARGE_TIME);
        }
        return (power - 100) / 900;
    }

    // 수정된 Cannon 클래스의 fire() 메소드
    public Projectile fire() {
        if (isCharging()) {
            stopCharging();
            // 포탄 생성 로직
            double startX = x + barrelLength * Math.cos(angle);
            double startY = y + barrelLength * Math.sin(angle);
            double initialDx = power * Math.cos(angle);
            double initialDy = power * Math.sin(angle);

            return new Projectile(startX, startY, initialDx, initialDy);
        }
        return null; // 충전 중이 아니면 null 반환
    }

    // 조작 메서드
    public void adjustAngle(double delta) {
        this.angle += delta;
        // 각도 제한 (-90도 ~ 0도)
        if (angle > 0) angle = 0;
        if (angle < -Math.PI / 2) angle = -Math.PI / 2;
    }
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void handleCollision(Collidable other) {
        // 대포는 충돌에 의해 파괴되지 않으므로, 충돌한 객체에만 영향을 줍니다.
        if (other instanceof Projectile) {
            Projectile projectile = (Projectile) other;
            // 포탄이 대포에 닿았을 때의 로직 (예: 폭발)
            projectile.explode();
        }
    }

    @Override
    public CollisionAction getCollisionAction() {
        // 대포는 움직이지 않는 객체이므로 PASS 액션을 반환합니다.
        return CollisionAction.PASS;
    }

    @Override
    public Bounds getBounds() {
        // 대포 본체를 감싸는 사각형 경계를 반환합니다.
        double cannonWidth = 40; // 대포 본체 크기 (예시)
        double cannonHeight = 40;
        return new RectangleBounds(x - cannonWidth / 2, y - cannonHeight / 2, cannonWidth, cannonHeight);
    }

    @Override
    public boolean isColliding(Boundable other) {
        // 자신의 경계와 상대방 경계의 교차 여부를 반환합니다.
        return this.getBounds().intersects(other.getBounds());
    }
    //... (Collidable 인터페이스 구현은 요구사항에 따라 추가)

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getPower() {
        return power;
    }
    public boolean isCharging() {
        return isCharging;
    }
}