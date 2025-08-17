package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import com.nhnacademy.cannongame.simpleworld.Paintable;
import com.nhnacademy.cannongame.simpleworld.Boundable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class StaticObject implements Collidable, Paintable {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Color color;
    protected CollisionAction collisionAction; // CollisionAction 필드 추가

    public StaticObject(double x, double y, double width, double height, Color color, CollisionAction collisionAction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.collisionAction = collisionAction;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    @Override
    public Bounds getBounds() {
        return new RectangleBounds(x, y, width, height);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public abstract void handleCollision(Collidable other);

    @Override
    public CollisionAction getCollisionAction() {
        return collisionAction;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}