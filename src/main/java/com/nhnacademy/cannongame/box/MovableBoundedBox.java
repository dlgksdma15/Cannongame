package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;

public class MovableBoundedBox extends BoundedBox {
    private Vector2D velocity;

    public MovableBoundedBox(Point position, double width, double height) {
        super(position, width, height);
        this.velocity = new Vector2D(0, 0);
    }

    public MovableBoundedBox(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        if (velocity == null) {
            throw new IllegalArgumentException("velocity는 null일 수 없습니다.");
        }
        this.velocity = velocity;
    }

    public void move(double deltaTime) {
        Point newPosition = new Point(
                getPosition().getX() + velocity.getX() * deltaTime,
                getPosition().getY() + velocity.getY() * deltaTime
        );
        moveTo(newPosition);
    }

    public double getDx() {
        return velocity.getX();
    }

    public double getDy() {
        return velocity.getY();
    }
}