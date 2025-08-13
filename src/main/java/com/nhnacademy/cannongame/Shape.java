package com.nhnacademy.cannongame;

public abstract class Shape {
    protected Point position;

    // 추상 메서드 - 구현 없음
    public abstract double getArea();

    // 구체 메서드 - 구현 있음
    public void moveBy(Vector2D delta){
        this.position = new Point(
                position.getX() + delta.getX(),
                position.getY() + delta.getY()
        );
    }
}
