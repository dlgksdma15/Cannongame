package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle implements Paintable, Movable, Collidable{
    // 필드
    private double[] xPoints;
    private double[] yPoints;
    private int nPoints = 3;
    private double dx, dy;
    private Color color;
    private CollisionAction collisionAction;

    // 생성자
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color, CollisionAction collisionAction) {
        this.xPoints = new double[]{x1, x2, x3};
        this.yPoints = new double[]{y1, y2, y3};
        this.color = color;
        this.collisionAction = collisionAction;
    }

    // Movable 인터페이스 구현
    @Override
    public void paint(GraphicsContext gc) {

    }


    // Movable 인터페이스 구현
    @Override
    public void move(double deltaTime) {
        for (int i = 0; i < nPoints; i++) {
            xPoints[i] += dx * deltaTime;
            yPoints[i] += dy * deltaTime;
        }
    }
    @Override public double getDx() { return dx; }
    @Override public double getDy() { return dy; }
    @Override public void setDx(double dx) { this.dx = dx; }
    @Override public void setDy(double dy) { this.dy = dy; }

    // Collidable 인터페이스 구현
    @Override
    public Bounds getBounds() {
        double minX = Math.min(xPoints[0], Math.min(xPoints[1], xPoints[2]));
        double minY = Math.min(yPoints[0], Math.min(yPoints[1], yPoints[2]));
        double maxX = Math.max(xPoints[0], Math.max(xPoints[1], xPoints[2]));
        double maxY = Math.max(yPoints[0], Math.max(yPoints[1], yPoints[2]));
        return new RectangleBounds(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public void handleCollision(Collidable other) {
        // 충돌 로직 구현은 다른 객체와 동일하게 수행합니다.
        // 여기서는 예시로 BOUNCE만 처리
        if (collisionAction == CollisionAction.BOUNCE) {
            if (other instanceof Movable) {
                Movable movableOther = (Movable) other;
                movableOther.setDx(-movableOther.getDx());
                movableOther.setDy(-movableOther.getDy());
                this.setDx(-this.getDx());
                this.setDy(-this.getDy());
            }
        }
    }

    @Override public CollisionAction getCollisionAction() { return collisionAction; }


}
