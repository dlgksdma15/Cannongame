package com.nhnacademy.cannongame.shapes;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;

public abstract class Shape {
    protected Point position;

    // 추상 메서드 - 구현 없음
    public abstract double getArea();
    public abstract Point getPosition();
    public abstract String getShapeType();
    public abstract double getPerimeter();

    // 구체 메서드 - 구현 있음
    public void moveBy(Vector2D delta){
        this.position = new Point(
                position.getX() + delta.getX(),
                position.getY() + delta.getY()
        );
    }


}
