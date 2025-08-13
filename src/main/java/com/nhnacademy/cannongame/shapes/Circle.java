package com.nhnacademy.cannongame.shapes;

import com.nhnacademy.cannongame.Point;

public class Circle extends Shape{
    private double radius;

    public Circle(Point position, double radius){
        this.position = position;
        this.radius = radius;
    }
    public Circle(double x, double y, double radius){
        this.position = new Point(x,y);
        this.radius = radius;
    }

    // 메서드 오버라이딩
    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }
    @Override
    public Point getPosition() {
        return position;
    }
    @Override
    public String getShapeType() {
        return "Circle";
    }
    public double getRadius(){
        return radius;
    }

    @Override
    public double getPerimeter() {
        return (radius * 2) * Math.PI;
    }
}
