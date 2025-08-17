package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.CircleBounds;
import com.nhnacademy.cannongame.breakout.UnbreakableBrick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Paintable, Movable, Collidable {
    // 위치와 크기
    private double x;
    private double y;
    private double radius;
    // 속도
    protected double dx;
    protected double dy;
    //색상
    protected Color color;
    // 충돌 시 행동
    private CollisionAction collisionAction;
    private boolean isDestroyed = false;

    public Ball(double x, double y, double radius, Color color, CollisionAction collisionAction) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.collisionAction = collisionAction;
    }

    public Ball(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.collisionAction = CollisionAction.BOUNCE;
    }

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = Color.RED;
        this.collisionAction = CollisionAction.BOUNCE;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void move(double deltaTime) {
        x += dx * deltaTime;
        y += dy * deltaTime;
    }

    @Override
    public Bounds getBounds() {
        return new CircleBounds(x, y, radius);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return this.getBounds().intersects(other.getBounds());
    }

    @Override
    public void handleCollision(Collidable other) {
        // UnbreakableBrick과의 충돌 로직을 다른 충돌 처리보다 먼저 실행
        if (other instanceof UnbreakableBrick) {
            UnbreakableBrick wall = (UnbreakableBrick) other;
            Bounds wallBounds = wall.getBounds();

            // 공의 중심과 벽의 경계를 비교하여 충돌 방향을 판단
            double ballCenterX = this.x;
            double ballCenterY = this.y;
            double wallMinX = wallBounds.getMinX();
            double wallMaxX = wallBounds.getMaxX();
            double wallMinY = wallBounds.getMinY();
            double wallMaxY = wallBounds.getMaxY();

            // 수평 벽(상단/하단)과 충돌 시 Y 속도 반전
            if (ballCenterY - this.radius < wallMinY || ballCenterY + this.radius > wallMaxY) {
                this.setDy(-this.getDy());
            }
            // 수직 벽(좌/우)과 충돌 시 X 속도 반전
            else if (ballCenterX - this.radius < wallMinX || ballCenterX + this.radius > wallMaxX) {
                this.setDx(-this.getDx());
            }
            return;
        }

        switch (collisionAction) {
            case BOUNCE:
                // 충돌한 객체가 Movable이면 반사
                if (other instanceof Movable) {
                    Movable movableOther = (Movable) other;
                    movableOther.setDx(-movableOther.getDx());
                    movableOther.setDy(-movableOther.getDy());
                }
                // Ball 자신도 반사
                this.setDx(-this.getDx());
                this.setDy(-this.getDy());
                break;
            case DESTROY:
                this.isDestroyed = true;
                System.out.println("충돌로 인해 객체 파괴");
                break;
            case STOP:
                this.setDx(0);
                this.setDy(0);
                break;
            case PASS:
                // 아무것도 하지 않음 (통과)
                break;
            default:
                System.out.println("사용자 정의 충돌 처리");
                break;
        }
    }

    @Override
    public CollisionAction getCollisionAction() {
        return collisionAction;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCollisionAction(CollisionAction collisionAction) {
        this.collisionAction = collisionAction;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public double getRadius() {
        return radius;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}